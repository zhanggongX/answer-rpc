package tech.zg.answer.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.zg.answer.client.cache.AnswerClientRequestCache;
import tech.zg.answer.client.cache.AnswerClientResponseCache;
import tech.zg.answer.common.bean.AnswerRequest;
import tech.zg.answer.common.bean.AnswerResponse;

import java.util.concurrent.TimeoutException;

public class AnswerClient {

    private static final Logger log = LoggerFactory.getLogger(AnswerClient.class);

    public static AnswerResponse send(AnswerRequest request) throws TimeoutException, InterruptedException {
        AnswerClientRequestCache.cacheRequest(request);
        return AnswerClientResponseCache.getResponse(request.getRequestId(), 30000);
    }
}
