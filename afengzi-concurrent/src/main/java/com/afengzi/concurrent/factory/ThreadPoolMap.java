package com.afengzi.concurrent.factory;

import java.util.concurrent.*;

/**
 * Created by winged fish on 2016/7/14.
 */
public class ThreadPoolMap {

    private static final ConcurrentHashMap<String, ExecutorService> threadPoolMap = new ConcurrentHashMap<String, ExecutorService>();

    public static ExecutorService getThreadPool(String poolName) {
        if (threadPoolMap.containsKey(poolName)) {
            return threadPoolMap.get(poolName);
        }
        threadPoolMap.putIfAbsent(poolName, newCachedThreadPool(poolName, 50));
        return threadPoolMap.get(poolName);
    }

    public static ExecutorService newCachedThreadPool(String poolName, int coreSize) {
        return new ThreadPoolExecutor(coreSize, coreSize,
                50, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new AfThreadFactory(poolName,false));
    }
}
