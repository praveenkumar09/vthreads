package org.udemy.vthreads.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class RaceConditionDemoSyncWithIOReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionDemoSyncWithIOReentrantLock.class);
    private static final List<Integer> list = new ArrayList<>();
    private static final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(() -> log.info("*** Test Message ***"));
        CommonUtils.sleep(Duration.ofSeconds(15));
    }

    private static void demo(Thread.Builder builder) {
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            builder.start(() -> {
                log.info("Thread {} started", Thread.currentThread());
                ioTask();
                log.info("Thread {} finished, i : {}", Thread.currentThread(), finalI);
            });
        }
    }

    private static void ioTask(){
        try {
            lock.lock();
            list.add(1);
            CommonUtils.sleep(Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
