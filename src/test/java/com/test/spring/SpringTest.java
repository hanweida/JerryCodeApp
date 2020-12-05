package com.test.spring;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

@Configuration
@Import({ServiceConfig.class,DaoConfig.class})
public class SpringTest {
    @Bean
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringTest.class);
        AccountService service = ac.getBean(AccountService.class);
        service.saveMoney();
    }
}
