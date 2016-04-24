package com.afengzi.hystrix;

import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesCommandDefault;

/**
 * Created by lixiuhai on 2016/4/24.
 */
public class HelloworldCommand extends HystrixCommand<String> {

    private String name ;

    protected HelloworldCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + name))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("hello$Pool" + name))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(10)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(20))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerSleepWindowInMilliseconds(500)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(500);
        System.out.println("run success "+Thread.currentThread().getName());
        return "run success "+Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        System.out.println("fall back..."+Thread.currentThread().getName());
        return "fall back..."+Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        for (int i = 0 ; i < 100 ; i++){
            HelloworldCommand helloworldCommand = new HelloworldCommand("KLOV");
            helloworldCommand.queue();
//            String result = helloworldCommand.execute();
//            System.out.println(i+" result ="+result);
        }
    }
}
