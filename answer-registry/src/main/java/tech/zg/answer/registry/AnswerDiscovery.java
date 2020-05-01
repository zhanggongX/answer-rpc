package tech.zg.answer.registry;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.constant.AnswerConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * client端服务发现
 * 并实现负载均衡
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerDiscovery {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AnswerDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<String>();

    private String registryAddress;

    /**
     * zk链接
     * <p>
     *
     * @param registryAddress 注册地址
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    public AnswerDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;

        ZooKeeper zk = connectServer();
        if (zk != null) {
            watchNode(zk);
        }
    }

    /**
     * 发现新节点
     * <p>
     *
     * @author ：zhanggong
     * @version : 1.0.0
     * @date ：2018/5/1
     */
    public String discover() {
        String data = null;
        int size = dataList.size();
        // 存在新节点，使用即可
        if (size > 0) {
            if (size == 1) {
                data = dataList.get(0);
                LOGGER.debug("using only data: {}", data);
            } else {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random data: {}", data);
            }
        }
        return data;
    }

    /**
     * 链接
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
                        if (event.getState() == Event.KeeperState.SyncConnected) {
                            latch.countDown();
                        }
                    }
                });
            latch.await();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    /**
     * 监听
     * <p>
     *
     * @param zk
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    private void watchNode(final ZooKeeper zk) {
        try {
            // 获取所有子节点
            List<String> nodeList = zk.getChildren(AnswerConstant.ZK_REGISTRY_PATH,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        // 节点改变
                        if (event.getType() == Event.EventType.NodeChildrenChanged) {
                            watchNode(zk);
                        }
                    }
                });
            List<String> dataList = new ArrayList<String>();
            // 循环子节点
            for (String node : nodeList) {
                // 获取节点中的服务器地址
                byte[] bytes = zk.getData(AnswerConstant.ZK_REGISTRY_PATH + "/" + node, false, null);
                // 存储到list中
                dataList.add(new String(bytes));
            }
            LOGGER.debug("node data: {}", dataList);
            // 将节点信息记录在成员变量
            this.dataList = dataList;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

}

