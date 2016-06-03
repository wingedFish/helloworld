package com.afengzi.hystrix;

import com.netflix.hystrix.*;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

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
                        .withCoreSize(15)
                        .withMaxQueueSize(10)
                        .withQueueSizeRejectionThreshold(2))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerSleepWindowInMilliseconds(5000)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(300);
        System.out.println("run success "+Thread.currentThread().getName());
        return "run success "+Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        System.out.println("fall back..."+Thread.currentThread().getName());
        return "fall back..."+Thread.currentThread().getName();
    }

    private static void reactiveExecution(){
        Observable<String> observable = new HelloworldCommand("xhl").toObservable();
        observable.subscribe(new Action1<String>() {
            //first call
            @Override
            public void call(String s) {
                System.out.println("reactiveExecution call ..."+Thread.currentThread().getName());
            }
        });
        observable.subscribe(new Observer<String>() {
            //third call
            @Override
            public void onCompleted() {
                System.out.println("reactiveExecution onCompleted ..."+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("reactiveExecution onError ..."+Thread.currentThread().getName());
            }

            //second call
            @Override
            public void onNext(String s) {
                System.out.println("reactiveExecution onNext ..."+Thread.currentThread().getName());
            }
        });
    }

    private static void synExecution(){
            HelloworldCommand helloworldCommand = new HelloworldCommand("KLOV");
            helloworldCommand.queue();
            String result = helloworldCommand.execute();
//            System.out.println(i+" result ="+result);
    }


    public static void main(String[] args) {
        for (int i = 0 ; i< 15 ; i++){
//            reactiveExecution();
            HelloworldCommand helloworldCommand = new HelloworldCommand("KLOV");
            helloworldCommand.execute();
//            String result = helloworldCommand.execute();
        }
    }
}
