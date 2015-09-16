package com.afengzi.demo.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by klov on 2015/9/16.
 */
public class LoadSpringConfig {

    public static void load(String path){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path) ;
    }
}
