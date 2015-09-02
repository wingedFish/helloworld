package com.afengzi.items.proxy.dynamic;

/**
 * Created by lixiuhai on 2015/8/26.
 */
public class Civics implements ElectChairman {
    @Override
    public boolean vote(String executorName) {
        System.out.println(executorName+"行使公民选举权利");
        return true ;
    }
}
