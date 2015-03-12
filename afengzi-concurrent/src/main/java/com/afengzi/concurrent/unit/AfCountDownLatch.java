package com.afengzi.concurrent.unit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lixiuhai on 2015/3/12.
 */
public class AfCountDownLatch {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Executor executor = Executors.newCachedThreadPool();
        for (int i = 0 ; i < 5 ; i++){
            executor.execute(new Worker(countDownLatch,i));
        }
        try {
            countDownLatch.await();
            System.out.println("###############");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class Worker implements Runnable {
        private CountDownLatch countDownLatch;
        private int index;

        public Worker(CountDownLatch countDownLatch, int index) {
            this.countDownLatch = countDownLatch;
            this.index = index;
        }

        @Override
        public void run() {
            try {
                doWork(index);
                countDownLatch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void doWork(int i) {
            System.out.println("************** " + i);
        }
    }
}
