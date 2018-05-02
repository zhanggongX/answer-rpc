package tech.zg.answer.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "answer")
public class AnswerConfig {
    /**
     * answer 服务的地址
     */
    public static String answerAddress;

    /**
     * zookeeper 地址，服务注册使用
     */
    public static String registryAddress;

    public static String getAnswerAddress() {
        return answerAddress;
    }

    public static void setAnswerAddress(String answerAddress) {
        AnswerConfig.answerAddress = answerAddress;
    }

    public static String getRegistryAddress() {
        return registryAddress;
    }

    public static void setRegistryAddress(String registryAddress) {
        AnswerConfig.registryAddress = registryAddress;
    }
}