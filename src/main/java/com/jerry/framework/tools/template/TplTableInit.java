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
 * 数据库table映射为实体
 */
public class TplTableInit {

	public static final int SQLSERVER = 1;
	public static final int MYSQL = 2;
	public static final int ORACLE = 3;
	private static final String LINE = "\r\n";
	private static final String TAB = "\t";
    public static List<TableColum> TABLE_COLUM_LIST = new ArrayList<TableColum>();

    public TplTableInit(String tableName) {
        Connection conn = getConnection();
        getTableColum(conn,tableName);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
	 *
	 * @Methods Name getConnection
	 * @Create In 2015-1-22 By likang
	 * @return
	 * @throws ClassNotFoundException
	 * @throws java.sql.SQLException Connection
	 */
	private static Connection getConnection() {
        try {
            return getConnection("10.26.28.189:3619", "test", "jingjia","ecom@easou", TplTableInit.MYSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    private static Connection getConnection(String ipport, String dbName, String username, String password, int type) throws ClassNotFoundException, SQLException {
		String jdbcString = null;
		if (type == SQLSERVER) {
			jdbcString = "jdbc:jtds:sqlserver://" + ipport + ";databaseName=" + dbName;
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		} else if (type == MYSQL) {
			jdbcString = "jdbc:mysql://" + ipport + "/" + dbName;
			Class.forName("org.gjt.mm.mysql.Driver");
		} else if (type == ORACLE) {
			jdbcString = "jdbc:oracle:thin:@" + ipport + ":" + dbName;
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		Connection connection = null;
		connection = DriverManager.getConnection(jdbcString, username, password);
		return connection;
	}


    private static synchronized void getTableColum(Connection connection, String tableName) {
        TABLE_COLUM_LIST.clear();
        try {
            Map<String, String> map = getComment(connection,tableName);
            String sql = "select * from " + tableName + " where 1 <> 1";
            PreparedStatement ps = null;
            ResultSet rs = null;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();
            tableName = tableName.substring(0, 1).toUpperCase() + tableName.subSequence(1, tableName.length());
            for (int i = 1; i <= columnCount; i++) {
                TableColum tc = new TableColum();
                //id字段名
                tc.setName(getMemberName(md.getColumnName(i)));
                //中文描述
                String nameCn = map.get(md.getColumnName(i));
                //中文描述取 注释中第一个空格之前的文字 一般用空格 隔断 后面做具体值说明 列如：（通知类别 1:系统升级 2:其他）取通知类别作为描述
                //若无中文描述则用字段英文
                nameCn = StringUtils.isNotBlank(nameCn) ? nameCn.split(" ")[0] : tc.getName();
                tc.setNameCn(nameCn);
                TABLE_COLUM_LIST.add(tc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 获得成员变量驼峰命名
	 * @Methods Name getMemberName
	 * @Create In 2015-1-21 By likang
	 * @param columnName
	 * @return String
	 */
	private static String getMemberName(String columnName) {
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


	private static Map<String, String> getComment(Connection connection,String tableName) {
		// TODO Auto-generated method stub
        Map<String, String> map = new HashMap<String, String>();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
		return map;
	}


}
