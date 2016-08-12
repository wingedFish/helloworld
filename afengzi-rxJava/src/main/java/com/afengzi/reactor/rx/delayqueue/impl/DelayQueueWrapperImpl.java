package com.afengzi.reactor.rx.delayqueue.impl;

import com.afengzi.reactor.rx.DelayQueueWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.DelayQueue;

/**
 * Created by lixiuhai on 2016/8/11.
 */
public class DelayQueueWrapperImpl implements DelayQueueWrapper {
    private final static Logger log = Logger.getLogger(DelayQueueWrapperImpl.class);
    //无界序列
    //default capacity is 11
    private DelayQueue<DelayElement> queue = new DelayQueue<DelayElement>();
    //第一队列是否是n分钟前的订单
    private int delayTime;
    //队列最大长度
    private long maxQueueSize = 1000;


    public boolean addQueue(String value) {
        System.out.println(Thread.currentThread().getName()+" -- > value : "+value);
        return addValue(queue, delayTime, value);
    }


    public String pollQueueValue() {
        return getValue(queue);
    }


    public int getQueueSize() {
        return queue.size();
    }

    private boolean addValue(DelayQueue<DelayElement> queue, long delayTime, String value) {
        if (StringUtils.isBlank(value)) {
            return false;
        }
        if (queue.size() > maxQueueSize) {
            log.error("delayTime queue size greater than maxQueueSize, add failure ! delayTime : " + delayTime);
            return false;
        }
        return queue.add(new DelayElement(delayTime, value));
    }

    private String getValue(DelayQueue<DelayElement> queue) {
        DelayElement element = null;
        try {
            System.out.println(Thread.currentThread().getName() + " -->queue size "+queue.size());
            element = queue.take();
            System.out.println(Thread.currentThread().getName() + " -->queue size "+queue.size());
        } catch (InterruptedException e) {
            log.error("DelayQueueWrapper getValue queue.take error!",e);
        }
        if (element == null) {
            return StringUtils.EMPTY;
        }
        return element.getValue();
    }


    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }


    public void setMaxQueueSize(long maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }
}
