package com.mycode.purchasing.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class AutoDouYuTask6 {
    Logger logger = LoggerFactory.getLogger(AutoDouYuTask6.class);

    @Resource
    private DouYu61 douYu61;
    @Resource
    private DouYu62 douYu62;
    @Resource
    private DouYu63 douYu63;

    //@Scheduled(cron = "51 59 23 * * ?")
    //@Scheduled(cron = "*/9 * * * * ?")
    public void excute() throws Exception {
        logger.info("excute");
        long starTime = System.currentTimeMillis();
        for(int i = 0;;){
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        douYu61.excute();
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
            if(endTime - starTime >= 45000){
                logger.info((endTime - starTime)+"");
                break;
            }
        }
    }


    @Scheduled(cron = "51 59 23 * * ?")
    //@Scheduled(cron = "*/9 * * * * ?")
    public void excute2() throws Exception {
        logger.info("excute");
        long starTime = System.currentTimeMillis();
        for(int i = 0;;){
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        douYu62.excute();
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
            if(endTime - starTime >= 45000){
                logger.info((endTime - starTime)+"");
                break;
            }
        }
    }

    @Scheduled(cron = "51 59 23 * * ?")
    //@Scheduled(cron = "*/9 * * * * ?")
    public void excute3() throws Exception {
        logger.info("excute");
        long starTime = System.currentTimeMillis();
        for(int i = 0;;){
            ExecutorService executor = Executors.newFixedThreadPool(10);
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        douYu63.excute();
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
            if(endTime - starTime >= 45000){
                logger.info((endTime - starTime)+"");
                break;
            }
        }
    }
}
