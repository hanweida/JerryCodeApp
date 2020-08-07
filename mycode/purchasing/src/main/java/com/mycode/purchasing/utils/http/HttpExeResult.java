package com.mycode.purchasing.utils.http;

import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Class: HttpExeResult
 * User: likang
 * Date: 17-2-27
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public class HttpExeResult {
    private int code;
    private String result;
    private Map<String,String> responseCookie;
    private String redirectUrl;
    private Map<String,String> responseHeader;
    private File responseFile;
    private byte[] fileBytes;

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public HttpExeResult() {
    }

    public Map<String, String> getResponseHeader() {
        return responseHeader;
    }

    public HttpExeResult setResponseHeader(Map<String, String> responseHeader) {
        this.responseHeader = responseHeader;
        return this;
    }

    public int getCode() {
        return code;
    }

    public HttpExeResult setCode(int code) {
        this.code = code;
        return this;
    }

    public String getResult() {
        return result;
    }

    public HttpExeResult setResult(String result) {
        this.result = result;
        return this;
    }

    public Map<String,String> getResponseCookie() {
        return responseCookie;
    }

    public String getResponseCookieToStr() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for(Map.Entry<String, String>  entry: this.getResponseCookie().entrySet()) {
            if (i > 0) {
                sb.append(";");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            i++;
        }
        return sb.toString();
    }


    public String getResponseCookieToStrByKey(String key) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String>  entry: this.getResponseCookie().entrySet()) {
            if(entry.getKey().equals(key)) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                break;
            }
        }
        return sb.toString();
    }

    public HttpExeResult setResponseCookie(Map<String,String> responseCookie) {
        this.responseCookie = responseCookie;
        return this;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public HttpExeResult setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }

    public File getResponseFile() {
        return responseFile;
    }

    public void setResponseFile(File responseFile) {
        this.responseFile = responseFile;
    }

    @Override
    public String toString() {
        return "HttpExeResult{" +
                "code=" + code +
                ", result='" + result + '\'' +
                ", cookie=" + responseCookie +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", header=" + responseHeader +
                '}';
    }
}
