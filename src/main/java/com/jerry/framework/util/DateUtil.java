package com.jerry.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DateUtil {
    public static String datePattern = "yyyy-MM-dd";
    public static String timePattern = datePattern + " HH:mm:ss";


    /**
     * 
     * @Methods Name getDatePattern
     * @Create In Jul 23, 2010 By likang
     * @return String
     */
    public static String getDatePattern() {
        return datePattern;
    }

    public static String getTimePattern() {
        return timePattern;
    }

    /**
     * 
     * @Methods Name getDate
     * @Create In Jul 23, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    public static final String getTodayDate() {
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        return (df.format(new Date()));
    }

    /**
     * 
     * @Methods Name convertStringToDate
     * @Create In Jul 23, 2010 By likang
     * @param aMask
     * @param strDate
     * @return date
     * @throws java.text.ParseException
     * @throws java.text.ParseException Date
     */
    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException{
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        date = df.parse(strDate);
        return (date);
    }

    /**
     * 
     * @Methods Name getTimeNow
     * @Create In Jul 23, 2010 By likang
     * @param theTime
     * @return String
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * 
     * @Methods Name getToday
     * @Create In Jul 23, 2010 By likang
     * @return
     * @throws java.text.ParseException Calendar
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }
    
    /**
     * 获取当前日期，日期格式为年月日
     * @Methods Name getCurrentDate
     * @Create In Jul 22, 2010 By likang
     * @return java.util.Date
     */
    public static Date getCurrentDate(){
    	 Date today = new Date();
         SimpleDateFormat df = new SimpleDateFormat(datePattern);
         String todayAsString = df.format(today);
         Calendar cal = new GregorianCalendar();
		 cal.setTime(convertStringToDate(todayAsString));
         return (Date)cal.getTime();
    }
    
    /**
     * 获取当前的日期，日期格式为年月日时分秒
     * @Methods Name getCurrentDateTime
     * @Create In Jul 23, 2010 By likang
     * @return java.util.Date
     */
    public static Date getCurrentDateTime(){
   	 	Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.timePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
        return (Date)cal.getTime();
   }
    
    
    /**
     * 获取当前的日期(字符串)，日期格式为年月日时分秒
     * @Methods Name getCurrentDateTimeString
     * @Create In Jul 23, 2010 By likang
     * @return java.util.Date
     */
    public static String getCurrentDateTimeString(){
   	 	Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.timePattern);
        return df.format(today);
   }
    
    /**
     * 
     * @Methods Name getCurrentSQLDate
     * @Create In Jul 23, 2010 By likang
     * @return java.sql.Date
     */
    public static java.sql.Date getCurrentSQLDate(){
   	    Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
        return (java.sql.Date)cal.getTime();
   }

    /**
     * 
     * @Methods Name getDateTime
     * @Create In Jul 23, 2010 By likang
     * @param aMask
     * @param aDate
     * @return String
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
           
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 
     * @Methods Name convertDateToString
     * @Create In Jul 23, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }
    
    /**
     * 将日期转成年月日 时分秒格式的字符串
     * @Methods Name convertDateTimeToString
     * @Create In Jul 22, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String convertDateTimeToString(Date aDate) {
        return getDateTime(timePattern, aDate);
    }
    

    /**
     * 
     * @Methods Name convertStringToDate
     * @Create In Jul 23, 2010 By likang
     * @param strDate
     * @return Date
     */
    public static Date convertStringToDate(String strDate) {
        Date aDate = null;
        try {
            String pattern = "\\d{4}[-|/]\\d{2}[-|/]\\d{2}[ ]\\d{2}[:]\\d{2}[:]\\d{2}";	
			if(strDate.matches(pattern)) {
				if(strDate.contains("/")) {
				    aDate = convertStringToDate("yyyy/MM/dd HH:mm:ss", strDate);
				} else {
				    aDate = convertStringToDate(timePattern, strDate);
				}
			} else {
				if(strDate.contains("/")) {
				    aDate = convertStringToDate("yyyy/MM/dd", strDate);
				} else {
				    aDate = convertStringToDate(datePattern, strDate);
				}
			}
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return aDate;
    }

    /**
     * 日期 增加 或 减少（日）
     * @Methods Name addDays
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }
    
    public static Date addMins(Date date, int mins) {
        return add(date, mins, Calendar.MINUTE);
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
     * 日期 增加 或 减少（年）
     * @Methods Name addYears
     * @Create In Jul 27, 2010 By likang
     * @param date
     * @param months
     * @return Date
     */
    public static Date addYears(Date date, int months) {
        return add(date, months, Calendar.YEAR);
    }
    
    /**
     * 日期 增加 或 减少（周）
     * @Methods Name addYears
     * @Create In Jul 27, 2010 By likang
     * @param date
     * @param months
     * @return Date
     */
    public static Date addWeeks(Date date, int months) {
        return add(date, months, Calendar.WEEK_OF_YEAR);
    }

    public static Date addSecond(Date date, int seconds) {
        return add(date, seconds, Calendar.SECOND);
    }

    /**
     * 获取date 的 0点
     * @param date
     * @return
     */
    public static Date getDateZeroTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return  cal.getTime();
    }

    /**
     * 获取date 的 23:59:59点
     * @param date
     * @return
     */
    public static Date getDateMaxTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 59);
        return  cal.getTime();
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
    
    /**
     * 取得日期所在月的最后一天
     * @Methods Name getLastDateOfMonth
     * @Create In 2014-4-11 By likang
     * @param date
     * @return Date
     */
	public static Date getLastDateOfMonth(Date date){
	    GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
	    gc.setTime(date);
	    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
	    int maxDaysOfMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
	    gc.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
	    return gc.getTime();
    }
	
	/**
	 * 取得日期所在月的第一天
	 * @Methods Name getFirstDateOfMonth
	 * @Create In 2014-4-11 By likang
	 * @param date
	 * @return Date
	 */
	public static Date getFirstDateOfMonth(Date date){
	    GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
	    gc.setTime(date);
	    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
	    int maxDaysOfMonth = gc.getActualMinimum(Calendar.DAY_OF_MONTH);
	    gc.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
	    return gc.getTime();
    }
	
    /**
     * 是否是闰年
     * @Methods Name isLeapYear
     * @Create In Jul 23, 2010 By likang
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {   
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);   
    }  
    
    
    /**  
     * 获取某年某月的最后一天  
     * @Methods Name getLastDayOfMonth
     * @Create In Jul 23, 2010 By likang
     * @param year 年  
     * @param month 月  
     * @return 最后一天  
     */  
	  private int getLastDayOfMonth(int year, int month) {   
	         if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
	                   || month == 10 || month == 12) {   
	               return 31;   
	         }   
	         if (month == 4 || month == 6 || month == 9 || month == 11) {   
	               return 30;   
	         }   
	         if (month == 2) {   
	               if (isLeapYear(year)) {   
	                   return 29;   
	               } else {   
	                   return 28;   
	               }   
	         }   
	         return 0;   
	  } 
	  
	  /**
	   * 获得当前毫秒数
	   * @Methods Name getTimeMillis
	   * @Create In Sep 13, 2010 By Administrator
	   * @return long
	   */
	  public static long getTimeMillis() {
		return System.currentTimeMillis();
	  }
 
	  
	  /**
	   * 两个日期之间相差天数
	   * @Methods Name daysBetweenTwoDate
	   * @Create In 2014-3-4 By likang
	   * @param begin
	   * @param end
	   * @return int
	   */
	  public static int daysBetweenTwoDate(String begin,String end){  
		  return daysBetweenTwoDate(convertStringToDate(begin),convertStringToDate(end));
      } 
	  
	  /**
	   * 两个日期之间相差天数
	   * @Methods Name daysBetweenTwoDate
	   * @Create In 2014-3-4 By 005
	   * @param begin
	   * @param end
	   * @return int
	   */
	  public static int daysBetweenTwoDate(Date begin,Date end){  
          int days = (int)((end.getTime() - begin.getTime())/(24*60*60*1000));  
          return days;  
      }  

	  /**
	   * 两个日期间的所有日期
	   * @Methods Name getDates
	   * @Create In 2014-3-4 By 005
	   * @param p_start
	   * @param p_end
	   * @return List<Date>
	   */
	  public static List<Date> getDates(Date begin,Date end) {
		  Calendar p_start = Calendar.getInstance();
		  p_start.setTime(begin);
		  Calendar p_end = Calendar.getInstance();
		  p_end.setTime(end);
	      List<Date> result = new ArrayList<Date>();
	      Calendar temp = Calendar.getInstance();
	      temp.setTime(begin);
	      result.add(temp.getTime());
	      temp.add(Calendar.DAY_OF_YEAR, 1);
	      while (temp.before(p_end)) {
	          result.add(temp.getTime());
	          temp.add(Calendar.DAY_OF_YEAR, 1);
	      }
	      if (begin.compareTo(end) != 0) {
		      result.add(p_end.getTime());
	      }
	      return result;
	  }

    /**
     * 获取某年某周的最后一天
     * @Methods Name getLastDayOfMonth
     * @Create 20140707 By weida
     * @param year 年
     * @param week 周
     * @return 最后一天
     */
    public static Date getLastDayOfWeeks(int year, int week) {
          Calendar cal = Calendar.getInstance();
          //设置年份
          cal.set(Calendar.YEAR, year);
          //设置周
          cal.set(Calendar.WEEK_OF_YEAR, week);
          //设置该周第一天为星期一
          cal.setFirstDayOfWeek(Calendar.MONDAY);
          //设置最后一天是星期日
          cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
          return cal.getTime();
      }

      /**
       * 获取某年某周的第一天
       * @Methods Name getFirstDayOfWeeks
       * @Create 20140707 By weida
       * @param year 年
       * @param week 周
       * @return 第一天
       */
      public static Date getFirstDayOfWeeks(int year, int week) {
          Calendar cal = Calendar.getInstance();
          //设置年份
          cal.set(Calendar.YEAR,year);
          //设置周
          cal.set(Calendar.WEEK_OF_YEAR, week);
          //设置该周第一天为星期一
          cal.setFirstDayOfWeek(Calendar.MONDAY);
          //设置最后一天是星期日
          cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek()); // Sunday
          return cal.getTime();
      }

      /**
       * 取得指定日期所在周的最后一天
       * @Methods Name getLastDayOfWeek
       * @Create 20140707 By weida
       * @param date
       * @return 最后一天
       */
      public static Date getLastDayOfWeek(Date date) {
          Calendar c = new GregorianCalendar();
          c.setFirstDayOfWeek(Calendar.MONDAY);
          c.setTime(date);
          c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
          return c.getTime();
      }

      /**
       * 取得指定日期所在周的最后一天
       * @Methods Name getLastDayOfWeek
       * @Create 20140707 By weida
       * @param date
       * @return 最后一天
       */
      public static String getLastDayOfWeek(String date) {
          Calendar c = new GregorianCalendar();
          c.setFirstDayOfWeek(Calendar.MONDAY);
          c.setTime(convertStringToDate(date));
          c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
          return convertDateToString(c.getTime());
      }

      /**
       * 取得指定日期所在周的第一天
       * @Methods Name getFirstDayOfWeek
       * @Create 20140707 By weida
       * @param date
       * @return 第一天
       */
      public static Date getFirstDayOfWeek(Date date) {
          Calendar c = new GregorianCalendar();
          c.setFirstDayOfWeek(Calendar.MONDAY);
          c.setTime(date);
          // Monday
          c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
          return c.getTime ();
      }

      /**
       * 取得指定日期所在周的第一天
       * @Methods Name getFirstDayOfWeek
       * @Create 20140715 By weida
       * @param date
       * @return 第一天
       */
      public static String getFirstDayOfWeek(String date) {
          Calendar c = new GregorianCalendar();
          c.setFirstDayOfWeek(Calendar.MONDAY);
          c.setTime(convertStringToDate(date));
          c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
          return convertDateToString(c.getTime());
      }

      /**
       * 判断今天是星期几（返回int类型 周日是 0 ，周一是 1 周六是 6）
       * @Methods Name getWeekDayString
       * @Create 20140707 By weida
       * @param date
       * @return int
       */
      public static int getWeekDayString(Date date){
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
          return dayOfWeek;
      }

      /**
       * 根据参数得到哪周的周日（参数0为上周，-1为两周前）
       * @Methods Name getWeekSunday
       * @Create 20140707 By weida
       * @param week
       * @return Date
       */
      public static Date getWeekSunday(int week){
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date());
          int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
          if (day_of_week == 0){
              day_of_week = 7;
          }
          calendar.add(Calendar.WEEK_OF_MONTH, week);
          calendar.add(Calendar.DATE, -day_of_week);
          return calendar.getTime();
      }

	    public static void print(Calendar calendar) {
		    System.out.println(getDate(calendar.getTime()));
	  }
	  
	  public static List<Date> getDates(String begin,String end) {
		  return getDates(convertStringToDate(begin),convertStringToDate(end));
	  }
	  
	  public static String formatByPattern(Date date,String pattern){
		  DateFormatUtils dateFormat = new DateFormatUtils();
		  return dateFormat.format(date, pattern);
	  }

    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(timePattern);
        try {
           return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
