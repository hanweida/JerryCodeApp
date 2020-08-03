package com.jerry.util.http;

import org.apache.commons.httpclient.HttpMethodBase;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;


public class ResponseUtil {

    public static String getResponseBodyGizpAsString(HttpMethodBase methodBase) {
        String result = "";
        GZIPInputStream gzin;
        try {
            if (methodBase != null && methodBase.getResponseBodyAsStream() != null) {
                //For GZip response
                gzin = new GZIPInputStream(methodBase.getResponseBodyAsStream());
                InputStreamReader isr = new InputStreamReader(gzin, methodBase.getResponseCharSet());
                java.io.BufferedReader br = new java.io.BufferedReader(isr);
                StringBuffer sb = new StringBuffer();
                String tempbf;
                while ((tempbf = br.readLine()) != null) {
                    sb.append(tempbf);
                    sb.append("\r\n");
                }
                isr.close();
                gzin.close();
                result = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

}
