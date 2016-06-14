package com.afengzi.hystrix;

import com.afengzi.util.LoggerHelper;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;

import java.util.List;

/**
 * Created by lixiuhai on 2016/6/3.
 */
public class MonitorPlugin extends HystrixEventNotifier {

    private static MonitorPlugin instance = new MonitorPlugin();

    public static MonitorPlugin getInstance(){
        return instance;
    }

    @Override
    public void markCommandExecution(HystrixCommandKey key, HystrixCommandProperties.ExecutionIsolationStrategy isolationStrategy, int duration, List<HystrixEventType> eventsDuringExecution) {
        LoggerHelper.print(key.name()+"has bean execution!");
    }

    @Override
    public void markEvent(HystrixEventType eventType, HystrixCommandKey key) {
        LoggerHelper.print(key.name()+" happened with type : "+eventType.name());
    }
}
