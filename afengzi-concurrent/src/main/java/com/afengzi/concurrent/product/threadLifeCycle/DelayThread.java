package com.afengzi.concurrent.product.threadLifeCycle;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixiuhai on 2015/9/2.
 */
public class DelayThread implements Delayed {
    private Thread thread ;
    private int taskType ;
    private long triggerMillseconde;

    public DelayThread(Thread thread, int taskType, long triggerMillseconde) {
        this.thread = thread;
        this.taskType = taskType ;
        this.triggerMillseconde = System.currentTimeMillis() + TimeUnit.MILLISECONDS.toMillis(triggerMillseconde);
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(triggerMillseconde-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    public int compareTo(Delayed o) {
        if (o == null) {
            throw new IllegalArgumentException("parameter must not null !");
        }
        if (!(o instanceof DelayThread)) {
            throw new ClassCastException("parameter is not class DelayedTask !");
        }
        DelayThread that = (DelayThread) o;
        if (this.triggerMillseconde == that.triggerMillseconde) {
            return 0;
        }
        return ((DelayThread) o).triggerMillseconde > that.triggerMillseconde ? 1 : -1;
    }

    public boolean suicide(){
        thread.interrupt();
        return true ;
    }

    public int getTaskType() {
        return taskType;
    }
}
