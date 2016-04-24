package com.afengzi.concurrent.product.threadPoolMonitor;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by lixiuhai on 2016/3/8.
 */
public class ThreadPoolMonitorTest {

    @Test
    public void testAFThreadPoolExecutor() throws InterruptedException {
        AFThreadPoolExecutor executor = new AFThreadPoolExecutor(5,10,5,new LinkedBlockingQueue<Runnable>(20),ThreadPoolNameEnum.EatMeat);
        for (int i = 0 ; i < 30 ; i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            Thread.sleep(1000);
        }
    }
}
