package com.jerry.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * httpclient工具类
 * @Class Name HttpClientUtil
 * @Author likang
 * @Create In 2014-6-19
 */
public class HttpClientUtil {
	
	public HttpClientUtil(Logger logger, String encode) {
		this.setLogger(logger);
		this.setEncode(encode);
	}
	
	public HttpClientUtil(Logger logger) {
		this.setLogger(logger);
	}
	
	public HttpClientUtil() {
		
	}
	
	//logger不为null时，记录日志
	private Logger logger;
	
	//默认转换编码
	private String encode = "utf-8";
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	private void writeLog(Object message, Throwable t) {
		if (this.getLogger() != null) {
			if (t != null) {
				this.getLogger().error(message.toString(), t);
			} else {
				this.getLogger().info(message.toString());
			}
		}
	}

	/**
	 * 将post参数放入map中
	 * @Methods Name initPostData
	 * @Create In 2014-6-17 By 005
	 * @param dataMap
	 * @return NameValuePair[]
	 */
	private NameValuePair[] initPostData(Map<String,String> dataMap) {
		Iterator<String> iterator = dataMap.keySet().iterator();
		int size = dataMap.keySet().size();
		if (size > 0) {
			NameValuePair[] postData = new NameValuePair[size];
			int i = 0;
			while (iterator.hasNext()) {
				String name = iterator.next();
				postData [i] = new NameValuePair(name,dataMap.get(name));
				i++;
			}
			return postData;
		}
		return null;
	}
	
	private String initGetData(String url, Map<String,String> dataMap) {
		Iterator<String> iterator = dataMap.keySet().iterator();
		url += "?";
		int size = dataMap.keySet().size();
		if (size > 0) {
			while (iterator.hasNext()) {
				String name = iterator.next();
				try {
					url += name + "=" + URLEncoder.encode(dataMap.get(name),"utf-8") + "&";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					url += name + "=" + dataMap.get(name) + "&";
					e.printStackTrace();
				}
			}
			url = url.substring(0,url.length() - 1);
		}
		return url;
	}
	
	/**
	 * 发送post请求
	 * @Methods Name sendData
	 * @Create In 2014-6-19 By likang
	 * @param url
	 * @param dataMap
	 * @return String
	 */
	public String sendData(String url, Map<String,String> dataMap){
		HttpClient httpClient = new HttpClient(); 
		HttpConnectionManagerParams configParams = httpClient.getHttpConnectionManager().getParams();  
        configParams.setConnectionTimeout(60*1000);  
        configParams.setSoTimeout(60*1000);  
        httpClient.getParams().setConnectionManagerTimeout(60*1000l);  
		String text = "";
		//模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式         
		PostMethod postMethod = new PostMethod(url);           
		//设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了       
		NameValuePair[] data = initPostData(dataMap);
		postMethod.setRequestBody(data);   
		postMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		try {
			//设置 HttpClient 接收 Cookie,用与浏览器一样的策略            
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);             
			httpClient.executeMethod(postMethod);   
			int code = httpClient.executeMethod(postMethod);  
			writeLog("code:" + code + ",url:" + url + ",data:" + dataMap,null);
			text = postMethod.getResponseBodyAsString();  
			text = URLDecoder.decode(text, this.getEncode());
		} catch (Exception e) {
			writeLog("sendData error",e);
			e.printStackTrace();         
		}     
		return text;
	}

	public String sendDataGet(String url, Map<String,String> dataMap){
		 GetMethod getMethod = null;
		 String text = "";
		 try{	   
			url = initGetData(url, dataMap);
			getMethod = new GetMethod(url);
			HttpClient httpClient; 
			MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
			httpClient = new org.apache.commons.httpclient.HttpClient(defaultManager);
			HttpConnectionManagerParams params = defaultManager.getParams();
			params.setMaxTotalConnections(10000);
			params.setConnectionTimeout(0);
			params.setSoTimeout(0);
			httpClient.getParams().setConnectionManagerTimeout(0);
			httpClient.getParams().setSoTimeout(0);
			httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			httpClient.getParams().setContentCharset("utf-8");
		   httpClient.getHostConfiguration().setProxy("localhost", 8888);
			 List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			int code = httpClient.executeMethod(getMethod);
			writeLog("code:" + code + ",url:" + url + ",data:" + dataMap,null);
			text = getMethod.getResponseBodyAsString();
			writeLog("text:" + text,null);
		  }
		  catch(Exception e){
			writeLog("sendData error",e);
		  }finally{
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		  }
		return text;
	}

    public String sendDataGet(String url, Map<String,String> dataMap, int timeout){
        GetMethod getMethod = null;
        String text = "";
        try{
            url = initGetData(url, dataMap);
            getMethod = new GetMethod(url);
            HttpClient httpClient;
            MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
            httpClient = new HttpClient(defaultManager);
            HttpConnectionManagerParams params = defaultManager.getParams();
            params.setMaxTotalConnections(timeout * 1000);
            params.setConnectionTimeout(timeout * 1000);
            params.setSoTimeout(timeout * 1000);
            httpClient.getParams().setConnectionManagerTimeout(timeout * 1000);
            httpClient.getParams().setSoTimeout(timeout * 1000);
            httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            httpClient.getParams().setContentCharset("utf-8");
            List<Header> headers = new ArrayList<Header>();
            httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            int code = httpClient.executeMethod(getMethod);
            writeLog("code:" + code + ",url:" + url + ",data:" + dataMap,null);
            //text = getMethod.getResponseBodyAsString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            text = stringBuffer.toString();
            writeLog("back result:[" + text + "]",null);
        } catch(Exception e){
            writeLog("sendData error",e);
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return text;
    }



	public String sendDataGetHttps(String url, Map<String,String> dataMap){
		GetMethod getMethod = null;
		String text = "";
		try{
			url = initGetData(url, dataMap);
			getMethod = new GetMethod(url);
			HttpClient httpClient;
			MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
			httpClient = new org.apache.commons.httpclient.HttpClient(defaultManager);
			HttpConnectionManagerParams params = defaultManager.getParams();
			params.setMaxTotalConnections(10000);
			params.setConnectionTimeout(0);
			params.setSoTimeout(0);
			httpClient.getParams().setConnectionManagerTimeout(0);
			httpClient.getParams().setSoTimeout(0);
			httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			httpClient.getParams().setContentCharset("utf-8");
			httpClient.getHostConfiguration().setProxy("localhost", 8888);
			List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			int code = httpClient.executeMethod(getMethod);
			writeLog("code:" + code + ",url:" + url + ",data:" + dataMap,null);
			text = getMethod.getResponseBodyAsString();
			writeLog("text:" + text,null);
		}
		catch(Exception e){
			writeLog("sendData error",e);
		}finally{
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		}
		return text;
	}
}
