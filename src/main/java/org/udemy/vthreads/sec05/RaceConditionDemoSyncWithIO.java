package org.udemy.vthreads.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RaceConditionDemoSyncWithIO {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionDemoSyncWithIO.class);
    private static final List<Integer> list = new ArrayList<>();

    static{
        System.setProperty("jdk.tracePinnedThreads","short");
    }

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(() -> log.info("*** Test Message ***"));
        CommonUtils.sleep(Duration.ofSeconds(15));
    }

    private static void demo(Thread.Builder builder) throws InterruptedException {
       //CountDownLatch countDownLatch = new CountDownLatch(50);
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            builder.start(() -> {
                log.info("Thread {} started", Thread.currentThread());
                ioTask();
                log.info("Thread {} finished, i : {}", Thread.currentThread(), finalI);
                //countDownLatch.countDown();
            });
        }
        //countDownLatch.await();
    }

    private static synchronized void ioTask(){
        list.add(1);
        CommonUtils.sleep(Duration.ofSeconds(10));
    }
}
