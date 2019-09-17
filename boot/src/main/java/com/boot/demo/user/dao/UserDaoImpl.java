package com.boot.demo.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    private static final String insertSql = "INSERT INTO test (name) values (?)";

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert() {
        String[] nameArr = {"张三", "李四", "王二麻子", "赵钱", "孙李"};

        for(int i = 0; i < 100000; i++){
           long start = System.currentTimeMillis();
            List<Object[]> nameList = new ArrayList<Object[]>();
            for(int j = 0; j < 5000; j++){
                int mod = j%5;
                Object[] objects = {nameArr[mod]};
                nameList.add(objects);
            }
            System.out.println("循环执行时间" + (System.currentTimeMillis() - start));
            jdbcTemplate.batchUpdate(insertSql, nameList);
            System.out.println("执行时间" + (System.currentTimeMillis() - start));
        }
    }
}

interface A {}

