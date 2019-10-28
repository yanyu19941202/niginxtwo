package com.example.nginxtwo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ActiveThread implements ApplicationRunner {

    @Autowired
    private MqThread mqThread;

    @Override
    public void run(ApplicationArguments args) {


        System.out.println("启动线程....");
        mqThread.start();

    }
}