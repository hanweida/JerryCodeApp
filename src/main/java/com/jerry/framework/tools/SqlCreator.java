package com.jerry.framework.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.jdbc.SelectBuilder;
import org.apache.ibatis.jdbc.SqlBuilder;

import com.jerry.framework.util.BeanUtil;


/**
 * sql生成器
 * @Class Name SqlCreator
 * @Author 005
 * @Create In 2013-9-20
 */
public class SqlCreator {
	
	/**
	 * 根据class取映射的数据库表名，生成insert语句
	 * @Methods Name getInsertSql
	 * @Create In 2013-8-20 By 005
	 * @param object
	 * @return String
	 */
	public static String getInsertSql(Object object) {
		SqlBuilder sqlBuilder = new SqlBuilder();
		Map<String, String> objectMap = objectParseToMap(object);
		Iterator<String> iterator = objectMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!key.equals("table_name")) {
				if (objectMap.get(key) != null) {
					sqlBuilder.VALUES(key, "'"+objectMap.get(key).replace("\'", "\\'")+"'");
				}
			} else {
				sqlBuilder.INSERT_INTO(objectMap.get(key));
			}
			
		}
		return sqlBuilder.SQL();
	}
	
	/**
	 * 根据class取映射的数据库表名，生成单表select语句
	 * @Methods Name getSelectSql
	 * @Create In 2013-8-20 By 005
	 * @param clazz
	 * @param queryMap 查询条件
	 * @param orderBy  排序字段
	 * @param isAsc    是否是升序
	 * @return String
	 */
	public static String getSelectSql(Class clazz, Map queryMap,String orderBy, Boolean isAsc) {
		SelectBuilder selectBuilder = new SelectBuilder();
		Map<String, String> clazzMap = clazzParseToMap(clazz);
		Iterator<String> iterator = null;
		if (queryMap != null) {
			iterator = queryMap.keySet().iterator();
			//遍历queryMap 取查询条件
			while (iterator.hasNext()) {
				String key = iterator.next();
				//约定默认所有条件用等于 如果用其他限制条件 需要将key写为
				//key_条件 这样key  例如 name_like,name_<>,name_not null,name_in
				if (key.contains("_")) {
					String column = key.split("_")[0];
					String condition = key.split("_")[1];
					//容错处理 防止querymap中的参数 不是数据库字段
					if (clazzMap.containsKey(column)) {
						if (queryMap.get(key) instanceof String || queryMap.get(key) instanceof Date) {
							if (condition.equals("like")) {
								selectBuilder.WHERE(column+" "+condition+" "+"'%"+queryMap.get(key)+"%'");
							} else if (condition.equals("in")) {
								selectBuilder.WHERE(column+" "+condition+" "+queryMap.get(key));
							} else {
								selectBuilder.WHERE(column+" "+condition+" "+"'"+queryMap.get(key)+"'");
							}
						} else {
							selectBuilder.WHERE(column+" "+condition+" "+queryMap.get(key));
						}
					}
				} else {
					if (clazzMap.containsKey(key)) {
						if (queryMap.get(key) instanceof String || queryMap.get(key) instanceof Date) {
							selectBuilder.WHERE(key + "='" + queryMap.get(key)+"'");
						} else {
							selectBuilder.WHERE(key + "=" + queryMap.get(key));
						}
					}
				}
			}
		}
		iterator = clazzMap.keySet().iterator();
		//遍历clazz取select条目和表名
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!key.equals("table_name")) {
				selectBuilder.SELECT(key);
			} else {
				selectBuilder.FROM(clazzMap.get(key));
			}
		}
		//排序条件
		if (orderBy != null && clazzMap.containsKey(orderBy)) {
			if (isAsc != null && isAsc) {
				selectBuilder.ORDER_BY(orderBy + " asc");
			} else if (isAsc != null && !isAsc) {
				selectBuilder.ORDER_BY(orderBy + " desc");
			}
		}
		return selectBuilder.SQL();
	}

	/**
	 * 将object 转换为 map（key成员变量名(只包含存在与数据库中的字段映射关系的变量)，value为成员变量的值）
	 * 用于更新、插入拼sql
	 * @Methods Name objectParseToMap
	 * @Create In 2013-8-20 By likang
	 * @param object
	 * @return Map<String,String>
	 */
	private static Map<String, String> objectParseToMap(Object object) {
		Map<String, String> objectMap = new HashMap<String, String>();
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Class clazz = object.getClass();
	        Method getColumnNameMethod = clazz.getMethod("getColumnName", new Class[] {String.class});
	        Method getTableNameMethod = clazz.getMethod("getTableName");

	        //所有方法
	        Method[] ms = clazz.getMethods();
	        //遍历所有方法取get方法
	        for(int i=0; i<ms.length; i++) {
	            String methodName = ms[i].getName();
	            if(methodName.startsWith("get")) {
	            	try {
	            		String memberName = methodName.substring(methodName.indexOf("get")+"get".length()).toLowerCase();
	                	String value = "";
	                	//调用 成员变量-数据库表字段 映射方法 判断该成员变量是否是 数据库中表字段
	                	String columnName = (String) getColumnNameMethod.invoke(object, memberName);
	                	//如果无映射 则不属于数据库字段 跳过
	                	if (columnName == null) {
	    					continue;
	    				}
	                	Object result = ms[i].invoke(object);
	                    //返回值类型
	                    if (result instanceof Date) {
	    					//如果日期类型 转换为 String
	                    	value = sdf.format(result);
	    				} else {
	    					if (result != null) {
		    					value = String.valueOf(result);
							}
	    				}
	                    if (result != null) {
		                    objectMap.put(columnName, (String)getNormalString(value));
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
	            }
	        }
	        objectMap.put("table_name", (String)getTableNameMethod.invoke(object));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return objectMap;
	}
	
	/**
	 * 通过反射得到所有与数据库中表做了映射的成员变量map
	 * @Methods Name clazzParseToMap
	 * @Create In 2013-9-24 By 005
	 * @param clazz
	 * @return Map<String,String>
	 */
	public static Map<String, String> clazzParseToMap(Class clazz) {
		Map<String, String> objectMap = new HashMap<String, String>();
		// TODO Auto-generated method stub
		try {
	        Method getColumnNameMethod = clazz.getMethod("getColumnName", new Class[] {String.class});
	        Method getTableNameMethod = clazz.getMethod("getTableName");
	        Field [] fields = clazz.getDeclaredFields();
	        //所有方法
	        Method[] ms = clazz.getMethods();
	        Object object = clazz.newInstance();
	        //遍历所有方法取get方法
	        for(int i=0; i<fields.length; i++) {
//	            String methodName = ms[i].getName();
//	        	String fieldName = fields[i].getName();
            	try {
            		String memberName = fields[i].getName();
            		if (memberName.equals("levelId")) {
						System.out.println("");
					}
                	String value = "";
                	//调用 成员变量-数据库表字段 映射方法 判断该成员变量是否是 数据库中表字段
                	String columnName = (String) getColumnNameMethod.invoke(object, memberName);
                	//如果无映射 则不属于数据库字段 跳过
                	if (columnName == null) {
    					continue;
    				}
                    objectMap.put(memberName, columnName);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	        }
	        objectMap.put("table_name", (String)getTableNameMethod.invoke(object));
		} catch (Exception e) {
			// TODO: handle exception
			e.fillInStackTrace();
		}
		return objectMap;
	}
	
	/**
	 * rs转object
	 * @Methods Name rsToObject
	 * @Create In 2013-9-23 By 005
	 * @param rs
	 * @param object
	 * @return Object
	 */
	public static Object rsToObject(ResultSet rs,Object object) {
		try {
			Map map = rsToMap(rs);
			BeanUtil.parseMap2Bean(map, object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return object;
	}
	
	/**
	 * 兼容其他非联盟已存在实体映射
	 * @Methods Name rsToObject4Compatibility
	 * @Create In 2014-3-22 By 005
	 * @param rs
	 * @param object
	 * @return Object
	 */
	public static Object rsToObject4Compatibility(ResultSet rs,Object object) {
		try {
			Map map = rsToMap4Compatibility(rs);
			BeanUtil.parseMap2Bean(map, object);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return object;
	}
	
	/**
	 * 转换为基础类型 如 int double float String 只返回一个值 用于 count等
	 * @Methods Name rsToObjectBaseDataType
	 * @Create In 2013-8-23 By 005
	 * @param rs
	 * @return Object
	 */
	public static Object rsToObjectBaseDataType(ResultSet rs,Object object) {
		try {
			Map map = rsToMap(rs);
			for(Object key:map.keySet()) {
				object = map.get(key);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * rs转map
	 * @Methods Name rsToMap
	 * @Create In 2013-9-23 By 005
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException Map
	 */
	public static Map rsToMap(ResultSet rs) throws SQLException {
        Map m = new HashMap();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int mdCount = rsmd.getColumnCount();
            for (int i = 1; i <= mdCount; i++) {
                    // all name convert to lower case
            	try {
            		//规定映射实体中 成员变量名称需要小写
                    m.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getObject(i));
				} catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
				}
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return m;
	}
	
	/**
	 * 兼容其他非联盟已存在实体映射
	 * @Methods Name rsToMap4Compatibility
	 * @Create In 2014-3-22 By 005
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException Map
	 */
	private static Map rsToMap4Compatibility(ResultSet rs) throws SQLException {
        Map m = new HashMap();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int mdCount = rsmd.getColumnCount();
            for (int i = 1; i <= mdCount; i++) {
                    // all name convert to lower case
            	try {
                    m.put(rsmd.getColumnLabel(i), rs.getObject(i));
				} catch (Exception e) {
					// TODO: handle exception
					//e.printStackTrace();
				}
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return m;
	}

	/**
	 * 通过id查询唯一一个实体
	 * @Methods Name getSelectSqlById
	 * @Create In 2013-8-21 By 005
	 * @param clazz
	 * @param id
	 * @return String
	 */
	public static String getSelectSqlOnlyOneObjectByParam(Class clazz, Map queryMap) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(getSelectSql(clazz, queryMap, null, null));
		if (sql.length() > 0) {
			sql.append(" limit 1");
		}
		return sql.toString();
	}

	/**
	 * 分页查询
	 * @Methods Name getSelectSqlByPageWithParam
	 * @Create In 2013-8-22 By 005
	 * @param clazz
	 * @param queryMap
	 * @param orderBy
	 * @param isAsc
	 * @param limit
	 * @param size
	 * @return String
	 */
	public static String getSelectSqlByPageWithParam(Class clazz, Map queryMap,String orderBy, Boolean isAsc,int limit,int size) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(getSelectSql(clazz, queryMap, orderBy, isAsc));
		if (sql.length() > 0) {
			sql.append(" limit " + limit + "," + size);
		}
		return sql.toString();
	}
	
	/**
	 * 通过sql分页
	 * @Methods Name getSelectSqlByPageWithParamForSql
	 * @Create In 2013-9-23 By 005
	 * @param sql
	 * @param limit
	 * @param size
	 * @return String
	 */
	public static String getSelectSqlByPageWithParamForSql(String sql,int limit,int size) {
		// TODO Auto-generated method stub
		StringBuffer sqlbBuffer = new StringBuffer(sql);
		if (sql.length() > 0 && size>0) {
			sqlbBuffer.append(" limit " + limit + "," + size);
		}
		return sqlbBuffer.toString();
	}


	/**
	 * 根据条件查询总数
	 * @Methods Name getSelectTotalByParam
	 * @Create In 2013-8-22 By 005
	 * @param clazz
	 * @param queryMap
	 * @return String
	 */
	public static String getSelectTotalByParam(Class clazz, Map queryMap) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(getSelectSql(clazz, queryMap, null, null));
		String sqlStr = sql.toString().toLowerCase();
		sqlStr = "select count(1) from " + sqlStr.substring(sqlStr.indexOf("from") + "from".length());
		return sqlStr;
	}
	
	/**
	 * 通过sql查总数
	 * @Methods Name getSelectSqlByPageWithParamForSql
	 * @Create In 2013-9-23 By 005
	 * @param sql
	 * @param limit
	 * @param size
	 * @return String
	 */
	public static String getSelectTotalByParamForSql(Class clazz, String sqlStrF) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer(sqlStrF);
		String sqlStr = sql.toString().toLowerCase();
		sqlStr = "select count(1) from ("+sqlStr+") as t";
		return sqlStr;
	}

	/**
	 * 生成单表update语句，带where 条件
	 * @Methods Name getUpdateSql
	 * @Create In 2013-8-23 By 005
	 * @param object
	 * @param queryMap
	 * @return String
	 */
	public static String getUpdateSql(Object object, Map queryMap) {
		SqlBuilder sqlBuilder = new SqlBuilder();
		Map<String, String> objectMap = objectParseToMap(object);
		Iterator<String> iterator = null;
		//update条件
		Map<String, String> clazzMap = clazzParseToMap(object.getClass());
		if (queryMap != null) {
			iterator = queryMap.keySet().iterator();
			//遍历queryMap 取查询条件
			while (iterator.hasNext()) {
				String key = iterator.next();
				//约定默认所有条件用等于 如果用其他限制条件 需要将key写为
				//key_条件 这样key  例如 name_like,name_<>,name_not null,name_in
				if (key.contains("_")) {
					String column = key.split("_")[0];
					String condition = key.split("_")[1];
					//容错处理 防止querymap中的参数 不是数据库字段
					if (clazzMap.containsKey(column)) {
						sqlBuilder.WHERE(column+" "+condition+" "+queryMap.get(key));
					}
				} else {
					if (clazzMap.containsKey(key)) {
						if (queryMap.get(key) instanceof String || queryMap.get(key) instanceof Date) {
							sqlBuilder.WHERE(key + "='" + getNormalString(queryMap.get(key))+"'");
						} else {
							sqlBuilder.WHERE(key + "=" + getNormalString(queryMap.get(key)));
						}
					}
				}
			}
		}
		//update表名和值
		iterator = objectMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (!key.equals("table_name")) {
				if (objectMap.get(key) != null) {
					sqlBuilder.SET(key+"='"+getNormalString(objectMap.get(key))+"'");
				}
			} else {
				sqlBuilder.UPDATE(objectMap.get(key));
			}
		}
		return sqlBuilder.SQL();
	}

	
	/**
	 * 生成单表delete语句，带where条件
	 * @Methods Name getUpdateSql
	 * @Create In 2014-01-17 By likang
	 * @param object
	 * @param queryMap
	 * @return String
	 */
	public static String getDeleteSql(Class clazz, Map queryMap) {
		SqlBuilder sqlBuilder = new SqlBuilder();
		Iterator<String> iterator = null;
		//Delete条件
		Map<String, String> clazzMap = clazzParseToMap(clazz);
		if (queryMap != null) {
			iterator = queryMap.keySet().iterator();
			//遍历queryMap 取查询条件
			while (iterator.hasNext()) {
				String key = iterator.next();
				//约定默认所有条件用等于 如果用其他限制条件 需要将key写为
				//key_条件 这样key  例如 name_like,name_<>,name_not null,name_in
				if (key.contains("_")) {
					String column = key.split("_")[0];
					String condition = key.split("_")[1];
					//容错处理 防止querymap中的参数 不是数据库字段
					if (clazzMap.containsKey(column)) {
						if (queryMap.get(key) instanceof String || queryMap.get(key) instanceof Date) {
							if (condition.equals("like")) {
								sqlBuilder.WHERE(column+" "+condition+" "+"'%"+getNormalString(queryMap.get(key))+"%'");
							} else if (condition.equals("in")) {
								sqlBuilder.WHERE(column+" "+condition+" "+getNormalString(queryMap.get(key)));
							} else {
								sqlBuilder.WHERE(column+" "+condition+" "+"'"+getNormalString(queryMap.get(key))+"'");
							}
						} else {
							sqlBuilder.WHERE(column+" "+condition+" "+getNormalString(queryMap.get(key)));
						}
					}
				} else {
					if (clazzMap.containsKey(key)) {
						if (queryMap.get(key) instanceof String || queryMap.get(key) instanceof Date) {
							sqlBuilder.WHERE(key + "='" + getNormalString(queryMap.get(key))+"'");
						} else {
							sqlBuilder.WHERE(key + "=" + getNormalString(queryMap.get(key)));
						}
					}
				}
			}
		}
		//DELETE表名
		iterator = clazzMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.equals("table_name")) {
				sqlBuilder.DELETE_FROM(clazzMap.get(key));
			} 
		}
		return sqlBuilder.SQL();
	}

	
	private static Object getNormalString(Object source) {
		if (source instanceof String || source instanceof Date) {
			return ((String)(source)).replace("\'", "\\'");
		} else {
			return source;
		}
		
		
	}

}
