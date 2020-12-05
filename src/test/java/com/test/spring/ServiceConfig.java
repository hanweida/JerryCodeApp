package com.test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Autowired
    private AccountDao accountDao;

    @Bean
    public AccountService accountService() {
        return new AccountService(accountDao);
    }
}
