package com.jerry.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字工具类
 * @Class Name NumberFormatUtil
 * @Author likang
 * @Create In Jun 13, 2012
 */
public class NumberFormatUtil {
	
	/**
	 * 将数字转换成百分号格式
	 * @Methods Name parseToPercent
	 * @Create In Jun 13, 2012 By likang
	 * @param number
	 * @return String
	 */
	public static String parseToPercent(Float number) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat(".00%");
		return df.format(number);
	}
	
	/**
	 * 将数字转换成百分号格式
	 * @Methods Name parseToPercent
	 * @Create In Jun 13, 2012 By likang
	 * @param number
	 * @return String
	 */
	public static String parseToPercent(Double number) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat(".00%");
		return df.format(number);
	}
	
	
	public static String parseToInt(Double number) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("#");
		return df.format(number);
	}
	
	public static String parseTo2DecimalsStr(Double number) {
		// TODO Auto-generated method stub
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(number);
	}
	
	/**
	 * 保留n位小数
	 * @Methods Name parseToNDecimals
	 * @Create In 2013-1-7 By likang
	 * @param number
	 * @param decimalsNum
	 * @return double
	 */
	public static double parseToNDecimals(Double number, int decimalsNum) {
		try {
			BigDecimal bigDecimal = new BigDecimal(number);
			return bigDecimal.setScale(decimalsNum, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0d;
		
	}
	
	

}
