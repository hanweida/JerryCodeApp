package com.jerry.framework.util;

import java.util.Iterator;
import java.util.Map;

public class MapUtil {
	
	/**
	 * map toString
	 * @Methods Name mapToStr
	 * @Create In 2015-2-6 By likang
	 * @param section
	 * @return String
	 */
    public static String mapToStr(Map section) {
		// TODO Auto-generated method stub
    	StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("{");
    	if (section != null) {
        	Iterator<String> iterator = section.keySet().iterator();
        	int i = 0;
        	while (iterator.hasNext()) {
        		String key = iterator.next();
        		if (i>0) {
        			sBuffer.append(",");
    			}
        		sBuffer.append(key).append("=").append(section.get(key));
        		i++;
    		}
		}
		sBuffer.append("}");
		return sBuffer.toString();
	}
}
