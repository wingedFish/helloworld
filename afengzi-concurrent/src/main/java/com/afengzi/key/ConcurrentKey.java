package com.afengzi.key;

import org.junit.Test;

/**
 * Created by lixiuhai on 2017/10/19.
 */
public class ConcurrentKey {

    @Test
    public void testJoin() throws InterruptedException {
        Thread printT = new Thread(new Runnable() {
//            @Override
            public void run() {
                System.out.println("==="+Thread.currentThread().getName());
            }
        });
        Thread.sleep(1000);
        printT.start();
        printT.join();
    }
}
