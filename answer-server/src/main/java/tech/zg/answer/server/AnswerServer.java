package tech.zg.answer.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.AnswerConstant;
import tech.zg.answer.common.scanner.PackageScanner;
import tech.zg.answer.server.config.AnswerConfig;

import java.util.HashMap;
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
        if (answerConfig.getAnswerAddress() == null || answerConfig.getAnswerAddress().length() <= 0) {
            answerConfig.setAnswerAddress(AnswerConstant.DEFAULT_SERVER_ADDRESS);
        }
        if (answerConfig.getAnswerAddressPort() <= 0) {
            answerConfig.setAnswerAddressPort(AnswerConstant.DEFAULT_SERVER_PORT);
        }
        if (answerConfig.getServicePackage() == null || answerConfig.getServicePackage().length() <= 0) {
            answerConfig.setServicePackage(AnswerServer.class.getPackage().getName());
        }
    }

    public void run() {
        try {
            PackageScanner packageScanner = new PackageScanner(answerConfig.getServicePackage());
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("加载到类：{}", objectMapper.writeValueAsString(packageScanner));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("加载类失败");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error("加载类失败");
        }
    }

    //用于存储业务接口和实现类的实例对象(由spring所构造)
    private Map<String, Object> handlerMap = new HashMap<String, Object>();


    /**
     * 通过注解，获取标注了rpc服务注解的业务类的----接口及impl对象，将它放到handlerMap中
     * <p>
     *
     * @param applicationContext spring 上下文
     * @return
     * @throws
     * @author : zhanggong
     * @version : 1.0.0
     * @date : 2018/5/1
     */
    /*public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(Answer.class);
        if(MapUtils.isNotEmpty(serviceBeanMap)){
            for(Object serviceBean : serviceBeanMap.values()){
                //从业务实现类上的自定义注解中获取到value，从来获取到业务接口的全名
                String interfaceName = serviceBean.getClass().getAnnotation(Answer.class).value().getName();
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }*/
}