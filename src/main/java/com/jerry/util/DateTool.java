package com.jerry.util;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTool {

	/**
	 * 将指定日期转换为指定格式的字符串
	 * @param date ( java.util.Date )
	 * @return String
	 */
	public static String getDateStringByPattern(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sf.format(date);
		return str;
	}
	
	public static String getDateStringByPatternOnlyDate(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sf.format(date);
		return str;
	}
	
	public static String getDateStringByPattern() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sf.format(new Date());
		return str;
	}
	
	public static String getToday() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String str = sf.format(new Date());
		return str;
	}
	
	/**
	 * 日期转换
	 * @param
	 * @return String
	 */
	public static String getDateLongByPattern(long _date) {
		Date date = new Date(_date);//默认是当前时间
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sf.format(date);
		return str;
	}
	
	/**   
	 * 获取指定日期的毫秒     
	 * @param p_date util.Date日期   
	 * @return long 毫秒 
	*/ 
	public static long getMillisOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime( p_date );
		return c.getTimeInMillis();
	}

	public static long getMillisOfDateForString(String date) {
	    java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime( getTime(date) );
		return c.getTimeInMillis();
	}

    public static Date getTime(String date){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = simpledateformat.parse(date,new ParsePosition(0));
        return d;
    }

	public static String getFormatData(double data) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(data);
	}

	public static int dateFormatAndCheck(String dateStr, int dateBefore) {
		 java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		 if(dateStr==null || dateStr.length()==0){
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 0-dateBefore);
			dateStr = sdf.format(cal.getTime());
		 }else{
		 	dateStr = dateStr.substring(0,4)+dateStr.substring(5,7)+dateStr.substring(8,10);			
		 }
		 
		 int dateInt = 0;
		 try{
			dateInt = Integer.parseInt(dateStr);
		 }catch(Exception e){
			 dateInt = 20020101;
		 }
		 return dateInt;
	 }
	 
	 public static String dateFormatToShow(String dateStr) {
		 dateStr = dateStr.substring(0,4)+ "-" + dateStr.substring(4,6)+ "-" + dateStr.substring(6,8);		 
		 return dateStr;
	 }
	 
    public static String getTime(){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return simpledateformat.format(new Date());
    }
    
    public static String getYMD(){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
        return simpledateformat.format(new Date());
    }
    
    public static String getHHMMSS(){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("HHmmss");
        return simpledateformat.format(new Date());
    }
    
    public static long getLongTime(){
    	return new Date().getTime();
    }
    
    public static int getYear() {
    	int year;
    	GregorianCalendar g=new GregorianCalendar();
        year=(int)g.get(Calendar.YEAR);
        return year;
    }
    
    public static int getMonth() {
    	int month;
    	GregorianCalendar g=new GregorianCalendar();
    	month=(int)g.get(Calendar.MONTH)+1;
        return month;
    }
    
    public static int getDay() {
    	int month;
    	GregorianCalendar g=new GregorianCalendar();
    	month=(int)g.get(Calendar.DATE);
        return month;
    }
    
    /**
     * 参数日期加1
     * @Methods Name addOneday
     * @Create In 2012-2-24 By zhenyu
     * @param today
     * @return String
     */
	public static String addOneDay(String today){
        SimpleDateFormat f =  new SimpleDateFormat("yyyy-MM-dd");
        try   {  
            Date d  =  new Date(f.parse(today).getTime()+24*3600*1000);
              return  f.format(d);  
        }  
        catch(Exception ex) {
            return   "输入格式错误";    
        }  
    }
	
	/**
	 * 参数日期加1
	 * @Methods Name addOneDay
	 * @Create In 2012-2-24 By zhenyu
	 * @param today
	 * @return Date
	 */
	public static Date addOneDay(Date today){
		try   {  
			Date d  =  new Date(today.getTime()+24*3600*1000);
			return  d;  
		}  
		catch(Exception ex) {
		}  
		return null;
	}
    public static void main(String[] args){
    	System.out.println(getDateLongByPattern(1515121144414l));
		//2017-12-01 11:14:40
		//2017-12-01 11:14:51
		//2017-12-01 11:15:02
    }
}
