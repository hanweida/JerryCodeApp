package com.jerry.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


@Component("writeUtils")
public class WriteUtils {

    public synchronized void write(String data, String name) {
        if(!StringUtils.hasText(name)){
            name = "book";
        }
        RandomAccessFile rf = null;
        String str = data + "\t\n";
        try {
            rf = new RandomAccessFile("C:\\softwarework\\easouCode\\crawl-bid\\src\\main\\resources\\datafile\\"+name+".txt", "rw");
            byte datet[] = str.getBytes("gbk");
            rf.seek(rf.length());
            rf.write(datet);
            rf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }                                                    ExcelUtils
    }
}
