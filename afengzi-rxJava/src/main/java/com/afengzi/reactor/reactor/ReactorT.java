package com.afengzi.reactor.reactor;

import org.reactivestreams.Processor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.Environment;
import reactor.core.processor.RingBufferProcessor;
import reactor.rx.Stream;
import reactor.rx.Streams;

/**
 * Created by lixiuhai on 2016/8/12.
 */
public class ReactorT {

    public static void main(String[] args) {
        Environment environment = Environment.initialize();

        Processor<Integer,Integer> processor =  RingBufferProcessor.create("test",64);
        Stream<Integer> s = Streams.wrap(processor);
        s.consume(i->System.out.println(Thread.currentThread().getName()+" -- data : "+i));
        s.consume(i->System.out.println(Thread.currentThread().getName()+" -- data : "+i));
        s.consume(i->System.out.println(Thread.currentThread().getName()+" -- data : "+i));



    }
}
