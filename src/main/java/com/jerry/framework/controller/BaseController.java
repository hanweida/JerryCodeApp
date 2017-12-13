package com.jerry.framework.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;

import com.alibaba.fastjson.JSON;

public class BaseController{
	    
    
    /**
     * 取PrintWriter
     * @Methods Name getPrintWriter
     * @Create In Jul 26, 2012 By likang
     * @param response
     * @return PrintWriter
     */
    protected PrintWriter getPrintWriter(HttpServletResponse response) {
    	response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return out;
    }
    
    protected void print(HttpServletResponse response,String content) {
    	response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(content);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
}
