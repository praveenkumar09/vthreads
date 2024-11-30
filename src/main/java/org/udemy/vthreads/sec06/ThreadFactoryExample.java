package org.udemy.vthreads.sec06;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryExample {

    private static final Logger log = LoggerFactory.getLogger(ThreadFactoryExample.class);

    public static void main(String[] args) {
        try {
            demo(Thread.ofVirtual().name("virtual-",1).factory());
            CommonUtils.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void demo(ThreadFactory factory) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Thread factoryParentThread = factory.newThread(() -> {
                log.info("Thread parent started {}, i: {}", Thread.currentThread(), finalI);
                Thread factoryChildThread = factory.newThread(() -> {
                    log.info("Thread child started {}, i: {}", Thread.currentThread(), finalI);
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    log.info("Thread child finished {}, i: {}", Thread.currentThread(), finalI);
                });
                factoryChildThread.start();
                log.info("Thread parent finished {}, i : {}", Thread.currentThread(), finalI);
            });
            factoryParentThread.start();
        }
    }
}
