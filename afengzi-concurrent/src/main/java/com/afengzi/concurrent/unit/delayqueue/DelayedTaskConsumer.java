package com.afengzi.concurrent.unit.delayqueue;

import java.util.concurrent.DelayQueue;

/**
 * Created by lixiuhai on 2015/8/11.
 */
public class DelayedTaskConsumer implements Runnable {
    private final DelayQueue<DelayedTask> delayedTaskDelayQueue ;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> delayedTaskDelayQueue) {
        this.delayedTaskDelayQueue = delayedTaskDelayQueue;
    }

    @Override
    public void run() {
        System.out.println(" start nanosecond : "+System.nanoTime());
        while (!Thread.currentThread().isInterrupted()){
            DelayedTask delayedTask = delayedTaskDelayQueue.poll();
            if (delayedTask!=null){
                delayedTask.doTask();
            }else {
//                System.out.println(" poll task is null !");
            }

        }
    }
}
