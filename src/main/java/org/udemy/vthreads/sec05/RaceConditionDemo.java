package org.udemy.vthreads.sec05;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionDemo {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionDemo.class);
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        demo(Thread.ofVirtual());
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info(" List size: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Thread {} started", Thread.currentThread().getName());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Thread {} finished", Thread.currentThread().getName());
            });
        }
    }

    private static void inMemoryTask(){
        list.add(1);
    }
}
