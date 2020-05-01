package tech.zg.answer.registry;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.constant.AnswerConstant;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.concurrent.CountDownLatch;

/**
 * server端服务注册
 * ZK 在该架构中扮演了“服务注册表”的角色
 * 用于注册所有服务器的地址与端口
 * 并对客户端提供服务发现的功能
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerRegistry {

    /**
     * 日志
     */
    Logger LOGGER = LoggerFactory.getLogger(AnswerRegistry.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * zookeeper的地址
     */
    private String registryAddress;

    public AnswerRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    /**
     * 创建zookeeper链接
     * <p>
     *
     * @param data
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    public void register(String data) {
        if (data != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                createNode(zk, data);
            }
        }
    }

    /**
     * 创建zookeeper链接，监听
     * <p>
     *
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, AnswerConstant.ZK_SESSION_TIMEOUT,
                    new Watcher() {
                        public void process(WatchedEvent event) {
                            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                                countDownLatch.countDown();
                            }
                        }
                    });
            countDownLatch.await();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    /**
    * 创建节点
    * <p>
    * @author ：zhanggong
    * @version : 1.0.0
    * @date ：2018/5/1
    */
    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            if (zk.exists(AnswerConstant.ZK_REGISTRY_PATH, null) == null) {
                zk.create(AnswerConstant.ZK_REGISTRY_PATH, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String path = zk.create(AnswerConstant.ZK_DATA_PATH, bytes, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, data);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

}