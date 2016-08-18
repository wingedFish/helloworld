package com.afengzi.helloworld.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by winged fish on 2016/1/17.
 *
 */
public class FutureCancel {

    private static final long SHORT_SLEEP_MILLIS = 2*1000;
    private static final long LONG_SLEEP_MILLIS = 20*1000;
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    CountDownLatch latch = new CountDownLatch(10);

    @Test
    public void test() throws InterruptedException {
        System.out.println("@@@@@@ thread  %s active thread count :  " + poolExecutor.getActiveCount());
        for (int i = 0; i < 10; i++) {
            Future<String> future = poolExecutor.submit(new TimeOutThread());
        }
        System.out.println(" ###### thread : "+Thread.currentThread().getName() + " active thread count : " + poolExecutor.getActiveCount());
        latch.await();
    }
    @Test
    public void testTimeoutAndCancel() throws InterruptedException {
        executeTimeout(true);
    }

    @Test
    public void testTimeout() throws InterruptedException {
        executeTimeout(false);
    }

    private void executeTimeout(boolean isCancle) throws InterruptedException {
        List<Future> futureList = new ArrayList<Future>();
        for (int i = 0; i < 10; i++) {
            Future<String> future = poolExecutor.submit(new TimeOutThread());
            futureList.add(future);
        }
        System.out.println("##### thread : "+Thread.currentThread().getName() + " active thread count : " + poolExecutor.getActiveCount());
        for (Future future : futureList){
            try {
                future.get(3, TimeUnit.SECONDS);
            } catch (ExecutionException e) {
                future.cancel(isCancle);
                System.out.println(" $$$ thread "+ Thread.currentThread().getName()+" ExecutionException error!"+e);
            } catch (TimeoutException e) {
                future.cancel(isCancle);
                System.out.println(" $$$ thread " + Thread.currentThread().getName() + " TimeoutException error!" + e);
            }
        }
        latch.await();
    }


    class TimeOutThread implements Callable<String> {

        @Override
        public String call() {
            try {
                Thread.sleep(LONG_SLEEP_MILLIS);
            } catch (InterruptedException e) {
                System.out.println(String.format(" !!!! thread : %s sleep error!", Thread.currentThread().getName()) + e);
            }
            System.out.println(Thread.currentThread().getName() + " active thread count : " + poolExecutor.getActiveCount());
            latch.countDown();
            return Thread.currentThread().getName();
        }
    }
}
