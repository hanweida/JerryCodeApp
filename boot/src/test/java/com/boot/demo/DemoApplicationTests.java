package com.boot.demo;

import com.boot.demo.user.dao.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  @Resource
  UserDao userDao;

  @Test
  public void contextLoads() {
    userDao.insert();
  }

}
