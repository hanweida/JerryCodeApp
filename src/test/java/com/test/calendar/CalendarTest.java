package com.test.calendar;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarTest {
    @Test
    public void test(){
        Calendar start = Calendar.getInstance();
        start.set(2017, 0, 1);
        Calendar end = Calendar.getInstance();
        end.set(2018, 0, 0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        while (start.compareTo(end) < 0){
            System.out.println(simpleDateFormat.format(start.getTime()));
            start.set(Calendar.DATE, start.get(Calendar.DATE)+1) ;
        }
    }

}
