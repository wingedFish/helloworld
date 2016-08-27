package com.afengzi.concurrent.unit.timeout;

import com.afengzi.concurrent.unit.AfThreadPoolExecutor;
import com.afengzi.concurrent.unit.TimeoutUncaughtExceptionHandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by winged fish on 2015/7/28.
 */
public class TimeoutThreadT {

    public static void main(String[] args) {
        int index = 1 ;
        TimeoutThreadT t = new TimeoutThreadT();
        AfThreadPoolExecutor executor = t.getThreadPoolExecutor();
//        ExecutorService executorService =  Executors.newScheduledThreadPool(5);
//        try {
//            executorService.awaitTermination(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        while (index < 55){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("start execute index : "+index);
            executor.execute(new DoWorker());
            index++;
        }
        System.out.println("start execute end ");
//        try {
//            System.out.println(" # "+Thread.currentThread().getName()+" -- > main thread start ");
//            Thread.sleep(1000);
//            System.out.println(" # "+Thread.currentThread().getName()+" -- > main thread end ");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private AfThreadPoolExecutor getThreadPoolExecutor(){
        return new AfThreadPoolExecutor(5,15,1,new LinkedBlockingQueue<Runnable>(55),"testPool","testThread");
    }

    public <A> A fun(){
        return null ;
    }


    static class DoWorker implements Runnable{

        @Override
        public void run() {
            Thread t = Thread.currentThread() ;
            System.out.println("######### "+t.getName());
            TimeoutThread timeoutThread = new TimeoutThread(2*1000,t) ;
            timeoutThread.start();
            timeoutThread.setUncaughtExceptionHandler(new TimeoutUncaughtExceptionHandler(t.getName()));
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (Exception e) {
               System.out.println("try exception : "+e);
//                e.printStackTrace();
            }
            System.out.println("# "+Thread.currentThread().getName()+" end !");
        }
    }


}
