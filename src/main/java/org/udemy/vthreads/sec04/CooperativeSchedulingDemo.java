package org.udemy.vthreads.sec04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;

public class CooperativeSchedulingDemo {

    private static final Logger log = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    static{
        System.setProperty("jdk.virtualThreadScheduler.parallelism","1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize","1");
    }

    public static void main(String[] args) {
        var builder = Thread.ofVirtual();
        var t1 = builder.unstarted(() -> demo(1));
        var t2 = builder.unstarted(() -> demo(2));
        t1.start();
        t2.start();
        CommonUtils.sleep(Duration.ofSeconds(10));
    }

    private static void demo(int threadNumber){
        log.info("Starting {} thread", threadNumber);
        for(int i=0; i< 10; i++){
            log.info("thread={} is printing {}, Thread: {}",threadNumber,i,Thread.currentThread());
            Thread.yield();
        }
        log.info("Ending {} thread", threadNumber);
    }
}
