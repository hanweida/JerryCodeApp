package com.jerry.framework.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Class: RequestUtil
 * User: likang
 * Date: 16-1-26
 * Time: 上午11:18
 * To change this template use File | Settings | File Templates.
 */
public class RequestUtil {

    /**
     * 将Map<java.lang.String,java.lang.String[]> 转换为 Map<String,String>
     * @param request
     * @return
     */
    public static Map<String,String> requestMap(HttpServletRequest request){
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = request.getParameterMap().entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return  returnMap;
    }
}
