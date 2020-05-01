package tech.zg.answer.example.server;

import tech.zg.answer.server.AnswerServer;
import tech.zg.answer.server.config.AnswerConfig;

public class Start {

    public static void main(String[] args) throws Exception {
        AnswerConfig answerConfig = new AnswerConfig();
        answerConfig.setAnswerAddress("localhost");
        answerConfig.setAnswerAddressPort(8001);
        answerConfig.setServicePackage("tech.zg.answer.example.server.service");

        AnswerServer answerServer = AnswerServer.open(answerConfig);
        answerServer.run();
    }
}
