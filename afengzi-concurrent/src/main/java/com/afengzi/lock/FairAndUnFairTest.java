package com.afengzi.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lixiuhai on 2017/10/10.
 */
public class FairAndUnFairTest {


    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unFairLock = new ReentrantLock2(false);


    @org.junit.Test
    public void fair() throws InterruptedException {
        testLock(fairLock);
        Thread.currentThread().join();
    }

    public void unFair(){
        testLock(unFairLock);
    }

    private void testLock(ReentrantLock2 lock){
        for (int i = 0 ; i < 5 ; i++){
            new Thread(new Job(lock)).start();
        }
    }


    private static class Job extends Thread{
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println("current thread - 1 : "+Thread.currentThread().getName());
            System.out.println("current thread - 2 : "+Thread.currentThread().getName());
            System.out.println("queued thread  : "+lock.getQueuedThreads());
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{
        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads(){
            List<Thread> queuedThreads = new ArrayList<Thread>(super.getQueuedThreads());
            Collections.reverse(queuedThreads);
            return queuedThreads;
        }
    }
}
