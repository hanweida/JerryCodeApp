package com.jerry.framework.tools.template;

import com.jerry.framework.util.StringUtil;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库表映射为pojo类
 * @Class Name DBUtil
 * @Author likang
 * @Create In 2015-1-22
 */
public class TemplateMain {

	public static void main(String[] args) throws Exception {
        String tableName = "t_sys_fun";
        List<TableColum> tableColumList = new TplTableInit(tableName).TABLE_COLUM_LIST;
        String POJOPATH = "E:\\workings\\myeclipse10.6\\MyBatis_Spring3_Maven\\mybatis\\src\\main\\java\\com\\easou\\ecom\\framework\\pojo";
		String PACKAGE = "com.easou.ecom.framework.pojo";
        TplConfig tbc = new TplConfig();
        String pojoClassName = "SysFun";
        tbc.setPojoClassName(pojoClassName);
        tbc.setControllerAndJspUrl("sys/loginLog");
        tbc.setJspFileModelNameCn("登陆日志");
        tbc.setJspFilePrefix("login_log");
        //jsp首页文件
        TplBuilder tb = new JavaControllerPageTplBuilder(tbc,tableColumList);
//        System.out.println(tb.build());

    }
}
