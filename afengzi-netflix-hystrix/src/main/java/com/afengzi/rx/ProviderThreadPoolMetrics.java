package com.afengzi.rx;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolMetrics;

/**
 * Created by lixiuhai on 2016/7/12.
 */
public class ProviderThreadPoolMetrics {

    static ProviderThreadPoolMetrics INSTANCE = new ProviderThreadPoolMetrics();

    public static ProviderThreadPoolMetrics getInstance(){
        return INSTANCE ;
    }


    public int getActiveCount(String threadPoolKey){
        HystrixThreadPoolKey key = getThreadPoolKey(threadPoolKey);
        HystrixThreadPoolMetrics threadPoolMetrics =  HystrixThreadPoolMetrics.getInstance(key);
        if (threadPoolMetrics==null){
            return 0 ;
        }
        return threadPoolMetrics.getCurrentActiveCount()==null?0:threadPoolMetrics.getCurrentActiveCount().intValue();
    }

    private HystrixThreadPoolKey getThreadPoolKey(String threadPoolKey){
        return HystrixThreadPoolKey.Factory.asKey(threadPoolKey) ;
    }
}
