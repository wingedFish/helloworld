package com.afengzi.rx;

import com.afengzi.util.LoggerHelper;
import com.netflix.hystrix.*;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

/**
 * Created by lixiuhai on 2016/7/8.
 */
public class ChongzhiRx {

    @Test
    public void recharge(){

        prepareFill();
    }



    public String prepareFill(){
        Observable<String> prepareObservable = new PrepareFillCommand("telecom","telecom").toObservable();
        prepareObservable.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                LoggerHelper.print("findFill....."+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.print("onError....."+Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                beginFill(s);
                LoggerHelper.print("onNext....."+Thread.currentThread().getName());
            }
        });

        return Thread.currentThread().getName()+"-----prepare";
    }

    public String findFill(){
        LoggerHelper.print("findFill.....");
        return Thread.currentThread().getName()+"-findFill";
    }

    public String beginFill(String info){
        LoggerHelper.print("beginFill....."+info);
        return Thread.currentThread().getName()+"-beginFill";
    }


    static class PrepareFillCommand extends HystrixCommand{

        public PrepareFillCommand(String commandGroupKey,String threadPoolKey) {
            super(Setter
                            .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroupKey))
                            .andCommandKey(HystrixCommandKey.Factory.asKey(commandGroupKey))

                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerEnabled(false)
                                    .withExecutionTimeoutInMilliseconds(300)
                                            //isolate strategy
                                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))

                                    //thread pool setter
                            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(threadPoolKey))
                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)
                            ) );
        }

        @Override
        protected Object run() throws Exception {
            LoggerHelper.print("prepareFill.....");
            return Thread.currentThread().getName()+"-prepareFill";
        }
    }
}
