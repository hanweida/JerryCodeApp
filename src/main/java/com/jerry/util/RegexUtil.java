package com.jerry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	/**
	 * 根据url获取主域名
	 * @Methods Name getHost
	 * @Create In Jul 13, 2012 By zhenyu
	 * @param url
	 * @return String
	 */
	public static String getHost(String url){
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(me|com.cn|com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		if(matcher.find()){
			return matcher.group();
		}else{
			return url;
		}
		
	}

    public static String[] delSecpFromStringList(String[] old){
        List<String> tmp = new ArrayList<String>();
        for(String str:old){
            if(str!=null && str.length()!=0){
                tmp.add(str);
            }
        }
        old = tmp.toArray(new String[0]);
        return old;
    }
}
