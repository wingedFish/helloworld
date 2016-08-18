package com.afengzi.concurrent.schedule;

import com.afengzi.concurrent.factory.ThreadPoolMap;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Created by winged fish on 2016/7/14.
 */
public class ThreadDispatcher {
    private ExecutorService dispatcher = ThreadPoolMap.getThreadPool("dispatcher");
    private CountDownLatch latch = new CountDownLatch(30);

    @Test
    public void loadDataAndSchedule() throws InterruptedException {
        System.out.println("ThreadDispatcher loadDataAndSchedule thread = "+Thread.currentThread().getName());

        for (int i = 0 ; i < 30 ; i++){
            int message = i%5 ;
            dispatcher.submit(new ThreadSchedule(message+"",latch)) ;
            System.out.println(i+"  loadDataAndSchedule thread = "+Thread.currentThread().getName());
        }
        latch.await();
    }


    static class ThreadSchedule implements Callable<String>{

        private String message ;
        private CountDownLatch latch ;

        public ThreadSchedule(String message,CountDownLatch latch) {
            this.message = message;
            this.latch = latch ;
        }

        @Override
        public String call() throws Exception {
            return Business.Factory.getBusiness().dispatchProvider(message,latch);
        }
    }


}
