package com.test.spring;

import javax.sql.DataSource;

public class AccountDao {
    private DataSource dataSource;

    public AccountDao(DataSource dataSource) {
        this.dataSource=dataSource;
    }

    public void saveMoney() {
        System.out.println("利用dataSource模拟存钱操作");
    }
}
