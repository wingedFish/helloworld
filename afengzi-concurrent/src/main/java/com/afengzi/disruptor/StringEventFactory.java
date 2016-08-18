package com.afengzi.disruptor;


import com.lmax.disruptor.EventFactory;

/**
 * Created by winged fish on 2016/8/12.
 */
public class StringEventFactory implements EventFactory<StringEvent> {
    @Override
    public StringEvent newInstance() {
        return new StringEvent();
    }
}
