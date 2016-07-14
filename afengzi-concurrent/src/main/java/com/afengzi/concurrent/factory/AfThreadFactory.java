package com.afengzi.concurrent.factory;

import org.apache.log4j.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lixiuhai on 2016/7/14.
 */
class AfThreadFactory implements ThreadFactory {
    private final static Logger log = Logger.getLogger(AfThreadFactory.class);
    final ThreadGroup group;
    final AtomicInteger threadNumber = new AtomicInteger(1);
    final String namePrefix;
    private boolean isDaemon ;

    public AfThreadFactory(String poolName,boolean isDaemon) {

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = poolName + "-";
        this.isDaemon = isDaemon ;
    }

    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
//        if (t.isDaemon())
//            t.setDaemon(false);
        t.setDaemon(isDaemon);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
