package com.afengzi.reactor.rx;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by winged fish on 2016/8/11.
 */
public class RxJavaT {

    public static void main(String[] args) {
        filter();
    }

    public static void filter(){
        for(int i =0;i<5;i++){
          Observable<Integer> result=   Observable.just(i);
            result= result.filter((orderId) -> orderId < 4);
            result= result.filter((orderId)->orderId<3);
            result.subscribe((orderId) -> System.out.println("orderId " + orderId));
        }

    }
}
