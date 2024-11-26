package org.udemy.vthreads.sec01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {

    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 20;

    public static void main(String[] args) throws InterruptedException {
        //platformThreadDemo3();
        virtualThreadDemo1();
    }

    /** conventional simple java platform thread method **/
    private static void platformThreadDemo1() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /** non-Daemon threads - using Thread Builder**/
    private static void platformThreadDemo2() {
        Thread.Builder.OfPlatform builder = Thread.ofPlatform().name("tpk", 1);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int finalI = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(finalI));
            thread.start();
        }
    }

    /** Daemon threads -- using Thread Builder**/
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

    /** Virtual Thread **/
    /** - Daemon threads by default, cannot create non daemon virtual threads **/
    /** - Fork join pool, park, un-park, carrier mount - Important terminologies **/
    private static void virtualThreadDemo1() throws InterruptedException {
        Thread.Builder.OfVirtual virtualThread = Thread.ofVirtual().name("virtual-", 1);
        CountDownLatch latch = new CountDownLatch(MAX_VIRTUAL);
        for (int i = 0; i < MAX_VIRTUAL; i++) {
            int finalI = i;
            Thread thread = virtualThread.unstarted(() -> {
                Task.ioIntensive(finalI);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }
}
