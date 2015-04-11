package com.afengzi.mydefined;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by klov on 2015/4/9.
 */
public class MyDefinitionT {

    public static void main(String[] args) {
        initSpring();
    }
    public static void initSpring(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        Client client = (Client) context.getBean("clientId");
        System.out.print(client.getAddress());
    }
}
