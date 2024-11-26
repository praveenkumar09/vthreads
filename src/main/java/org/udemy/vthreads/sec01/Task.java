package org.udemy.vthreads.sec01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);


    public static void ioIntensive(int i){
        try {
            logger.info("Starting I/O intensive task {}, Thread Info: {}",i,Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(10));
            logger.info("Ending I/O intensive task {}, Thread Info: {}",i,Thread.currentThread());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
