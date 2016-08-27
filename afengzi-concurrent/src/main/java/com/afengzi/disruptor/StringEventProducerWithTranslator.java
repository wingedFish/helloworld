package com.afengzi.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created by winged fish on 2016/8/12.
 */
public class StringEventProducerWithTranslator {
    private final RingBuffer<StringEvent> ringBuffer;

    public StringEventProducerWithTranslator(RingBuffer<StringEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    private static final EventTranslatorOneArg<StringEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<StringEvent, ByteBuffer>()
            {
                public void translateTo(StringEvent event, long sequence, ByteBuffer bb)
                {
                    event.setValue(new String(bb.array()));
                }
            };

    public void onData(ByteBuffer bb)
    {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }

}
