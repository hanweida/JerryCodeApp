package com.jerry.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.util.ArrayList;
import java.util.List;

public class HttpTool {
	public static HttpClient httpClient;
	public static int LINE_NUM = 3;
	private static final int CONNECTIONS_MAX_TOTAL = 10000;
	private static final int TIME_OUT_CONN = 10000;
	private static final int TIME_OUT_SOCKET = 20000;
	public final static String ENCODING = "utf-8";
	public final static String WEB = "web";
	static {
		if (httpClient == null) {
			MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
			httpClient = new org.apache.commons.httpclient.HttpClient(defaultManager);
			HttpConnectionManagerParams params = defaultManager.getParams();
			params.setMaxTotalConnections(CONNECTIONS_MAX_TOTAL);
			params.setConnectionTimeout(TIME_OUT_CONN);
			params.setSoTimeout(TIME_OUT_SOCKET);
			httpClient.getParams().setConnectionManagerTimeout(TIME_OUT_CONN);
			httpClient.getParams().setSoTimeout(TIME_OUT_SOCKET);
			httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			// add cloud at 2012-10-17
			httpClient.getParams().setContentCharset(ENCODING);
			
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("User-Agent", "Nokia N73"));

			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		}
	}
}
