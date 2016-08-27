package com.afengzi.concurrent.product.threadPoolMonitor;

/**
 * Created by winged fish on 2016/3/8.
 */
public enum ThreadPoolNameEnum {

    EatMeat("eatMeatThreadPool","1","eat meat");

    private String threadPoolName;
    private String threadPoolType;
    private String threadPoolDesc;

    ThreadPoolNameEnum(String threadPoolName,String threadPoolDesc, String threadPoolType) {
        this.threadPoolName = threadPoolName;
        this.threadPoolDesc = threadPoolDesc;
        this.threadPoolType = threadPoolType;
    }

    public String getThreadPoolDesc() {
        return threadPoolDesc;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    public String getThreadPoolType() {
        return threadPoolType;
    }
}
