package com.cw.delayqueuedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//打开定时
@EnableScheduling
public class DelayQueueDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayQueueDemoApplication.class, args);
    }

}
