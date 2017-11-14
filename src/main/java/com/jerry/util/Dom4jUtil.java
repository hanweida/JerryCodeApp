package com.jerry.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 用dom4j操作xml 需要时再增加其他方法
 * @Class Name Dom4jUtil
 * @Author likang
 * @Create In 2014-6-18
 */
public class Dom4jUtil {
	
	/**
	 * 从根目录起 取node 是 key的text
	 * 例如<a><b><c>hi</c></b></a>
	 * key: a.b.c 返回值 hi
	 * @Methods Name getTextValueByElementPath
	 * @Create In 2014-6-18 By likang
	 * @param text
	 * @param key
	 * @return String
	 */
	public static String getTextValueByElementPath(String text, String key) {
		String value = "";
		try {
			if (text != null && !text.trim().equals("")) {
				Document document = DocumentHelper.parseText(text);
				String[] keyPath = key.split("\\.");
				Element root = document.getRootElement();  
				Element now = root;
				for(int i = 1; i < keyPath.length; i++) {
				   	now = now.element(keyPath[i]);
				}
				if (now != null) {
					value = now.getText();
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
		
	}
	
}
