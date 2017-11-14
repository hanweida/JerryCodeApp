package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Xie
 * 读取properties文件
 */
public class JProperties {
	// 从properties文件中，根据key读取value
	public static String readValue(String filePath, String key) {
		Properties props = new Properties();
		try {
			InputStream in = JProperties.class.getClassLoader().getResourceAsStream(filePath);//new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    //从properties文件中，根据key读取value 设置默认值
    public static String readValueByDefault(String filePath, String key, String defaultValue){
        String value = readValue(filePath,key);
        if(StringUtils.isEmpty(value)){
            value = defaultValue;
        }
        return value;
    }

	// 读取properties的全部信息
	public static void readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = JProperties.class.getClassLoader().getResourceAsStream(filePath);//new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 读取properties的server_sites_count信息
	public static Integer getServerSiteCount(Integer siteId) {
		String siteCountStr = readValue("config.properties", "server.site.count");
		int siteCount = 0;
		if(siteCountStr!=null&& StringUtils.isNumeric(siteCountStr)){
			siteCount = Integer.valueOf(siteCountStr);
			if(siteId>0 && siteId%siteCount==0){
				siteCount = siteId%siteCount;
			}else if(siteId>0 && siteId%siteCount!=0){
				siteCount = siteId/siteCount+1;
			}
		}
		return siteCount;
	}

}
