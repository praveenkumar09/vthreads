package org.udemy.vthreads.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RaceConditionDemoSyncWithReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionDemoSyncWithReentrantLock.class);
    private static final List<Integer> list = new ArrayList<>();
    private static final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info(" List size: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Thread {} started", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Thread {} finished", Thread.currentThread());
            });
        }
    }

    private static void inMemoryTask(){
        try {
            lock.lock();
            list.add(1);
        }catch (Exception e){
            log.error("Error {}",e.getMessage());
        }finally {
            lock.unlock();
        }
    }
}
