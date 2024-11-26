package org.udemy.vthreads.sec02;

import org.udemy.vthreads.utils.CommonUtils;

import java.time.Duration;

public class StackTraceDemo {
    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("virtual-",1));
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(Thread.Builder builder){
        for(int i = 0; i < 40; i++){
            int j = i;
            builder.start(() -> Task.exec(j));
        }
    }
}
