package tech.zg.answer.example.server;

import lombok.extern.slf4j.Slf4j;
import tech.zg.answer.server.AnswerServer;
import tech.zg.answer.server.config.AnswerConfig;

@Slf4j
public class Start {

    public static void main(String[] args) throws Exception {
        AnswerConfig answerConfig = new AnswerConfig();
        answerConfig.setAnswerPort(8888);
        answerConfig.setServicePackage("tech.zg.answer.example.server.service");

        AnswerServer answerServer = AnswerServer.open(answerConfig);
        answerServer.run();
        log.info("1");
    }
}
