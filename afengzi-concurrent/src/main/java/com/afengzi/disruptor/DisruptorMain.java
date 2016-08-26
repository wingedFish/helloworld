package com.afengzi.disruptor;

import com.afengzi.concurrent.factory.AfThreadFactory;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;

/**
 * Created by winged fish on 2016/8/12.
 */
public class DisruptorMain {

    private static final EventTranslatorOneArg<StringEvent, String> TRANSLATOR =
            new EventTranslatorOneArg<StringEvent, String>()
            {
                public void translateTo(StringEvent event, long sequence, String value)
                {
                    event.setValue(value);
                }
            };


    public static void main(String[] args) throws Exception {
        ThreadFactory factory = new AfThreadFactory("disruptorT",true);

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 64;

        // Construct the Disruptor
        Disruptor<StringEvent> disruptor = new Disruptor<StringEvent>(new StringEventFactory(),bufferSize,factory);


        //set handler
        disruptor.handleEventsWithWorkerPool(getHandlers());


        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();

        //publish event
        for (long index = 0; true; index++) {
            ringBuffer.publishEvent(TRANSLATOR, "winged"+index);
            System.out.println(Thread.currentThread().getName()+" ---> publishEvent --> "+index);
//            Thread.sleep(1000);
        }


    }

    private static WorkHandler[] getHandlers(){
        WorkHandler[] workHandlers = new WorkHandler[10];
        for (int i = 0 ; i<workHandlers.length ;i++){
            workHandlers[i] = new WorkHandler<StringEvent>() {
                @Override
                public void onEvent(StringEvent event) throws Exception {
                    System.out.println(Thread.currentThread().getName()+" ****====> onEvent: " + event.getValue());
                    Thread.sleep(10000);
                }
            };
        }
        return workHandlers;
    }




}
