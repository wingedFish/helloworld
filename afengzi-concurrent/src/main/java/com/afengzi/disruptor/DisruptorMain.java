package com.afengzi.disruptor;

import com.afengzi.concurrent.factory.AfThreadFactory;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * Created by winged fish on 2016/8/12.
 */
public class DisruptorMain {
    public static void main(String[] args) throws Exception {
        ThreadFactory factory = new AfThreadFactory("disruptorT",true);

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 64;

        // Construct the Disruptor
        Disruptor<StringEvent> disruptor = new Disruptor<StringEvent>(new StringEventFactory(),bufferSize,factory);

//        for (int i = 0 ; i < 10 ; i++){
//            // Connect the handler
//            final int finalI = i;
//            disruptor.handleEventsWith(new EventHandler<StringEvent>() {
//                @Override
//                public void onEvent(StringEvent event, long sequence, boolean endOfBatch) throws Exception {
//                    System.out.println(Thread.currentThread().getName()+"----> onEvent: " + event+"  --> i "+ finalI);
//                    Thread.sleep(10000);
//
//                }
//            });
//
//        }

        WorkHandler[] workHandlers = new WorkHandler[10];
        for (int i = 0 ; i<workHandlers.length ;i++){
            final int finalI = i;
            workHandlers[i] = new WorkHandler() {
                @Override
                public void onEvent(Object event) throws Exception {
                    System.out.println(Thread.currentThread().getName()+"----> onEvent: " + event+"  --> i "+ finalI);
                    Thread.sleep(10000);
                }
            };
        }
        disruptor.handleEventsWithWorkerPool(workHandlers);


        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(new String(bb.array())), bb);
            System.out.println(Thread.currentThread().getName()+" ---> publishEvent l "+l);
//            Thread.sleep(1000);
        }
    }

}
