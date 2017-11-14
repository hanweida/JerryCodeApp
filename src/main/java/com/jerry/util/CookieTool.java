package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;


/**
 * Cookie操作工具类
 * @author zhenyu
 *
 */
public class CookieTool {

	 /**
	  * 设置cookie  
	  * @param response  
	  * @param name  cookie名字  
	  * @param value cookie值  
	  * @param maxAge cookie生命周期  以秒为单位  
	  */ 

	 public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
        //begin by weida 2014.3.19 for 设置一级域名
        DomainUtil.setCookieDomain(cookie, request);
        //end by weida 2014.3.19 for 设置一级域名
	    if(maxAge>0)  cookie.setMaxAge(maxAge);  
	    response.setHeader("P3P","CP=CAO PSA OUR");
	    response.addCookie(cookie);
	    HttpSession session = request.getSession();
	    if (value != null) {
	    	//同时放入session
		    session.setAttribute(name, value);
		} else {
			//删除
			session.removeAttribute(name);
		}
	     
	 }
	 
	 /**  
	   * 根据名字获取cookie  
	   * @param request  
	   * @param name cookie名字  
	   * @return  
	   */ 
	  public static Cookie getCookieByName(HttpServletRequest request, String name){
		 StringBuffer message = new StringBuffer();
		 message.append("[CookieTool]--- name:[").append(name).append("],");
		 Cookie cookie = null;
		 HttpSession session = request.getSession();
		 String value = "";
		 try {
			 value = (String)session.getAttribute(name);
		 } catch (Exception e) {
			// TODO: handle exception
		 }
		 //先从session取
		 if (StringUtils.isNotBlank(value)) {
			 message.append("value:[").append(value).append("]"); 
			 cookie = new Cookie(name,value);
			 DomainUtil.setCookieDomain(cookie, request);
			 cookie.setPath("/");
			 message.append(" --- from session."); 
		 } else {
	     //所有取都从session取
			 //非后台登陆key，session不存在则从cookie中取
			//if (!name.equals("adminCookie")) {
			//	Map<String,Cookie> cookieMap = ReadCookieMap(request);  
//			    if(cookieMap.containsKey(name)){  
//			  	  cookie = (Cookie)cookieMap.get(name);  
//			  	  message.append("value:").append(cookie.getValue()); 
//			  	  message.append(",from cookie"); 
//			  	  //System.out.println(name + ":" + cookie.getMaxAge());
//			    }
			//} else {
				message.append(" --- not in session."); 
			//}
		 }
		 //logger.info(message);
		 return cookie;  
	  }  

	     

	  /**  
	   * 将cookie封装到Map里面  
	   * @param request  
	   * @return  
	   */ 

	  private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
	      Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	      Cookie[] cookies = request.getCookies();
	      if(null!=cookies){  
	          for(Cookie cookie : cookies){
	              cookieMap.put(cookie.getName(), cookie);  
	          }  
	      }  
	      return cookieMap;  
	  } 
	  
	/**
	 *  取得cookie中的信息
	 * @param request
	 * @param cookieName：adminCookie，frontCookie
	 * @return
	 */
	public static Map<String, Object> getCookieInfo(HttpServletRequest request, String cookieName){
		Cookie cookie = CookieTool.getCookieByName(request, cookieName);//取cookie中保存的信息
		Map<String, Object> model = new HashMap<String, Object>();
		if(cookie!=null ){
			String cookieValue = cookie.getValue();
			if(cookieValue!=null && !"".endsWith(cookieValue)){ //cookie是否内容为空
				int num = cookieValue.indexOf(":");
				if(num<=0){//cookie信息不合法
					return null;
				}
				String id = cookieValue.substring(0,num);
				String userName=cookieValue.substring(num+1);
				model.put("id", id);
				try {
					model.put("userName", URLDecoder.decode(userName, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return model; 
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	/**
	 * 返回admin和user的id
	 * @Methods Name getUserId
	 * @Create In 2013-8-8 By likang
	 * @param request
	 * @return Map<String,Integer>
	 */
	public static Map<String,Integer> getUserId(HttpServletRequest request) {
		Map model = CookieTool.getCookieInfo(request,"frontCookie");
		int userid = 0;
		int adminid = 0;
		if ( model != null && model.get("id") != null) {
			userid = Integer.parseInt((String) model.get("id") );
		}
		Map map = CookieTool.getCookieInfo(request, "adminCookie");
		if ( map != null && map.get("id") != null) {
			adminid = Integer.parseInt((String) map.get("id") );//审核管理员用户id
		}
		Map mapBack = new HashMap<String,Integer>();
		mapBack.put("adminid", adminid);
		mapBack.put("userid", userid);
		return mapBack;
	}
	
	
	/**
	 *  取得cookie中的原始信息
	 * @param request
	 * @param cookieName：adminCookie，frontCookie
	 * @return String
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		Cookie cookie = CookieTool.getCookieByName(request, cookieName);//取cookie中保存的信息
		if(cookie!=null ){
			String cookieValue = cookie.getValue();
			if(cookieValue!=null && !"".endsWith(cookieValue)){//cookie是否内容为空
				return cookieValue; 
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 是否包含有效cookie
	 * @param request
	 * @param cookieName：adminCookie，frontCookie
	 * @return
	 */
	public static boolean hasCookieInfo(HttpServletRequest request, String cookieName){
		Cookie cookie = CookieTool.getCookieByName(request, cookieName);//取cookie中保存的信息
		if(cookie!=null && cookie.getValue()!=null && !"".endsWith(cookie.getValue())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 清除cookie
	 * @param response
	 * @param cookie
	 */
	 public static void removeCookie(HttpServletResponse response, Cookie cookie){
		 cookie.setMaxAge(0);
         response.addCookie(cookie);
	 } 
	 
	 public static void removeCookie(String key, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	 }
	 
	 public static void removeSession(String key, HttpServletRequest request) {
		 HttpSession session = request.getSession();
		 session.removeAttribute(key);
	}

}
