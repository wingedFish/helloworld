package com.afengzi.concurrent.unit;

/**
 * Created by lixiuhai on 2015/7/29.
 */
public class TimeoutUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String threadName ;

    public TimeoutUncaughtExceptionHandler(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("threadName # "+t.getName());
        System.out.println("construct threadName # "+threadName);

        e.printStackTrace();
    }
}
