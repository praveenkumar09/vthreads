package org.udemy.vthreads.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutbondTaskDemo {

    private static final int MAX_PLATFORM = 10;

    public static void main(String[] args) throws InterruptedException {
        platformThreadDemo3();
    }

    private static void platformThreadDemo1() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /** non-Daemon threads **/
    private static void platformThreadDemo2() {
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().name("tpk", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /** Daemon threads **/
    private static void platformThreadDemo3() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(MAX_PLATFORM);
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().daemon().name("daemon", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() ->
            {
                Task.ioIntensive(finalI);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }
}
