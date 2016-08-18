package com.afengzi.reactor.rx;

import com.afengzi.delayqueue.DelayQueueWrapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by winged fish on 2016/8/11.
 */
@Service
public class QueryQueueReactiveSchedule extends QueryQueueReactive {

    @Autowired
    private DelayQueueWrapper delayQueue1;
    @Autowired
    private DelayQueueWrapper delayQueue2;
    @Autowired
    private DelayQueueWrapper delayQueue3;

    @Scheduled(fixedDelay = 2000)
    public void queryFromQueue1() {
        System.out.println(Thread.currentThread().getName()+ " ->  start execute queryFromQueue1 -- "+delayQueue1.getQueueSize());
        super.queryPayResult(delayQueue1, delayQueue2);
    }
//    @Scheduled(fixedRate = 5000)
    public void queryFromQueue2() {
        System.out.println(Thread.currentThread().getName()+ " ->  start execute queryFromQueue2");
        super.queryPayResult(delayQueue2, delayQueue3);
    }
//    @Scheduled(fixedRate = 1000)
    public void queryFromQueue3() {
        System.out.println(Thread.currentThread().getName()+ " ->  start execute queryFromQueue3");
        super.queryPayResult(delayQueue3, null);
    }


    @Override
    public boolean isSwitchQuery() {
        return true;
    }

    @Override
    public boolean isNeedQuery(String orderId) {
        boolean result = NumberUtils.toInt(orderId)%2==0;
        System.out.println(Thread.currentThread().getName() + " -->isNeedQuery orderId = [" + orderId + "] , result : "+result);
        return result;
    }

    @Override
    public boolean doQueryAndExecute(String orderId) {
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " --> doQueryAndExecute orderId : "+orderId);
        return NumberUtils.toInt(orderId)<40;
    }

}
