package com.afengzi.springbatch;

import org.springframework.boot.SpringApplication;

/**
 * Created by lixiuhai on 2016/8/10.
 */
public class SpringBatchT {
    public static void main(String [] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(
                BatchConfiguration.class, args)));
    }
}
