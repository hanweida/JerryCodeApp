package com.mycode.purchasing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 代购辅助项目
 */
@SpringBootApplication
@EnableScheduling
public class PurchasingApplication {

  public static void main(String[] args) {
    SpringApplication.run(PurchasingApplication.class, args);
  }

}
