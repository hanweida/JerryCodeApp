package com.jerry.util;

import com.jerry.web.ContextHolder;
import org.springframework.context.ApplicationContext;

import java.text.MessageFormat;


/**
 * 读取属性配置文件.propertries 的工具类
 * @author zhenyu
 *
 */
public class PropertiesUtil {
	
	public static String getProperties(String Key, String defaultValue) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: defaultValue);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	

	public static String getProperties(String Key) {
		ApplicationContext appContext = ContextHolder.getApplicationContext();
		String message = "";
		try {
			message = appContext.getMessage(Key, new Object[0], ContextHolder.getInstance().getLocal());

			return (message != null && !message.equals("") ? message
					: null);
		} catch (Exception e) {
			return null;
		}
	}
	

	public static String format(String source, Object[] arg){
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(ContextHolder.getInstance().getLocal());
		String value = "";
		try{
			value = formatter.format(source,arg);
		}catch(Exception e){
			value = source;
		}
		
		return value;
	}

}
