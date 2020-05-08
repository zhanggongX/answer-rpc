package tech.zg.answer.client.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.bean.AnswerResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class AnswerClientResponseCache {

    private static final Logger log = LoggerFactory.getLogger(AnswerClientResponseCache.class);

    private static Map<String, AnswerResponse> responseMap = new ConcurrentHashMap<>();


    public static AnswerResponse getResponse(String requestId, int timeOut) throws TimeoutException, InterruptedException {
        long startTime = System.currentTimeMillis();
        log.info("获取-{}-结果开始-{}ms", requestId, startTime);
        AnswerResponse answerResponse = null;
        do {
            answerResponse = responseMap.get(requestId);
            if (answerResponse != null) {
                log.info("获取-{}-结果耗时-{}ms", requestId, System.currentTimeMillis() - startTime);
                return answerResponse;
            }
            Thread.sleep(100);
        } while (System.currentTimeMillis() - startTime < timeOut);
        log.info("获取-{}-结果超时-{}ms", requestId, System.currentTimeMillis() - startTime);
        throw new TimeoutException();
    }

    public static void cacheResponse(String requestId, AnswerResponse answerResponse) {
        responseMap.put(requestId, answerResponse);
    }
}
