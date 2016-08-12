package com.afengzi.reactor.rx;

/**
 * Created by lixiuhai on 2016/8/12.
 */
public interface DelayQueueWrapper {
    /**
     * 向对列1中添加
     */
    public boolean addQueue(String value);

    /**
     * 获取对列1中数据
     */
    public String pollQueueValue();

    /**
     * 获取对列1中个数
     */
    public int getQueueSize();
}
