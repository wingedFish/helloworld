package com.afengzi.items.proxy.dynamic;

/**
 * Created by winged fish on 2015/8/26.
 */
public class Civics implements ElectChairman {
    @Override
    public boolean vote(String executorName) {
        System.out.println(executorName+"��ʹ����ѡ��Ȩ��");
        return true ;
    }
}
