package tech.zg.answer.client.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleThreadPool {

    private static final Logger log = LoggerFactory.getLogger(SingleThreadPool.class);

    public final static ThreadPoolExecutor SINGLE_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
            1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(1),
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("AnswerSingleThreadPool");
                return thread;
            }, (r, executor1) -> log.error("AnswerSingleThreadPool Reject: {}", r.toString()));
}