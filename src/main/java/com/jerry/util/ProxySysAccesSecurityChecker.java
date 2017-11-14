package com.jerry.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProxySysAccesSecurityChecker {
	
	private static Map<String,Boolean> idMap = new HashMap<String, Boolean>();
	private static Map<String,Boolean> blackMap = new HashMap<String, Boolean>();

	private static final Logger log = LogWriter.getIntf();

	static{
		idMap.put("59.151.6.184", true);
		idMap.put("59.151.6.180", true);
        //crm测试机内外网Ip地址 begin
        idMap.put("10.26.28.183", true);
        idMap.put("223.99.226.155", true);
        //crm测试机内外网Ip地址 end
        //59.151.6.184
		//59.151.6.180
		blackMap.put("124.202.129.123", true);
	}
	
	public static boolean  check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String nowIp = HttpUtil.getIpAddr(request);
		boolean pass = false;
		//黑名单
		if (StringUtils.isNotBlank(nowIp) && blackMap.get(nowIp)!= null) {
			log.error("black forbidden ip:" + nowIp + ", url:" + request.getRequestURI());
			return false; 
		}
		String isOpenChecker = PropertiesUtil.getProperties("proxySysAccesSecurityChecker");
		//开放ip过滤
		if (StringUtils.isNotBlank(isOpenChecker) && isOpenChecker.equals("true")) {
			if (StringUtils.isNotBlank(nowIp) && idMap.get(nowIp) != null) {
				log.info("pass ip:" + nowIp);
				pass = true;
			}
			if(!pass) {
				log.error("forbidden ip:" + nowIp + ", url:" + request.getRequestURI());
				response.sendRedirect("/");
			}
		} else {
		//停止ip过滤
			log.info("ip:" + nowIp + ", url:" + request.getRequestURI());
			return true;
		}
		return pass;
	}
}
