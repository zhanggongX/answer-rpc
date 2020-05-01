package tech.zg.answer.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.constant.AnswerConstant;
import tech.zg.answer.common.annotation.AnswerService;
import tech.zg.answer.common.util.AnnotationUtil;
import tech.zg.answer.common.util.ClassUtil;
import tech.zg.answer.server.config.AnswerConfig;
import tech.zg.answer.server.netty.NettyServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 框架的RPC 服务器（用于将用户系统的业务类发布为 RPC 服务）
* <p>
* @author ：zhanggong
* @version : 1.0.0
* @date ：2018/5/1
*/
public class AnswerServer {

    private AnswerConfig answerConfig;
    private Map<String, Object> handlerMap = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(AnswerServer.class);

    private AnswerServer() {
    }

    public AnswerConfig getAnswerConfig() {
        return answerConfig;
    }

    public void setAnswerConfig(AnswerConfig answerConfig) {
        this.answerConfig = answerConfig;
    }

    public static AnswerServer open(AnswerConfig answerConfig) {
        AnswerServer answerServer = new AnswerServer();
        checkConfig(answerConfig);
        answerServer.setAnswerConfig(answerConfig);
        return answerServer;
    }

    private static void checkConfig(AnswerConfig answerConfig) {
        if (answerConfig.getAnswerPort() <= 0) {
            answerConfig.setAnswerPort(AnswerConstant.DEFAULT_SERVER_PORT);
        }
        if (answerConfig.getServicePackage() == null || answerConfig.getServicePackage().length() <= 0) {
            answerConfig.setServicePackage(AnswerServer.class.getPackage().getName());
        }
    }

    public void run() throws Exception {
        List<Class<?>> classes = ClassUtil.getClasses(answerConfig.getServicePackage());
        AnnotationUtil.validAnswerServiceAnnotation(classes);
        if (classes != null && classes.size() > 0) {
            for (int i = 0; i < classes.size(); i++) {
                Class clazz = classes.get(i);
                Object object = clazz.newInstance();
                String interName = object.getClass().getAnnotation(AnswerService.class).value().getName();
                handlerMap.put(interName, object);
            }
        }

        // 启动服务
        NettyServer nettyServer = new NettyServer(this.getAnswerConfig().getAnswerPort());
        nettyServer.run();
    }
}