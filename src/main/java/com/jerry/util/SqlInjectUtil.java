package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * sql注入
 * @Class Name SqlInjectUtil
 * @Author likang
 * @Create In 2013-12-18
 */
public class SqlInjectUtil {
	
	//private static final String regEx_sql = "select[\\s]+|update[\\s]+|delete[\\s]+|insert[\\s]+|[\\s]+or[\\s]+|[\\s]+and[\\s]+|exec|drop|net[\\s]*user|declare|cast[\\s]*\\(|truncate|'|\\(|\\)|char\\(|delete%20from%20|select%20|update%20|truncate%20|drop%20table%20|%20and%20|%20or%20|\"|net%20user|<";
	
	private static final String regEx_sql = "select[\\s]+|update[\\s]+|delete[\\s]+|insert[\\s]+|[\\s]+or[\\s]+|[\\s]+and[\\s]+|exec|drop|net[\\s]*user|declare|cast[\\s]*\\(|truncate|'|char\\(|delete%20from%20|select%20|update%20|truncate%20|drop%20table%20|%20and%20|%20or%20|net%20user|<|..\\\\|]]>>|\\$\\{|\\\\";

	/**
	 * 判断参数是否符合sql注入条件
	 * @Methods Name isInject
	 * @Create In 2013-12-18 By likang
	 * @param param
	 * @return boolean
	 */
	public static boolean isInject(String param) {
		if (StringUtils.isNotBlank(param)) {
			Pattern validatePattern = Pattern.compile(regEx_sql, Pattern.CASE_INSENSITIVE);
			Matcher validateMatch = validatePattern.matcher(param);
			return validateMatch.find();
		}
		return false;
	}

    /**
     * 忽略搜索投展示绑定的SQL注入
     * @Methods Name isInjectIgnore
     * @Create In 2014-10-23 By weida
     * @param param
     * @return boolean
     */
    public static boolean isInjectIgnore(String param) {
        return false;
    }

    /**
     * 判断map所有参数
     * @param params
     * @return
     */
    public static boolean isInject(Map<String, String> params) {
        Set<String> keys = params.keySet();
        for(String key : keys){
            String value = params.get(key);
            if(isInject(value)){
                //有一个参数为sql注入
                return true;
            }
        }
        return false;
    }
}
