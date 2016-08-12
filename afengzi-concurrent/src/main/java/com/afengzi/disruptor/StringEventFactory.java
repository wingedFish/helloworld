package com.afengzi.disruptor;

import reactor.jarjar.com.lmax.disruptor.EventFactory;

/**
 * Created by lixiuhai on 2016/8/12.
 */
public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
