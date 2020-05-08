package tech.zg.answer.client.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.common.bean.AnswerRequest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class AnswerClientRequestCache {

    private static final Logger log = LoggerFactory.getLogger(AnswerClientRequestCache.class);

    private static LinkedBlockingQueue<AnswerRequest> queue = new LinkedBlockingQueue<>();


    public static AnswerRequest pollRequest() throws InterruptedException {
        return queue.poll(100, TimeUnit.MILLISECONDS);
    }

    public static void cacheRequest(AnswerRequest answerRequest) {
        queue.add(answerRequest);
    }
}
