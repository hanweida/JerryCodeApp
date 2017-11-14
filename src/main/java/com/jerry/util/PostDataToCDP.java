package com.jerry.util;

import com.easou.consts.CommonConst;
import com.easou.obj.ManageUser;
import com.easou.obj.Plan;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

/**
 * 给cdp端发送数据
 * @Class Name PostDataToCDP
 * @Author zhenyu
 * @Create In 2012-2-27
 */
public class PostDataToCDP {
	private static final Logger log = LogWriter.getFrontLog();
	
	//给cdp接口发送数据的url
	private static String upInfoServletURL=PropertiesUtil.getProperties("upInfoServletURL", "http://59.151.6.189:9090/dp/ups?ver=0");
	
	/**
	 * 给cdp发送用户数据
	 * @Methods Name postUserData
	 * @Create In 2012-2-27 By zhenyu
	 * @param type
	 * @param mu void
	 */
	public static void postUserData(int type, ManageUser mu){
//		String url = upInfoServletURL+"&t="+type+"&u="+mu.getUserid()+
//				"&bd="+mu.getBudgetday()+"&b="+mu.getBudget()+"&vs="+mu.getValuestat()+
//				"&ic="+mu.getIncome()+"&oc="+mu.getOutcome()+"&v="+mu.getValue()+
//				"&p=0&bs=0";
		StringBuilder sb = new StringBuilder(upInfoServletURL);
		sb.append("&t="+type);
		sb.append("&u="+mu.getUserid());
		sb.append("&p=0");
		sb.append("&bd="+mu.getBudgetday());
		sb.append("&b="+mu.getBudget());
		sb.append("&v="+mu.getValue());
		sb.append("&bs=0");
		sb.append("&ic="+mu.getIncome());
		sb.append("&oc="+mu.getOutcome());
		sb.append("&vs="+mu.getValuestat());
		
		String sRet = getContent(sb.toString());
		if(CommonConst.WEB_OK.equals(sRet)) {
			log.info(sRet + ",r:" + sb.toString());
		} else {
			log.error(sRet + ",r:" + sb.toString());
		}
	}
	
	/**
	 * 给cdp端发送计划数据
	 * @Methods Name postPlanData
	 * @Create In 2012-2-27 By zhenyu
	 * @param type
	 * @param p void
	 */
	public static void postPlanData(int type, Plan p){
//		String url = upInfoServletURL+"&t="+type+"&u="+p.getUserid()+
//				"&bd="+p.getBudgetday()+"&b="+p.getBudget()+"&vs=0"+
//				"&ic=0&oc=0&v="+p.getValue()+
//				"&p="+p.getId()+"&bs="+p.getBudgetstat();			
		StringBuilder sb = new StringBuilder(upInfoServletURL);
		sb.append("&t="+type);
		sb.append("&u="+p.getUserid());
		sb.append("&p="+p.getId());
		sb.append("&bd="+p.getBudgetday());
		sb.append("&b="+p.getBudget());
		sb.append("&v="+p.getValue());
		sb.append("&bs="+p.getBudgetstat());
		sb.append("&ic=0");
		sb.append("&oc=0");
		sb.append("&vs=0");
		//增加展示推广调用cdp的参数 by likang on 2013-1-5 begin 
		sb.append("&promotion_type=" + p.getPromotionType());
		sb.append("&payment_type=" + p.getPaymentType());
		if (p.getShowBudget() != null) {
			sb.append("&show_budget=" + p.getShowBudget());
		} else {
			sb.append("&show_budget=0");
		}
		//增加展示推广调用cdp的参数 by likang on 2013-1-5 end

		String sRet = getContent(sb.toString());
		if(CommonConst.WEB_OK.equals(sRet)) {
			log.info(sRet + ",r:" + sb.toString());
		} else {
			log.error(sRet + ",r:" + sb.toString());
		}
	}
	
	/**
	 * 访问url
	 * @Methods Name getContent
	 * @Create In 2011-12-13 By zhenyu
	 * @param strUrl
	 * @return String
	 */
	private static String getContent(String strUrl){
		GetMethod getMethod = null;
		String result = "";
		 try{	   
			getMethod = new GetMethod(strUrl);
			HttpTool.httpClient.executeMethod(getMethod);
			result = getMethod.getResponseBodyAsString();
			return result;
		  }
		  catch(Exception e){
			  log.error(e);
		  }finally{
				if (getMethod != null) {
					getMethod.releaseConnection();
				}
		  }
		  return null;
	}
	
}
