package com.jerry.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * http工具类
 * @Class Name HttpUtil
 * @Author likang
 * @Create In 2013-1-23
 */
public class HttpUtil {
	/**
	 * 日志记录所有参数
	 * @Methods Name getRequestParam
	 * @Create In 2013-1-23 By likang
	 * @param request void
	 */
	public static String getRequestParam(HttpServletRequest request) {
		Enumeration enume = request.getParameterNames();
		StringBuilder sBuilder = new StringBuilder("param: ");
		while(enume.hasMoreElements()){
			Object item = enume.nextElement();
			String paramName = item.toString();
			String value = request.getParameter(paramName);
			sBuilder.append(paramName + "=" + value + ",");
		}
		return sBuilder.toString();
	}
	
	/**
	 * 访问ip来源
	 * @Methods Name getClientIp
	 * @Create In 2013-1-28 By likang
	 * @param request
	 * @return String
	 */
    public static String getClientIp(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	String clientIP = request.getHeader("X-Forwarded-For") ;
	 	if (clientIP == null) {
	 		clientIP = request.getRemoteAddr() ;
	 	}
	 	return clientIP;
    }
    
    
   public static String getIpAddr(HttpServletRequest request) {
	 String ip = request.getHeader("x-forwarded-for");
	 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		 ip = request.getHeader("Proxy-Client-IP");
	 }
	 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		 ip = request.getHeader("WL-Proxy-Client-IP");
	 }
	 if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		 ip = request.getRemoteAddr();
	 }
	 	return ip;
	 }
   
   /**
	 * 是否是Ajax请求
	 * @Methods Name isAjaxRequest
	 * @Create In Aug 31, 2010 By likang
	 * @param request
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		//在服务器端判断request来自异步还是同步请求
		return (request.getHeader("x-requested-with") != null)? true:false; 
	}
}
