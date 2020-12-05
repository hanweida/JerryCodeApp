package com.test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public AccountDao accountDao(){
        return new AccountDao(dataSource);
    }
}
