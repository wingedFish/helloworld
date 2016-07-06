package com.afengzi.hystrix.plugins;

import com.afengzi.util.LoggerHelper;
import com.netflix.hystrix.HystrixInvokable;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;

/**
 * Created by lixiuhai on 2016/7/5.
 */
public class HystrixExecutionHook extends HystrixCommandExecutionHook {

    private static final HystrixExecutionHook INSTANCE = new HystrixExecutionHook();

    @Override
    public <T> void onStart(HystrixInvokable<T> commandInstance) {
        super.onStart(commandInstance);
        LoggerHelper.print("HystrixExecutionHook onStart...");
    }

    @Override
    public <T> void onThreadStart(HystrixInvokable<T> commandInstance) {
        super.onThreadStart(commandInstance);
        LoggerHelper.print("HystrixExecutionHook onThreadStart...");
    }

    public static HystrixCommandExecutionHook getInstance(){
        return INSTANCE;
    }


}
