package com.afengzi.disruptor;

/**
 * Created by winged fish on 2016/8/12.
 */
public class StringEvent {
    private String value ;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
