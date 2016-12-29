package com.afengzi.reactor.performance.dao;

/**
 * Created by lixiuhai on 2016/12/27.
 */
public class CardDao {
    public String getCardId(String cardId){
        System.out.println("========== step 2 thread " +Thread.currentThread().getName()+ " cardId = [" + cardId + "]");
        return cardId;
    }

}
