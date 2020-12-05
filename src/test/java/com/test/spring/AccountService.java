package com.test.spring;

public class AccountService {
    private AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao=accountDao;
    }

    public void saveMoney() {
        accountDao.saveMoney();
    }
}
