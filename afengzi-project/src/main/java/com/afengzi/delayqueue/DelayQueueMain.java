package com.afengzi.delayqueue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by winged fish on 2016/8/12.
 */
public class DelayQueueMain {
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
