package com.afengzi.concurrent.unit;

import org.apache.log4j.Logger;

import java.util.concurrent.*;

/**
 * Created by winged fish on 2015/7/28.
 */
public class AfCanTimeoutThread {

    private final static Logger log = Logger.getLogger(AfCanTimeoutThread.class);

    public static void main(String[] args) {
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(5000);
//        ExecutorService executorService = new AfThreadPoolExecutor(5, 10, 5, workQueue, "timeoutPool", "timeoutThread");
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Boolean> future = executorService.submit(new TimeoutThread());
        try {
            future.get(5*100,TimeUnit.MILLISECONDS);
            System.out.println("##############");
        } catch (InterruptedException e) {
           log.error(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    static class TimeoutThread implements Callable<Boolean> {

        @Override
        public Boolean call() {
            try {
                Thread.sleep(5*60*1000);
                System.out.println("$$$$$$$$$$$$");
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            return true;
        }
    }
}
