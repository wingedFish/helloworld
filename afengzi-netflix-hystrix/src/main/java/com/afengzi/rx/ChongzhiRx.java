package com.afengzi.rx;

import com.afengzi.rx.io.SimulateRecharge;
import com.afengzi.util.LoggerHelper;
import com.netflix.hystrix.*;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by lixiuhai on 2016/7/8.
 */
public class ChongzhiRx {

    private CountDownLatch latch = new CountDownLatch(0);
    @Test
    public void recharge(){
        for (int i = 0 ; i < 20 ; i++){
            int count = ProviderThreadPoolMetrics.getInstance().getActiveCount("bjMobile") ;
            if (count>0){
                System.out.println("================ activeCount : "+count);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
               if (i < 11){
                   prepareFill(i%2+1,SimulateRecharge.Factory.getINSTANCE_BJMobile());
               }else {
                   prepareFill(i%2+1,SimulateRecharge.Factory.getInstanceTelecom());
               }
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    public String prepareFill(int venderId, final SimulateRecharge recharge){
        Observable<String> prepareObservable = new PrepareFillCommand(recharge,venderId+"").toObservable();
        prepareObservable.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                LoggerHelper.print(Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e) {
                LoggerHelper.print(Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
//                try {
//                    recharge.beginFill();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                LoggerHelper.print("onNext....."+Thread.currentThread().getName()+s);
                latch.countDown();
                LoggerHelper.print("latchCount " +latch.getCount()+"");
            }
        });

        return Thread.currentThread().getName()+"-----prepare";
    }




    static class PrepareFillCommand extends HystrixCommand<String>{
        private SimulateRecharge recharge ;
        public PrepareFillCommand(SimulateRecharge recharge,String venderId) {
            super(Setter
                            .withGroupKey(HystrixCommandGroupKey.Factory.asKey("Recharge"))
                            .andCommandKey(HystrixCommandKey.Factory.asKey(venderId+""))

                            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerEnabled(false)
                                    .withExecutionTimeoutInMilliseconds(6000)
                                            //isolate strategy
                                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD))

                                    //thread pool setter
                            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(venderId+""))
                            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(10)
                                    .withKeepAliveTimeMinutes(5)
                                    .withMaxQueueSize(-1)
                                    .withQueueSizeRejectionThreshold(5))

            );
            this.recharge = recharge ;
        }

        @Override
        protected String run() throws Exception {
//            return recharge.prepareFill();
            System.out.println("=============run");
            return Thread.currentThread().getName()+"-----run";
        }
    }
}
