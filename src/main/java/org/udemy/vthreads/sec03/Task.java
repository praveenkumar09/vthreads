package org.udemy.vthreads.sec03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.udemy.vthreads.utils.CommonUtils;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i){
        //log.info("Starting CPU intensive task with i: {}, Thread Info : {}",i,Thread.currentThread());
        var timeTaken = CommonUtils.timer(() -> findFeb(i));
        //log.info("Ending CPU Task with i: {}, Time Taken : {}ms",i,timeTaken);
    }

    // 2 ^ N algorithm - intentionally making it hard for CPU
    public static long findFeb(long input){
        if(input < 2)
            return input;
        return findFeb(input-1) + findFeb(input-2);
    }
}
