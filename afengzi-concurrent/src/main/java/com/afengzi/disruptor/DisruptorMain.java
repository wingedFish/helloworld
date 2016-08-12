package com.afengzi.disruptor;

import reactor.jarjar.com.lmax.disruptor.BusySpinWaitStrategy;
import reactor.jarjar.com.lmax.disruptor.RingBuffer;
import reactor.jarjar.com.lmax.disruptor.dsl.Disruptor;
import reactor.jarjar.com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lixiuhai on 2016/8/12.
 */
public class DisruptorMain {
    public static void main(String[] args) throws Exception {
        // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<StringEvent> disruptor = new Disruptor(new StringEventFactory()
                , bufferSize
                , executor
                , ProducerType.SINGLE
                , new BusySpinWaitStrategy()
        );

        // Connect the handler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(new String(bb.array())), bb);
            Thread.sleep(1000);
        }
    }

}
