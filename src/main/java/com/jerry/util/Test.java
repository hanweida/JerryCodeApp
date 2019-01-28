package com.jerry.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class Test{
public static String httpClient3Test() {

        HttpClient httpClient = new HttpClient();
        HttpMethod method = new GetMethod("https://kyfw.12306.cn/otn/leftTicket/queryZ?leftTicketDTO.train_date=2019-01-02&leftTicketDTO.from_station=BJP&leftTicketDTO.to_station=HBB&purpose_codes=ADULT");
    //httpClient.getHostConfiguration().setProxy("localhost", 8888);

    try {
        int code = httpClient.executeMethod(method);
        System.out.println("code: "+code);
        System.out.println("getURI: "+method.getURI());
        System.out.println("getStatusLine: "+method.getStatusLine());
        System.out.println("getName: "+method.getName());
        System.out.println("getResponseHeader: "+method.getResponseHeader("Server").getValue());
        System.out.println("getResponseBodyAsString: "+method.getResponseBodyAsString());
        }
        catch (Exception e) {
        e.printStackTrace();
        }

        return "httpClient3 test success";
}

    public static void main(String[] args) {
        Test.httpClient3Test();
    }
}

