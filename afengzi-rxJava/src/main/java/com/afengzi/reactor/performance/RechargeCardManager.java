package com.afengzi.reactor.performance;

import com.afengzi.reactor.performance.dao.CardDao;
import com.afengzi.reactor.performance.net.FillCard;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lixiuhai on 2016/12/27.
 */
public class RechargeCardManager {
    ExecutorService executorService = Executors.newFixedThreadPool(2, new CzThreadFactory("rx", "rxt"));

    public void recharge(String cardId) {
        Observable.create(((Observable.OnSubscribe<String>) subscriber -> {
                    System.out.println("========== step 1 thread " + Thread.currentThread().getName() + "$" + cardId);
                    String cardNo = new CardDao().getCardId(cardId);
                    subscriber.onNext(cardNo);
                })
        )
//                .observeOn(getExecuteScheduler()) //step 3 在线程池中异步执行
                .subscribeOn(getExecuteScheduler())  //step 1 step 2 step 3 step 4 都在线程池中执行
                .subscribe(result -> {
                    System.out.println("========== step 3 thread " + Thread.currentThread().getName() + "$" + cardId);
                    new FillCard().fillCard(result);
                });
    }

    public static void main(String[] args) {
/********************打印时间戳**************************/
        Runnable timestamp = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(Thread.currentThread().getName() + " time now : " + System.nanoTime() + "\n");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(timestamp).start();
/**************************提交请求*******************************/
        RechargeCardManager rechargeCardManager = new RechargeCardManager();
        for (int i = 1; i < 6; i++) {
            int finalI = i;
            rechargeCardManager.recharge(finalI + "");
            System.out.println("=========== main : " + Thread.currentThread().getName() + " args = [" + finalI + "]");
//            service.submit(() -> new RechargeCardManager().recharge(finalI +""));
        }
        try {
            Thread.sleep(10 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        service.shutdown();


    }


    private Scheduler getExecuteScheduler() {

        return Schedulers.from(executorService);
    }

    static class CzThreadFactory implements ThreadFactory {
        static final AtomicInteger poolNumber = new AtomicInteger(1);
        final ThreadGroup group;
        final AtomicInteger threadNumber = new AtomicInteger(1);
        final String namePrefix;

        CzThreadFactory(String poolName, String threadName) {
            if (StringUtils.isBlank(threadName)) {
                threadName = "thread";
            }
            if (StringUtils.isBlank(poolName)) {
                poolName = "pool";
            }
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-" +
                    poolNumber.getAndIncrement() +
                    "-" + threadName + "-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
