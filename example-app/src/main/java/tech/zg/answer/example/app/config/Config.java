package tech.zg.answer.example.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.zg.answer.client.AnswerClientProxy;

@Configuration
public class Config {

    @Value("${answer.serverAddress}")
    private String serverAddress;

    @Value("${answer.serverPort}")
    private String serverPort;

    @Bean
    public AnswerClientProxy anwerClientProxy() {
        return new AnswerClientProxy(serverAddress, Integer.valueOf(serverPort));
    }
}
