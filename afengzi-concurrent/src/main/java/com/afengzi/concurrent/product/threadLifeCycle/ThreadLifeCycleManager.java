package com.afengzi.concurrent.product.threadLifeCycle;

/**
 * Created by lixiuhai on 2015/9/2.
 * 线程生命周期管理器
 */
public interface ThreadLifeCycleManager {
    /**registe thread*/
    public void register(DelayThread delayThread) ;
}
