package com.afengzi.reactor.performance.net;

/**
 * Created by lixiuhai on 2016/12/27.
 */
public class FillCard {
    public String  fillCard(String cardId){
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("========== step 4 thread " +Thread.currentThread().getName()+ " cardId = [" + cardId + "]"+"\n\n");
        return cardId;
    }
}
