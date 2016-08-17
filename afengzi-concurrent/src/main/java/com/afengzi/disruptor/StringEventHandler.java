package com.afengzi.disruptor;


import reactor.jarjar.com.lmax.disruptor.EventHandler;

/**
 * Created by lixiuhai on 2016/8/12.
 */
public class StringEventHandler implements EventHandler<StringEvent> {
    @Override
    public void onEvent(StringEvent stringEvent, long l, boolean b) throws Exception {
        System.out.println("stringEvent = [" + stringEvent + "], l = [" + l + "], b = [" + b + "]");
    }
}
