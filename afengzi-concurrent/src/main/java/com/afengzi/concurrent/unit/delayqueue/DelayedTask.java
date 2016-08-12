package com.afengzi.concurrent.unit.delayqueue;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixiuhai on 2015/8/11.
 */
public class DelayedTask implements Delayed,Runnable {
    private final long trigger;

    public DelayedTask(long triggerMillsecond) {
        System.out.println("init now : "+new Date());
        this.trigger = System.currentTimeMillis() + TimeUnit.MILLISECONDS.toMillis(triggerMillsecond);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == null) {
            throw new IllegalArgumentException("parameter must not null !");
        }
        if (!(o instanceof DelayedTask)) {
            throw new ClassCastException("parameter is not class DelayedTask !");
        }
        DelayedTask that = (DelayedTask) o;
        if (this.trigger == that.trigger) {
            return 0;
        }
        return this.trigger > that.trigger ? 1 : -1;
    }

    @Override
    public void run() {
        System.out.println("# # "+getClass().getName());
    }

    public void doThread(ExecutorService service){
        service.execute(this);
    }

    public void doTask(){
        System.out.println("# # "+this+" ## "+(System.currentTimeMillis()-trigger));
        System.out.println("# # "+this+" $$$  "+new Date());
    }
}
