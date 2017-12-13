package com.jerry.framework.tools;

import com.jerry.framework.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库表映射为pojo类
 * @Class Name DBUtil
 * @Author likang
 * @Create In 2015-1-22
 */
public class DBUtil {

	public static final int SQLSERVER = 1;
	public static final int MYSQL = 2;
	public static final int ORACLE = 3;
	private static final String LINE = "\r\n";
	private static final String TAB = "\t";
	


	/**
	 * 
	 * @Methods Name getConnection
	 * @Create In 2015-1-22 By likang
	 * @return
	 * @throws ClassNotFoundException
	 * @throws java.sql.SQLException Connection
	 */
	public static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		return getConnection("10.26.28.189:3619", "cpd", "ecom_cpd",
				"cpd@easou", DBUtil.MYSQL);
//		return getConnection("10.23.44.189:3619", "lockdb", "lockdb",
//				"ecom.lockdb", DBUtil.MYSQL);
	}

	/**
	 * 
	 * 通过jdbc获取相应的数据库链接connection
	 * 
	 * @param ipport
	 *            ip+port ,eg.: 192.168.0.161:1997
	 * @param dbName
	 *            databaseName ,eg. : ETForMonitor_V2
	 * @param username
	 *            eg.:sa
	 * @param password
	 *            eg. :password
	 * @param type
	 *            请看本类的静态变量
	 * @return
	 * @throws ClassNotFoundException
	 * @throws java.sql.SQLException
	 */
	public static Connection getConnection(String ipport, String dbName,
			String username, String password, int type)
			throws ClassNotFoundException, SQLException {
		String jdbcString = null;
		if (type == SQLSERVER) {
			jdbcString = "jdbc:jtds:sqlserver://" + ipport + ";databaseName="
					+ dbName;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} else if (type == MYSQL) {
			jdbcString = "jdbc:mysql://" + ipport + "/" + dbName;
			Class.forName("org.gjt.mm.mysql.Driver");
		} else if (type == ORACLE) {
			jdbcString = "jdbc:oracle:thin:@" + ipport + ":" + dbName;
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		Connection connection = null;
		connection = DriverManager
				.getConnection(jdbcString, username, password);
		return connection;
	}

	/**
	 * 数据库表生成相应的java类，生成规则 类名= 表名（第一个字母大写） 属性名= 数据库列名 get/set方法 = 根据标准生成
	 * 其中生成的基本类型均为包装类，例如Integer , Long , Boolean , String
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String table2pojo(String tableName,String className,String packagePath) throws Exception {
        Connection connection = getConnection();

        Map<String, String> map = getComment(connection,tableName);
		
		String sql = "select * from " + tableName + " where 1 <> 1";
		PreparedStatement ps = null;
		ResultSet rs = null;
		ps = connection.prepareStatement(sql);
		rs = ps.executeQuery();
		ResultSetMetaData md = rs.getMetaData();
		int columnCount = md.getColumnCount();
		StringBuffer sb = new StringBuffer();
		tableName = tableName.substring(0, 1).toUpperCase()
				+ tableName.subSequence(1, tableName.length());
		
		sb.append("package" + TAB + packagePath + ";");
		sb.append(LINE);
		sb.append(LINE);
        sb.append("import com.alibaba.fastjson.annotation.JSONField;");
        sb.append(LINE);
        sb.append(LINE);
        //生成类注释
		sb.append(getClassComment(className,map));
		sb.append("public class " + className + " {");
		sb.append(LINE);
		for (int i = 1; i <= columnCount; i++) {
			sb.append(TAB);
			sb.append("private "
					+ DataBaseType.getPojoType(md.getColumnTypeName(i)) + " "
					+ getMemberName(md.getColumnName(i)) + ";" + TAB + "//" + map.get(md.getColumnName(i)));
			// System.out.println("name : " + md.getColumnName(i) +
			// " , type :"
			// + md.getColumnTypeName(i));
			sb.append(LINE);
		}
		//拼获取列明的串 解决实体中有 不属于数据库字段的情况
		StringBuffer getColumnSb = new StringBuffer();
		for (int i = 1; i <= columnCount; i++) {
			sb.append(TAB);
			String pojoType = DataBaseType.getPojoType(md.getColumnTypeName(i));
			String columnName = md.getColumnName(i);
			String methodName = getMethodName(columnName);
			String memberName = getMemberName(columnName);
			String getName = null;
			String setName = null;
			if (columnName.length() > 1) {
				getName = "public " + pojoType + " get"
						+ methodName + "() {";
				setName = "public void set"
						+ methodName + "("
						+ pojoType + " " + memberName + ") {";
			} else {
				getName = "public get" + methodName + "() {";
				setName = "public set" + methodName + "("
						+ pojoType + " " + methodName + ") {";
			}
			sb.append(LINE).append(TAB).append(getName);
			sb.append(LINE).append(TAB).append(TAB);
			sb.append("return " + memberName + ";");
			sb.append(LINE).append(TAB).append("}");
			sb.append(LINE);
			sb.append(LINE).append(TAB).append(setName);
			sb.append(LINE).append(TAB).append(TAB);
			sb.append("this." + memberName + " = " + memberName + ";");
			sb.append(LINE).append(TAB).append("}");
			sb.append(LINE);
			
			getColumnSb.append(TAB).append(TAB).append("if (memberName.equals(\""+memberName+"\")) {");
			getColumnSb.append(LINE);
			getColumnSb.append(TAB).append(TAB).append(TAB).append("return \"" + columnName + "\";");
			getColumnSb.append(LINE);
			getColumnSb.append(TAB).append(TAB).append("}");
			getColumnSb.append(LINE);
			
		}
		//**获取表名的方法 begin
		sb.append(LINE);
        //增加json序列化限制 不序列化此属性
        sb.append(TAB).append("@JSONField(serialize=false)");
        sb.append(LINE);
        sb.append(TAB).append("public String getTableName() {");
		sb.append(LINE);
		sb.append(TAB).append(TAB).append("return \""+tableName.toLowerCase()+"\";");
		sb.append(LINE);
		sb.append(TAB).append("}");
		sb.append(LINE);
		//**获取表名的方法 end
		
		//**获取列名的方法 begin
		sb.append(LINE);
		sb.append(TAB).append("public String getColumnName(String memberName) {");
		sb.append(LINE);
		sb.append(getColumnSb);
		sb.append(TAB).append(TAB).append("return null;");
		sb.append(LINE);
		sb.append(TAB).append("}");
		sb.append(LINE);
		//**获取表名的方法 end
		
		sb.append("}");
		System.out.println(sb.toString());
		return sb.toString();
	}


    public static void table2pojo2File(String tableName,String className,String path,String packagePath) throws Exception {
        stringToFile(path + "\\"+className + ".java", table2pojo(tableName,className,packagePath));
    }



    private static String getClassComment(String className, Map<String, String> map) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		sb.append("/**");
		sb.append(LINE);
		sb.append("* ").append(map.get("table_comment") == null? "" : map.get("table_comment"));
		sb.append(LINE);
		sb.append("* ").append("@Class Name ").append(className);
		sb.append(LINE);
		sb.append("* ").append("@Author sys ");
		sb.append(LINE);
		sb.append("* ").append("@Create In " + sdf.format(new Date()));
		sb.append(LINE);
		sb.append("*/");
		sb.append(LINE);
		return sb.toString();
	}

	/**
	 * 获得成员变量驼峰命名
	 * @Methods Name getMemberName
	 * @Create In 2015-1-21 By likang
	 * @param columnName
	 * @return String
	 */
	public static String getMemberName(String columnName) {
		// TODO Auto-generated method stub
		StringBuffer sBuffer = new StringBuffer();
		String[] arrStrings = columnName.split("_");
		for (int i = 0; i < arrStrings.length; i++) {
			if (i == 0) {
				sBuffer.append(StringUtil.toLowerCaseFirstOne(arrStrings[i]));
			} else {
				sBuffer.append(StringUtil.toUpperCaseFirstOne(arrStrings[i]));
			}
		}
		return sBuffer.toString();
	}
	
	/**
	 * 获取方法名 所有首字母大写驼峰
	 * @Methods Name getMethodName
	 * @Create In 2015-1-22 By likang
	 * @param columnName
	 * @return String
	 */
	private static String getMethodName(String columnName) {
		// TODO Auto-generated method stub
		StringBuffer sBuffer = new StringBuffer();
		String[] arrStrings = columnName.split("_");
		for (int i = 0; i < arrStrings.length; i++) {
			sBuffer.append(StringUtil.toUpperCaseFirstOne(arrStrings[i]));
		}
		return sBuffer.toString();
	}

	private static Map<String, String> getComment(Connection connection,
			String tableName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		String sql = "SELECT column_name,column_comment FROM INFORMATION_SCHEMA.Columns WHERE table_name='"+tableName+"' ";
		Statement statement = connection.createStatement();
		ResultSet rSet = statement.executeQuery(sql);
		while (rSet.next()) {
			map.put(rSet.getString("column_name"), rSet.getString("column_comment"));
		}
		//增加取表注释
		sql = "SELECT TABLE_COMMENT  FROM INFORMATION_SCHEMA.TABLES  WHERE table_name='"+tableName+"'";
		rSet = statement.executeQuery(sql);
		while (rSet.next()) {
			map.put("table_comment", rSet.getString("table_comment"));
		}
		return map;
	}

	private static void stringToFile(String path, String content) throws Exception {
		// TODO Auto-generated method stub
		File textFile = new File(path);
		if (!textFile.exists()) {
			textFile.createNewFile();
			System.out.println("create file path:" + textFile.getPath());
		}
		FileWriter fWriter = new FileWriter(textFile);
		fWriter.write(content);
		fWriter.flush();
		fWriter.close();
		 
	}

	public static void main(String[] args) throws Exception,
			ClassNotFoundException {

	}
}
