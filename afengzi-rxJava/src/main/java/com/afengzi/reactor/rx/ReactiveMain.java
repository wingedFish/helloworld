package com.afengzi.reactor.rx;

import com.afengzi.delayqueue.DelayQueueWrapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by lixiuhai on 2016/8/11.
 */
public class ReactiveMain {
    @Test
    public void testSec(){
       long sec = TimeUnit.SECONDS.toSeconds(1000);
        System.out.print("sec "+sec);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");

        String[] beans = context.getBeanDefinitionNames();

        for (String bean : beans)
            System.out.println(Thread.currentThread().getName() + " -->config bean --> "+bean);

        DelayQueueWrapper delayQueue1 = (DelayQueueWrapper) context.getBean("delayQueue1");
        for (int i = 0 ; i < 80 ; i++){
            delayQueue1.addQueue(i+"");
        }
        System.out.println(Thread.currentThread().getName() +"--> delayQueue1 size "+delayQueue1.getQueueSize());
        try {
            Thread.sleep(10*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
