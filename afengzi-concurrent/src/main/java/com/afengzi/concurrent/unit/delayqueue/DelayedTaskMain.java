package com.afengzi.concurrent.unit.delayqueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lixiuhai on 2015/8/11.
 */
public class DelayedTaskMain {

    public static void main(String[] args) {
        DelayQueue delayQueue = new DelayQueue();
        for (int i = 0 ; i<20 ; i++){
            System.out.println("# delayed task produce !");
            DelayedTask delayedTask = new DelayedTask(3000) ;
            delayQueue.add(delayedTask) ;
        }
        DelayedTaskConsumer consumer = new DelayedTaskConsumer(delayQueue) ;

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(consumer);

    }
}
