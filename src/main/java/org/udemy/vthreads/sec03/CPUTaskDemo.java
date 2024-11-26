package org.udemy.vthreads.sec03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.sql.SQLOutput;
import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    //private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int TASK_COUNT = 3;
    private static final Logger log = LoggerFactory.getLogger(CPUTaskDemo.class);

    public static void main(String[] args) {
        //demo(Thread.ofPlatform());
        //demo(Thread.ofVirtual().name("virtual-",1));
        log.info("Available Processors {} : Start Time :",TASK_COUNT);

        for(int i = 0; i < TASK_COUNT; i++) {
            var totalTimeTakenForVirtual = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            log.info("Total time take for virtual : {} ms",totalTimeTakenForVirtual);

            var totalTimeTakenForPlatform = CommonUtils.timer(() -> demo(Thread.ofPlatform()));
            log.info("Total time take for platform :  {} ms",totalTimeTakenForPlatform);
        }
    }

    public static void demo(Thread.Builder builder){
        CountDownLatch latch = new CountDownLatch(TASK_COUNT);
        for(int i = 0; i < TASK_COUNT; i++){
            builder.start(() -> {
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
