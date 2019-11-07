package com.jerry.dubbo.spring.consumer;

import com.jerry.dubbo.spring.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ServiceLoader;

public class Application {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/dubbo-consumer.xml");
        context.start();
        DemoService demoService = context.getBean("demoService", DemoService.class);
        String hello = demoService.sayHello("world");
        System.out.println("result: " + hello);
    }
}
