package com.jerry.util;

import com.easou.mis.controller.util.DataReportUtil;
import com.easou.mis.dao.UserListDao;
import com.easou.mis.pojo.FrontUser;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 处理header部分的用户权限显示
 * @Class Name HeaderUtil
 * @Author likang
 * @Create In 2013-1-4
 */
public class HeaderUtil {


	/**
	 * 是否有展示推广权限
	 * @Methods Name hasShowPromotionRole
	 * @Create In 2013-1-4 By likang
	 * @return boolean
	 */
	public static boolean hasShowPromotionRole(HttpServletRequest request, HttpServletResponse response) {
		boolean has = false;
		String roleKey = "spRole";
		Map<String, String> userModel = DataReportUtil.getUserID(request);
		Cookie spRolecCookie = CookieTool.getCookieByName(request, roleKey);
		//如果cookie有展示推广权限值
		if (spRolecCookie != null) {
			if (spRolecCookie.getValue().equals("true")) {
				has = true;
			} 
		} else {
	    //如果cookie没有展示推广权限值	
			if (userModel != null) {
				String userName = userModel.get("userName");
				if (StringUtils.isNotBlank(userName)) {
					UserListDao ulDao = UserListDao.getInstance();
					FrontUser fUser = ulDao.getFrontUser(userName);
					String cookieTimeout = PropertiesUtil.getProperties("cookieTimeout", "1200");//cookie过期时间，单位：秒 ， 60*20
					//用户类型：1-普通用户，2-大客户，3-内部用户
					if (fUser != null && fUser.getUserType() == 2) {
						//初始化
						has = true;
                        //edit by weida for 20140424 增加request参数
                        CookieTool.addCookie(request, response, roleKey, has+"", Integer.valueOf(cookieTimeout));//加入cookie
                        //end by weida for 20140424 增加request参数
                    } else {
                        //edit by weida for 20140424 增加request参数
						CookieTool.addCookie(request, response, roleKey, has+"", Integer.valueOf(cookieTimeout));//加入cookie
                        //end by weida for 20140424 增加request参数
                    }
				}
			}
		}
		return has;
	}
}
