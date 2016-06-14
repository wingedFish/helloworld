package com.afengzi.hystrix;

import com.netflix.hystrix.*;
import org.junit.Test;

/**
 * Created by lixiuhai on 2016/6/14.
 */
public class HelloHystrix {

    @Test
    public void helloWorldCommand() {
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("klov");
        String result = helloWorldCommand.execute();
        System.out.println("【HelloWorldCommand】 result = "+result);
    }

    class HelloWorldCommand extends HystrixCommand<String> {

        protected HelloWorldCommand(String name) {
            super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(name)));
        }

        @Override
        protected String run() throws Exception {
            System.out.println("run success " + Thread.currentThread().getName());
            return "run success " + Thread.currentThread().getName();
        }
    }

    @Test
    public void executeIsolateThreadPool() {
        HelloCommandIsolateThreadPool helloCommandIsolateThreadPool = new HelloCommandIsolateThreadPool("klov");
        helloCommandIsolateThreadPool.execute();
    }


    class HelloCommandIsolateThreadPool extends HystrixCommand<String> {

        protected HelloCommandIsolateThreadPool(String name) {
            super(HystrixCommand.Setter.
                    //设置GroupKey 用于dashboard 分组展示
                            withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
                            //设置commandKey 用户隔离线程池，不同的commandKey会使用不同的线程池
                    .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + name))
                            //设置线程池名字的前缀，默认使用commandKey
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("hello$Pool" + name))
                            //设置线程池相关参数
                    .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                            .withCoreSize(15)
                            .withMaxQueueSize(10)
                            .withQueueSizeRejectionThreshold(2))
                            //设置command相关参数
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            //是否开启熔断器机制
                            .withCircuitBreakerEnabled(true)
                                    //舱壁隔离策略
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                                    //circuitBreaker打开后多久关闭
                            .withCircuitBreakerSleepWindowInMilliseconds(5000)));
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(300);
            System.out.println("run success " + Thread.currentThread().getName());
            return "run success " + Thread.currentThread().getName();
        }

        @Override
        protected String getFallback() {
            System.out.println("fall back..." + Thread.currentThread().getName());
            return "fall back..." + Thread.currentThread().getName();
        }
    }


    @Test
    public void executeIsolateSemaphore() {
        HelloCommandIsolateSemaphore helloCommandIsolateSemaphore = new HelloCommandIsolateSemaphore("klov", 10);
        helloCommandIsolateSemaphore.execute();
    }

    class HelloCommandIsolateSemaphore extends HystrixCommand<String> {

        protected HelloCommandIsolateSemaphore(String key, int semaphoreCount) {
            super(HystrixCommand.Setter
                    //设置GroupKey 用于dashboard 分组展示
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello"))
                            //设置CommandKey 用于Semaphore分组，相同的CommandKey属于同一组隔离资源
                    .andCommandKey(HystrixCommandKey.Factory.asKey("hello" + key))
                            //设置隔离级别：Semaphore
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                            //是否开启熔断器机制
                            .withCircuitBreakerEnabled(true)
                                    //舱壁隔离策略
                            .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                                    //circuitBreaker打开后多久关闭
                            .withCircuitBreakerSleepWindowInMilliseconds(5000)));
        }

        @Override
        protected String run() throws Exception {
            Thread.sleep(300);
            System.out.println("run success " + Thread.currentThread().getName());
            return "run success " + Thread.currentThread().getName();
        }

        @Override
        protected String getFallback() {
            System.out.println("fall back..." + Thread.currentThread().getName());
            return "fall back..." + Thread.currentThread().getName();
        }
    }
}

