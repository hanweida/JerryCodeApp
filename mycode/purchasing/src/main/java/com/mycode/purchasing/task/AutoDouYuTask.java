package com.mycode.purchasing.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AutoDouYuTask {
    Logger logger = LoggerFactory.getLogger(AutoDouYuTask.class);

    @Resource
    private DouYu douYu;

    @Scheduled(cron = "50 59 23 * * ?")
    //@Scheduled(cron = "*/10 * * * * ?")
    public void excute() throws Exception {
        logger.info("excute");
        long starTime = System.currentTimeMillis();
        for(int i = 0;;){
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        douYu.excute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if(i++ < 10){
                Thread.sleep(1000);
            } else {
                Thread.sleep(100);
            }
            long endTime = System.currentTimeMillis();
            if(endTime - starTime >= 25000){
                logger.info((endTime - starTime)+"");
                break;
            }
        }
    }

    //@Scheduled(cron = "50 59 23 * * ?")
    //@Scheduled(cron = "*/10 * * * * ?")
    public void excute2() throws Exception {
        logger.info("excute");
        long starTime = System.currentTimeMillis();
        for(int i = 0;;){
            douYu.excute();
            if(i++ < 10){
                Thread.sleep(1000);
            } else {
                Thread.sleep(100);
            }
            long endTime = System.currentTimeMillis();
            if(endTime - starTime >= 25000){
                logger.info((endTime - starTime)+"");
                break;
            }
        }
    }
}
