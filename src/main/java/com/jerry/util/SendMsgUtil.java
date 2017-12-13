package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

public class SendMsgUtil {
	

	
	/**
	 * 短信接口通过短信通道-发送手机短信
	 * @Methods Name sendSmsMsg
	 * @Create In 2014-7-18 By likang
	 * @param msg
	 * @param mobile
	 * @return boolean
	 * @throws UnsupportedEncodingException
	 */
	public static boolean sendSmsMsg(String msg, String mobile) throws UnsupportedEncodingException {
		boolean success = false;
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		String url = "http://10.26.27.100:8667/api/send";
		dataMap.put("username", "ecom");
		dataMap.put("password", "ecomsms");
		dataMap.put("mobile", mobile);
		dataMap.put("content", msg);
		String result = httpClientUtil.sendDataGet(url, dataMap);
		if (StringUtils.isNotBlank(result) && result.contains("submitStatus")) {
			success = true;
		}
		return success;
	}
	
	/**
	 * 短信接口通过手机号-发送手机短信
	 * @Methods Name sendSmsMsgByPhoneNum
	 * @Create In 2014-10-31 By likang
	 * @param msg
	 * @param mobile
	 * @return
	 * @throws UnsupportedEncodingException boolean
	 */
	public static boolean sendSmsMsgByPhoneNum(String msg, String mobile) throws UnsupportedEncodingException {
		boolean success = false;
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		String url = "http://10.26.27.100:8666/";
		dataMap.put("PhoneNumber", mobile);
		dataMap.put("Text", msg);
		String result = httpClientUtil.sendDataGet(url, dataMap);
		if (StringUtils.isNotBlank(result) && result.contains("Submitted")) {
			success = true;
		}
		return success;
	}
	
}
