package tech.zg.answer.client;

import tech.zg.answer.common.bean.AnswerResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class AnswerClientResult {

    private static Map<String, AnswerResponse> responseMap = new ConcurrentHashMap<>();


    public static AnswerResponse getResponse(String requestId, int timeOut) throws TimeoutException {
        long startTime = System.currentTimeMillis();
        AnswerResponse answerResponse = null;
        do {
            answerResponse = responseMap.get(requestId);
        } while (System.currentTimeMillis() - startTime < timeOut);

        if (answerResponse == null) {
            throw new TimeoutException();
        }
        return answerResponse;
    }

    public static void cacheResponse(String requestId, AnswerResponse answerResponse) {
        responseMap.put(requestId, answerResponse);
    }
}
