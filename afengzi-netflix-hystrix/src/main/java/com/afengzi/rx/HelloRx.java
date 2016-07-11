package com.afengzi.rx;

import com.afengzi.util.LoggerHelper;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by lixiuhai on 2016/7/7.
 */
public class HelloRx {

    @Test
    public void helloTest(){
        hello();
    }

    public void hello(){
        Observable IntegerObservable =  Observable.create(new MyOnSubscribe());
        LoggerHelper.print("=====================");
        IntegerObservable.subscribeOn(Schedulers.io())
                          .observeOn(Schedulers.computation())
                         .subscribe(new MySubscriber());
    }

    public static class MyOnSubscribe implements Observable.OnSubscribe<Integer>{

        @Override
        public void call(Subscriber<? super Integer> observer) {
            try {
                if (!observer.isUnsubscribed()) {
                    for (int i = 1; i < 5; i++) {
                        System.out.println("call***********"+i);
                        observer.onNext(i);
                    }
                    observer.onCompleted();
                }
            } catch (Exception e) {
                observer.onError(e);
            }
        }
    }

    public static class MySubscriber extends Subscriber<Integer>{
        @Override
        public void onNext(Integer item) {
            System.out.println("Next: " + item);
        }

        @Override
        public void onError(Throwable error) {
            System.err.println("Error: " + error.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Sequence complete.");
        }
    }


}
