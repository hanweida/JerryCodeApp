package com.jerry.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: weida
 * Date: 14-3-18
 * Time: 下午5:30
 * Domain 设置域名工具类，在联盟，推广的首页中，跳转的时候，实现域名改变，cookie共享
 */

public class DomainUtil {

    /**
     * 操作CookieTool的addCookie中，加入setDomain,设置一级域名，使推广，联盟的二级域名共享cookie
     * @Methods Name setCookieDomain
     * @Create In 2014-4-22 By weida
     * @param cookie
     */
    public static void setCookieDomain(Cookie cookie, HttpServletRequest request){
        //根据配置文件得到一级域名
        String domain = "";
        String uri = request.getRequestURI();
        //boolean backToFront = false; 暂不考虑后台运营从推广账户跳转联盟
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookieTemp : cookies){
                if(("adminCookie").equals(cookieTemp.getName())){
                    //判断后台管理员登陆后，不再修改后台域名，防止出现两个adminCookie
                    if (!cookie.getName().equals("adminCookie")){
                        domain = null;
                        break;
                    }
                    //backToFront = true;
                }
            }
        }
        if(StringUtils.isNotBlank(domain)){
            cookie.setDomain(domain);
        }
       //request.getSession().setAttribute("backToFront",backToFront);
    }

    /**
     * 初始化Domain一级域名
     * @Methods Name initDomain
     * @Create In 2014-4-22 By weida
     * @param request
     */
    public static void initDomain(HttpServletRequest request){
        ServletContext servletContext = request.getSession().getServletContext();
        if((servletContext.getAttribute("domain")) == null){
            String domain = request.getServerName();
            //InitSys.domainMap.put("domain",domain);
            servletContext.setAttribute("domain",domain);
        }
    }
}
