package tech.zg.answer.common;

/**
 * 所有的常量
 * <p>
 *
 * @author ：zhanggong
 * @version : 1.0.0
 * @date ：2018/5/1
 */
public class AnswerConstant {

    /**
     * ZK超时时间
     */
    public static final int ZK_SESSION_TIMEOUT = 5000;

    /**
     * 注册节点
     */
    public static final String ZK_REGISTRY_PATH = "/AnswerRegistry";

    /**
     * 数据节点
     */
    public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/AnswerData";

    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final int DEFAULT_SERVER_PORT = 8000;
}