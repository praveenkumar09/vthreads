package org.udemy.vthreads.utils;

import java.time.Duration;

public class CommonUtils {

    public static void sleep(Duration duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
