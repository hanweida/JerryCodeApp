package com.jerry.dubbo.spring.provider;

import com.jerry.dubbo.spring.service.DemoService;
import org.apache.dubbo.rpc.RpcContext;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        System.out.println("say hello");
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
