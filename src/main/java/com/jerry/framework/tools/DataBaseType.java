package com.jerry.framework.tools;
/**
 * mysql数据库类型与java转换
 * @Class Name DataBaseType
 * @Author likang
 * @Create In 2013-9-20
 */
public class DataBaseType {

	public static String getPojoType(String ct) {
		// TODO Auto-generated method stub
		ct = ct.toUpperCase();
		String javaType = "";
		if (ct.equals("TIMESTAMP")
				||ct.equals("DATETIME")
				||ct.equals("TIME")
				||ct.equals("DATE")) {
			javaType = "java.util.Date";
		} else if (ct.equals("VARCHAR")|| ct.equals("CHAR")) {
			javaType = "String";
		} else if(ct.equals("INT") || ct.equals("TINYINT")){
			javaType = "Integer";
		} else if(ct.equals("LONG")){
			javaType = "Long";
		} else if(ct.equals("FLOAT")){
			javaType = "Float";
		} else if(ct.equals("DOUBLE")){
			javaType = "Double";
		}
		return javaType;
	}

}
