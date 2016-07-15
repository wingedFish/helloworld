package com.afengzi.concurrent.schedule;

import com.afengzi.concurrent.factory.ThreadPoolMap;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by lixiuhai on 2016/7/14.
 */
public interface Business {

    public String dispatchProvider(String message, CountDownLatch latch);

    public static class Factory {
        static final Business business = new BusinessImpl();

        public static Business getBusiness() {
            return business;
        }
    }

    static class BusinessImpl implements Business {

        @Override
        public String dispatchProvider(String message, CountDownLatch latch) {
            String result = "123";
            try {
              result = call(message,latch) ;
            } catch (InterruptedException e) {
                System.out.println("message = [" + message + "],"+e);
            } catch (ExecutionException e) {
                System.out.println("message = [" + message + "], "+e);
            }
            return result ;

        }

        private String call(String message, CountDownLatch latch) throws ExecutionException, InterruptedException {
//            System.out.println("BusinessImpl  dispatchProvider = [" + Thread.currentThread().getName() + "]");
            Future<String> result = ThreadPoolMap.getThreadPool(message).submit(new CallProvider(message, latch));
            System.out.println("BusinessImpl  dispatchProvider isDone = [" + Thread.currentThread().getName() + "]" + result.get());
//            System.out.println("BusinessImpl  dispatchProvider isDone result = [" + result.get() + "]");

            return result.get();
        }

        static class CallProvider implements Callable<String> {

            private String message;
            private CountDownLatch latch;

            public CallProvider(String message, CountDownLatch latch) {
                this.message = message;
                this.latch = latch;
            }

            @Override
            public String call() throws Exception {
                Thread.sleep(20);
                latch.countDown();
                if ("1".equals(message)){
                    throw  new RuntimeException(Thread.currentThread().getName() + "---" + message) ;
                }
                return Thread.currentThread().getName() + "---" + message;
            }
        }
    }


}
