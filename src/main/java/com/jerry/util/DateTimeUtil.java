package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @Class Name DateTimeUtil
 * @Author likang
 * @Create In 2013-1-15
 */
public class DateTimeUtil {
	
	/**
	 * 获取当前秒
	 * @Methods Name getCurrentSecond
	 * @Create In 2013-1-15 By likang
	 * @return int
	 */
	public static Long getCurrentSecond() {
		return System.currentTimeMillis()/1000;
	}
	
	
	public static Long getTodayZeroSecond() {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String nowStr = sdf1.format(new Date()) + " 00:00:00";
		Date now = new Date();
		try {
			now = sdf2.parse(nowStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return now.getTime()/1000;
	}
	
	/**
	 * 精确到分钟的时间 转 秒
	 * @Methods Name getDateHour2Second
	 * @Create In 2014-8-15 By likang
	 * @param date
	 * @return Long
	 */
	public static Long getDateHour2Second(Date date) {
		if (date != null) {
			return date.getTime()/1000;
		} else {
			return 0l;
		}
	}
	
	/**
	 * 精确到分钟的时间 转 日期
	 * @Methods Name parseDateHour2Date
	 * @Create In 2014-8-15 By likang
	 * @param date
	 * @return Date
	 */
	public static Date parseDateHour2Date(String date) {
		Date now = null;
		if (StringUtils.isNotBlank(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				now = sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return now;
	}

	/**
	 * 当前时间
	 * @Methods Name getNowFormatted
	 * @Create In 2013-1-28 By likang
	 * @return String
	 */
	public static String getNowFormatted() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	
    /**
     * 根据开始时间、结束时间得到两个时间段内所有的日期
     * @param start 开始日期
     * @param end   结束日期
     * @param calendarType  类型
     * @return  两个日期之间的日期
     */
    public static ArrayList<String> getDateList(Date start, Date end) {
      ArrayList<String> ret = new ArrayList();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      Date tmpDate = calendar.getTime();
      long endTime = end.getTime();
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        ret.add(sdf1.format(tmpDate));
        tmpDate = calendar.getTime();
      }
      return ret;
    }

    /**
     * 日期 增加 或 减少（月）
     * @Methods Name addMonths
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param months
     * @return Date
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }
    
    /**
     * 日期 增加 或 减少
     * @Methods Name add
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param amount
     * @param field
     * 				Calendar.YEAR;
     *   			Calendar.DATE;
     *   			Calendar.MONTH;
     * @return Date
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }
    
	
}
