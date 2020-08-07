package com.mycode.purchasing.utils.http;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Class: HttpUtil
 * Date: 17-2-27
 * User: likang
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtil {

    private HttpClient httpClient;

    public HttpUtil() {
        if (httpClient == null) {
            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        }
    }

    public HttpUtil(String proxyHostname, int proxyPort) {
        if (httpClient == null) {
            httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
            httpClient.getHostConfiguration().setProxy(proxyHostname,proxyPort);
        }
    }

    public HttpExeResult exeGetMethod0(String url, Map<String, String> headerMap) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        GetMethod getMethod = new GetMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
//        params.setMaxTotalConnections(10000);
//        params.setConnectionTimeout(999999999);
//        params.setSoTimeout(999999999);
//        httpClient.getParams().setConnectionManagerTimeout(999999999);
//        httpClient.getParams().setSoTimeout(999999999);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(getMethod);
        Map<String, String> responseHeader = getResponseHeader(getMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(getMethod);
        } else {
            text = getResponseBodyAsString(getMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(getMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return her;
    }

    public HttpExeResult exeGetMethod(String url, Map<String, String> headerMap) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        GetMethod getMethod = new GetMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(getMethod);
        Map<String, String> responseHeader = getResponseHeader(getMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(getMethod);
        } else {
            text = getResponseBodyAsString(getMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(getMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return her;
    }

    private String getResponseBodyAsString(HttpMethodBase methodBase) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(methodBase.getResponseBodyAsStream(),"utf-8"));
        //使用response返回的charset作为编码
        BufferedReader reader = new BufferedReader(new InputStreamReader(methodBase.getResponseBodyAsStream(), methodBase.getResponseCharSet()));
        StringBuffer stringBuffer = new StringBuffer();
        String str = "";
        while ((str = reader.readLine()) != null) {
            stringBuffer.append(str);
        }
        return stringBuffer.toString();
    }


    public HttpExeResult exePostMethod(String url, Map<String, String> headerMap, Map<String, String> dataMap) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        NameValuePair[] data = initPostData(dataMap);
        /**
         *  public void setRequestBody(org.apache.commons.httpclient.NameValuePair[] parametersBody)
         *  方法有bug 当 value是 = 等号值时 传递到服务器端取值为null
         *  */
        postMethod.setRequestBody(data);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return her;
    }

    private Map<String, String> getResponseHeader(HttpMethodBase methodBase) {
        Map<String, String> map = new HashMap();
        if (methodBase != null) {
            Header[] responseHeaders = methodBase.getResponseHeaders();
            for (Header header : responseHeaders) {
                if (map.containsKey(header.getName().toLowerCase())) {
                    map.put(header.getName().toLowerCase(), map.get(header.getName().toLowerCase()) + ";" + header.getValue());
                } else {
                    map.put(header.getName().toLowerCase(), header.getValue());
                }
            }
        }
        return map;  //To change body of created methods use File | Settings | File Templates.
    }


    public HttpExeResult exePostMethod302(String url, Map<String, String> headerMap, Map<String, String> dataMap) throws Exception {
        String text = "";
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        NameValuePair[] data = initPostData(dataMap);
        /**
         *  public void setRequestBody(org.apache.commons.httpclient.NameValuePair[] parametersBody)
         *  方法有bug 当 value是 = 等号值时 传递到服务器端取值为null
         *  */
        postMethod.setRequestBody(data);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
//        //处理http返回码302的情况
//        if (postMethod.getStatusLine().getStatusCode() == 302) {
//            String locationUrl=response.getLastHeader("location").getValue();
//            get(locationUrl);//跳转到重定向的url
//        }
        try {
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return (new HttpExeResult()).setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
    }

    public HttpExeResult exePostMethodHttps(String url, Map<String, String> headerMap, Map<String, String> dataMap) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        NameValuePair[] data = initPostData(dataMap);
        /**
         *  public void setRequestBody(org.apache.commons.httpclient.NameValuePair[] parametersBody)
         *  方法有bug 当 value是 = 等号值时 传递到服务器端取值为null
         *  */
        postMethod.setRequestBody(data);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            Protocol.unregisterProtocol("https");
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return her;
    }

    public HttpExeResult exeGetMethodHttps(String url, Map<String, String> headerMap) throws Exception {
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        HttpExeResult her = new HttpExeResult();
        String text = "";
        GetMethod getMethod = new GetMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(getMethod);
        Map<String, String> responseHeader = getResponseHeader(getMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(getMethod);
        } else {
            text = getResponseBodyAsString(getMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(getMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            Protocol.unregisterProtocol("https");
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return her;
    }

    public HttpExeResult exeGetMethodHttpsResultAsPic(String url, Map<String, String> headerMap) throws Exception {
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        HttpExeResult her = new HttpExeResult();
        String text = "";
        GetMethod getMethod = new GetMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(getMethod);
        Map<String, String> responseHeader = getResponseHeader(getMethod);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            if (responseHeader.get("content-type").contains("image") ||
                    responseHeader.get("content-type").contains("jpeg")) {
                text = "";
                File pic = File.createTempFile(System.nanoTime()/1000000000l+"",".jpeg");
                fos = new FileOutputStream(pic);
                bos = new BufferedOutputStream(fos);
                bos.write(getMethod.getResponseBody());
                her.setResponseFile(pic);
            }
            Map<String, String> cookieMap = getCookieFromResponseHeader(getMethod);
            her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
            if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
                her.setRedirectUrl(her.getResponseHeader().get("location"));
            }
        } finally {
            Protocol.unregisterProtocol("https");
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return her;
    }


    public HttpExeResult exePostMethodHttps(String url, Map<String, String> headerMap, String data) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", https);
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(1000000000);
        params.setConnectionTimeout(1000000000);
        params.setSoTimeout(1000000000);
        httpClient.getParams().setConnectionManagerTimeout(1000000000);
        httpClient.getParams().setSoTimeout(1000000000);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        /**
         *  public void setRequestBody(org.apache.commons.httpclient.NameValuePair[] parametersBody)
         *  方法有bug 当 value是 = 等号值时 传递到服务器端取值为null
         */
        postMethod.setRequestBody(data);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            Protocol.unregisterProtocol("https");
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return her;
    }

    /**
     * 返回是否gizp压缩
     *
     * @param responseHeader
     * @return
     */
    private boolean resultIsGizped(Map<String, String> responseHeader) {
        boolean isGiped = false;
        if (responseHeader != null && responseHeader.containsKey("content-encoding")) {
            isGiped = responseHeader.get("content-encoding").toLowerCase().equals("gzip");
        }
        return isGiped;  //To change body of created methods use File | Settings | File Templates.
    }


    public HttpExeResult exePostMethod(String url, Map<String, String> headerMap, String data) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        postMethod.setRequestBody(data);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(getResponseHeader(postMethod)).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return her;
    }


    public HttpExeResult exePostMethodMulti(String url, Map<String, String> headerMap, List<Part> partsList) throws Exception {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        PostMethod postMethod = new PostMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        MultipartRequestEntity mre = new MultipartRequestEntity(partsList.toArray(new Part[partsList.size()]), postMethod.getParams());
        postMethod.setRequestEntity(mre);
        int code = httpClient.executeMethod(postMethod);
        Map<String, String> responseHeader = getResponseHeader(postMethod);
        if (resultIsGizped(responseHeader)) {
            text = ResponseUtil.getResponseBodyGizpAsString(postMethod);
        } else {
            text = getResponseBodyAsString(postMethod);
        }
        Map<String, String> cookieMap = getCookieFromResponseHeader(postMethod);
        her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(getResponseHeader(postMethod)).setResponseHeader(responseHeader);
        if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
            her.setRedirectUrl(her.getResponseHeader().get("location"));
        }
        try {
        } finally {
            if (postMethod != null) {
                postMethod.releaseConnection();
            }
        }
        return her;
    }

    /**
     * 构造header
     *
     * @param headerMap
     * @return
     */
    private List<Header> initHeaders(Map<String, String> headerMap) {
        List<Header> headers = new ArrayList<Header>();
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                Header a = new Header();
                a.setName(entry.getKey());
                a.setValue(entry.getValue());
                headers.add(a);
            }
        }
        return headers;
    }

    /**
     * 将post参数放入map中
     *
     * @param dataMap
     * @return NameValuePair[]
     * @Methods Name initPostData
     * @Create In 2014-6-17 By likang
     */
    public static NameValuePair[] initPostData(Map<String, String> dataMap) {
        Iterator<String> iterator = dataMap.keySet().iterator();
        int size = dataMap.keySet().size();
        if (size > 0) {
            NameValuePair[] postData = new NameValuePair[size];
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                postData[i] = new NameValuePair(name, dataMap.get(name));
                i++;
            }
            return postData;
        }
        return new NameValuePair[0];
    }

    public static String getUrlContent(Map<String, String> dataMap) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            if (i > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            i++;
        }
        return sb.toString();  //To change body of created methods use File | Settings | File Templates.
    }

    /**
     * 从cookie获取信息
     *
     * @param methodBase
     */
    public static Map<String, String> getCookieFromResponseHeader(HttpMethodBase methodBase) {
        Map<String, String> cookiesMap = new HashMap();
        StringBuilder cookie = new StringBuilder();
        if (methodBase != null) {
            Header[] responseHeaders = methodBase.getResponseHeaders();
            for (Header header : responseHeaders) {
                if (header.getName().toLowerCase().equals("set-cookie")) {
                    String[] cookieParam = getCookieValue(header.getValue());
                    cookiesMap.put(cookieParam[0], cookieParam[1]);
                }
            }
        }
        return cookiesMap;
    }

    /**
     * 从set-cookie中 只获取 value 的 key 和 name
     *
     * @param value
     * @return
     */
    private static String[] getCookieValue(String value) {
        String[] cookie = new String[2];
        //cookie格式
        //value [ ;expires=date][ ;domain=domain][ ;path=path][ ;secure]
        //例如 a07=V1NEMTYwOTI4MDE5NTc4OQ==; domain=wsloan.com; expires=Wed, 01-Mar-2017 11:34:16 GMT; path=/
        String cookieValue = "";
        if (StringUtils.isNotBlank(value)) {
            cookieValue = value.substring(0, value.indexOf(";"));
            cookie[0] = cookieValue.substring(0, cookieValue.indexOf("="));
            cookie[1] = cookieValue.substring(cookieValue.indexOf("=") + 1, cookieValue.length());
        }
        return cookie;  //To change body of created methods use File | Settings | File Templates.
    }


    public HttpExeResult exeGetMethodHttpResultAsFileBytesByContentType(
            String url, Map<String, String> headerMap, String contentType) throws IOException {
        HttpExeResult her = new HttpExeResult();
        String text = "";
        GetMethod getMethod = new GetMethod(url);
        MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
        if (httpClient == null) {
            httpClient = new HttpClient(defaultManager);
        }
        HttpConnectionManagerParams params = defaultManager.getParams();
        params.setMaxTotalConnections(10000);
        params.setConnectionTimeout(0);
        params.setSoTimeout(0);
        httpClient.getParams().setConnectionManagerTimeout(0);
        httpClient.getParams().setSoTimeout(0);
        httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        httpClient.getParams().setContentCharset("utf-8");
        List<Header> headers = initHeaders(headerMap);
        httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
        int code = httpClient.executeMethod(getMethod);
        Map<String, String> responseHeader = getResponseHeader(getMethod);
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            if (responseHeader.get("content-type").equals(contentType)) {
                text = "";
                her.setFileBytes(getMethod.getResponseBody());
            }
            Map<String, String> cookieMap = getCookieFromResponseHeader(getMethod);
            her.setCode(code).setResult(text).setResponseCookie(cookieMap).setResponseHeader(responseHeader);
            if (code == HttpStatus.SC_MOVED_TEMPORARILY) {
                her.setRedirectUrl(her.getResponseHeader().get("location"));
            }
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return her;
    }
}
