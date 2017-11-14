package com.jerry.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取客户端的真实ip地址
 */
public class RealIpUtils {

    /**
     * 取所有IP段的私有IP段
     * A类  私有地址  10.0.0.0---10.255.255.255  保留地址 127.0.0.0---127.255.255.255
     * B类  私有地址 172.16.0.0-172.31.255.255
     * C类  私有地址 192.168.0.0-192.168.255.255
     * D类  地址不分网络地址和主机地址
     * E类  地址不分网络地址和主机地址
     */
    private static long aBegin = ip2long("10.0.0.0");
    private static long aEnd = ip2long("10.255.255.255");
    private static long bBegin = ip2long("172.16.0.0");
    private static long bEnd = ip2long("172.31.255.255");
    private static long cBegin = ip2long("192.168.0.0");
    private static long cEnd = ip2long("192.168.255.255");
    private static long saveBegin = ip2long("127.0.0.0");
    private static long saveEnd = ip2long("127.255.255.255");


    public static interface IpHeaderHandler {
        String getIp();
        void setNowValue(String value);
        String getNowValue();
    }

    private static class BaseIpHeaderHandler implements IpHeaderHandler {

        private String nowValue;

        @Override
        public String getIp() {
            String nowIp = "";
            if (nowValue == null || nowValue.isEmpty()) return nowIp;
            if(nowValue.indexOf(",")>0){
                String[] ipList = nowValue.split(",");
                for (String tempIp : ipList) {
                    long ip = ip2long(tempIp);
                    //取第一个不是内网IP的IP作为真实IP
                    if (ip > 0 && (!isInnerIp(ip))) {
                        nowIp = tempIp;
                    }
                }
            }else{
                long ip = ip2long(nowValue);
                nowIp = (ip < 0 || isInnerIp(ip)) ? "" : nowValue;
            }

            return nowIp;
        }

        @Override
        public String getNowValue() {
            return nowValue;
        }
        @Override
        public void setNowValue(String nowValue) {
            this.nowValue = nowValue;
        }
    }

    //跟IP有关需要做判断的header参数
    private static Map<String, IpHeaderHandler> HEADER_HANDLERS = new HashMap<String, IpHeaderHandler>() {
        {
            //终端的IP即终端上网时动态分配的IP
            put("clientip", new BaseIpHeaderHandler());
            //简称XFF头，它代表客户端，也就是HTTP的请求端真实的IP
            put("x-forwarded-for", new BaseIpHeaderHandler());
            //代理客服端IP
            put("proxy-client-ip", new BaseIpHeaderHandler());
            //WebLogic代理客服端IP（weblogic设置了才会有这个参数）
            put("wl-proxy-client-ip", new BaseIpHeaderHandler());
        }
    };

    /**
     * @param request
     * @return
     */
    public static String getRealIpAddress(HttpServletRequest request) {
        //防止在header中的参数名有大小写之分，重新将需要处理的参数值和内容装填入Map中
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            IpHeaderHandler handler = HEADER_HANDLERS.get(name.toLowerCase());
            if (handler == null) {
                continue;
            }
            handler.setNowValue(request.getHeader(name));
        }

        //取正确的IP
        String ipAddress = null;
        IpHeaderHandler handler = null;
        handler = HEADER_HANDLERS.get("clientip");//取clientip与client-ip有区别
        if(handler!=null){
            ipAddress = handler.getIp();
        }

        //若clientip为空或者是内网IP则取x-forwarded-for
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            handler = HEADER_HANDLERS.get("x-forwarded-for");//取clientip与client-ip有区别
            if(handler!=null){
                ipAddress = handler.getIp();
            }
        }

        //若x-forwarded-for为空则取proxy-client-ip
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            handler = HEADER_HANDLERS.get("proxy-client-ip");//取clientip与client-ip有区别
            if(handler!=null){
                ipAddress = handler.getIp();
            }
        }

        //若proxy-client-ip为空则取wl-proxy-client-ip
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            handler = HEADER_HANDLERS.get("wl-proxy-client-ip");//取clientip与client-ip有区别
            if(handler!=null){
                ipAddress = handler.getIp();
            }
        }

        //若wl-proxy-client-ip为空则取RemoteAddr
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }

        return ipAddress;
    }


    /**
     * @param ip
     * @return
     */
    public static boolean isInnerIp(long ip) {
        return isInner(ip, aBegin, aEnd) ||
                isInner(ip, bBegin, bEnd) ||
                isInner(ip, cBegin, cEnd) ||
                isInner(ip, saveBegin, saveEnd);
    }

    private static boolean isInner(long ip, long begin, long end) {
        return (ip >= begin) && (ip <= end);
    }

    /**
     * @param ipAddress
     * @return
     */
    public static long ip2long(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return -1;
        }
        long ip = 0, v = 0;
        int p1 = 0, p2 = 0;
        // 1
        p2 = ipAddress.indexOf('.', p1);
        if (p2 < 0) return -1;
        try {
            v = Long.parseLong(ipAddress.substring(p1, p2));
            if (v < 0 || v > 255) return -1;
            ip |= (v << 24);
            // 2
            p1 = p2 + 1;
            p2 = ipAddress.indexOf('.', p1);
            if (p2 < 0) return -1;
            v = Long.parseLong(ipAddress.substring(p1, p2));
            if (v < 0 || v > 255) return -1;
            ip |= (v << 16);
            // 3
            p1 = p2 + 1;
            p2 = ipAddress.indexOf('.', p1);
            if (p2 < 0) return -1;
            v = Long.parseLong(ipAddress.substring(p1, p2));
            if (v < 0 || v > 255) return -1;
            ip |= (v << 8);
            // 4
            v = Long.parseLong(ipAddress.substring(p2 + 1));
            if (v < 0 || v > 255) return -1;
            ip |= v;
            return ip;
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
