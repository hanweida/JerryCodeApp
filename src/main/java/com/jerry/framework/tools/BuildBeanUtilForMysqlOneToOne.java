package com.jerry.framework.tools;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * 实体工具类，提供生成实体的sqlMap和自动建表的功能
 * 如sqlMap功能不满足需要，则自己按情况添加
 * 建表的列长度，如不能满足需求，请自己在数据库中修改
 * @Class Name BuildBeanUtilForOracle
 * @Author likang
 * @Create In Aug 1, 2010
 */
public class BuildBeanUtilForMysqlOneToOne {
	
	//配置文件的存放路径可以修改 
	//不需要文件名
	private static String DBDRIVER = null;
	private static String DBURL = null;
	private static String DBUSER = null;
	private static String DBPASSWORD = null;
	private static String tableNamePreFix = null;
	private static List waiJianTypeAliasAndResultMapList = new ArrayList();
	private static List waiJianSelectStringList = new ArrayList();
	
	public static void main(String[] args) throws Exception {
		//生成XML文件的方法 生成的文件会保存到targetFile目录下


//        buildSqlXmlForMysql(SysUserMessage.class,targetFile);

//		buildSqlXmlForMysql(SysMenu.class,targetFile);
//		buildSqlXmlForMysql(SysFun.class,targetFile);
//		buildSqlXmlForMysql(SysRole.class,targetFile);
//		buildSqlXmlForMysql(SysRoleFun.class,targetFile);
//		buildSqlXmlForMysql(SysRoleMenu.class,targetFile);
//		buildSqlXmlForMysql(SysUser.class,targetFile);
//		buildSqlXmlForMysql(SysUserRole.class,targetFile);


//		生成实体对应的表 和 Sequenece 表名为实体名的大写
//		excuteCreatTableForMysql(ClientInfo.class);
//		如果实体对应的表名不一样可以用该方法 一般情况下不使用该方法
//		excuteCreatTableByTableName(PhoneAuthorization.class,PhoneAuthorization.class.getSimpleName());
	}
	
	/**
	 * 可以指定本实体对应数据库连接的配置
	 */
	static{
		Properties propsDB;
		Properties props;

		try {
//			propsDB = Resources.getResourceAsProperties("dbResources.properties");
//			props =Resources.getResourceAsProperties("applicationResources.properties");
//			DBDRIVER = (String) propsDB.get("jdbc.driverClassName");
//			DBURL = (String) propsDB.get("jdbc.url");
//			DBUSER = (String) propsDB.get("jdbc.username");
//			DBPASSWORD = (String) propsDB.get("jdbc.password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 2015-01-22修改为 mybatis版本
	 * 根据类名生成 
	 * 实体名.sqlMap.xml
	 * 包含 BaseService中需要的所有数据库访问
	 * 列表如下：
	 * insert
	 * update
	 * delete
	 * deleteByIds
	 * deleteByLogic
	 * deleteObjectsByLogic
	 * selectAll
	 * selectById
	 * selectTotal
	 * selectPage
	 * selectTotalByParam
	 * selectPageByParam
	 * selectObjectsByParamNoPage
	 * @Methods Name xmlReadForOracle
	 * @Create In Aug 2, 2010 By likang
	 * @param clazz void
	 */
	public static void buildSqlXmlForMysql(Class clazz,String targetFilePath) {
		  //全类名
		  String className = clazz.getName();
		  //命名空间 再建一个变量 便于阅读
		  String nameSpace = clazz.getName();
		  //对象名
		  String objectName = className.substring(className.lastIndexOf(".")+1,className.length());
	  	  //成员变量 对应 列名 map 含有 表名 固定key：table_name
  	  	  Map<String, String> memberToColumMap = SqlCreator.clazzParseToMap(clazz);
		  //表名
		  String tableName = new String(memberToColumMap.get("table_name"));
		  memberToColumMap.remove("table_name");
		  //新建文件
		  Document document =  DocumentHelper.createDocument(); 
		  //加入DocType
		  document.addDocType("mapper", "-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
		  //该实体中是否有外键
		  boolean hasWaiJian = false;
	      //root节点
	      Element root =  (Element) document.addElement("mapper");
	      root.addAttribute("namespace",className);
	      /**
	       * mybatis 无此节点
	       * 
	      Element element = root.addElement("typeAlias");
	      element.addAttribute("alias",objectName);
	      element.addAttribute("type",className);
	       */
	      //typeAlias节点
	      
	      
	      //resultMap节点
	      Element element = root.addElement("resultMap");
	      element.addAttribute("id",objectName+"Map");
	      element.addAttribute("type",className);
	      String whereSqlColumn = "";
	      
	      String insertSqlColumn = "";
	      String insertSqlProperty = "";
	      String batchInsertSqlProperty = "";
	      //类的所有属性
	      Field[] fields = clazz.getDeclaredFields();
	      String insertItemString = "";
	      String updateSqlColumn = "";
	      for (Field field : fields) {
	    	    //该实体中有其他 BaseObject类型的外键
			    //boolean isFromBaseObejct = false;
	    	  	//成员变量名称
	    	  	String memberName = field.getName();
	    	  	//列名称
	    	  	String columName = memberToColumMap.get(memberName);
				if ("java.lang.Long".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("jdbcType", "BIGINT");
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= '' \"> and "+columName+" = #{"+memberName+"}</if>";
				} else if ("java.lang.String".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("jdbcType", "VARCHAR");
					//whereSqlColumn += "<if test=\"" + memberName+ "!= null\"> and "+columName+" like '%${" + memberName + "}%'</if>";
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= ''\"> and "+columName+" = #{" + memberName + "}</if>";
				} else if ("java.util.Date".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= ''\"> and DATE_FORMAT("+columName+",'%Y-%m-%d') = #{"+memberName+"}</if>";
				} else if ("java.lang.Integer".equals(field.getType().getName())) {
					Element ele = null;
					//主键用id属性
					if (memberName.equals("id")) {
						ele = element.addElement("id");
					} else {
						ele = element.addElement("result");
					}
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("jdbcType", "INTEGER");
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= '' \"> and "+columName+" = #{"+memberName+"}</if>";
				} else if ("java.lang.Float".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("jdbcType", "FLOAT");
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= '' \"> and "+columName+" = #{"+memberName+"}</if>";
				} else if ("java.lang.Double".equals(field.getType().getName())) {
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("jdbcType", "DOUBLE");
					whereSqlColumn += "<if test=\"" + memberName+ "!= null and " + memberName+ "!= '' \"> and "+columName+" = #{"+memberName+"}</if>";
				} else {
					break;
				}
					
			    if (!"java.util.List".equals(field.getType().getName()) && !"id".equals(memberName) ) {
					  insertSqlColumn += ","+columName;
			    	  insertSqlProperty += ",#{"+memberName+"}";
			    	  batchInsertSqlProperty += ",#{item."+memberName+"}"; 
				}
	    	 	//如果不是id 可以加入到update序列
	    	  	if(!"id".equals(memberName) && !"java.util.List".equals(field.getType().getName())){
		    		 updateSqlColumn += "<if test=\"" + memberName+ "!= null\">"+columName+" = #{"+memberName+"},</if>";;
		    	}
	    	  	
		    	  	
		    	/**
		    	 * 暂不支持外键生成 	
				//如果是BaseObject类型 用于一对一
				String superClassName = "";
				if (field.getType().getGenericSuperclass() != null ) {
					superClassName = field.getType().getGenericSuperclass().toString();
					if (!superClassName.equals("")) {
						isFromBaseObejct = isFromBaseObejct(superClassName);
					}
				}
				if (isFromBaseObejct) {
					//有外键
					hasWaiJian = true;
					//get大写第一个字母+小写其他（属性名）+ By 大写第一个字母+小写其他（表名） + Id
					String classNameString = field.getType().getName().substring(field.getType().getName().lastIndexOf(".")+1);
					String selectNameString = "get"  + classNameString + "By" + classNameString + "Id";
					Element ele = element.addElement("result");
					ele.addAttribute("property", memberName).addAttribute("column", columName);
					ele.addAttribute("select", nameSpace + "." + selectNameString);
					saveAllTypeAliasResultMapSelectProperties(field,classNameString,selectNameString,nameSpace);
//					whereSqlColumn += "<isNotNull prepend=\" AND \" property=\"" + field.getName()+ "\">("+field.getName().toUpperCase()+"=#"+field.getName()+"#)</isNotNull>";
					//如果是BaseObject类型的 insert 为 属性.id
					insertItemString += ".id";
				}
				 if ("java.util.List".equals(field.getType().getName())) {
						String selectNameString = field.getName();
						selectNameString = "get" + selectNameString.substring(0,1).toUpperCase() + selectNameString.substring(1, selectNameString.length());
						
//						String selectNameString = "get"  + field.getName().substring(0,field.getName().length())+field.getName().substring(1,field.getName().length());
						Element ele = element.addElement("result");
						ele.addAttribute("property", field.getName());
						//根据this的ID 对应的多个 引用  无法预知主键名 新表全部规定主键为id
						ele.addAttribute("column", "ID");
						ele.addAttribute("select", nameSpace + "." + selectNameString);
						//拼写该List对应BaseObject的 selectSql 和 泛型中 BaseObject 的ResultMap
						//该field应该是泛型的 Object类型 带包名的全类名
						String listFanXingTypeString = getListFanXingType(field);
						//不带包名的类名
						String classNameString = listFanXingTypeString.substring(listFanXingTypeString.lastIndexOf(".")+1);
						listTypeSaveAllTypeAliasResultMapSelectProperties(listFanXingTypeString,classNameString,selectNameString,nameSpace,className);
//						field.get
//						String selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
				}
				
	    	  	for(String string : (List<String>)waiJianTypeAliasAndResultMapList) {
	  	    	  root.addText(string);
	  	      }
	      //**/
	      }
  	  	  String selectSqlColumn = "id" + insertSqlColumn;

	      //insert sql
	      Element insertElement = root.addElement("insert");
	      insertElement.addAttribute("id", "insert");
	      insertElement.addAttribute("parameterType", className);
	      insertElement.addAttribute("useGeneratedKeys", "true");
	      insertElement.addAttribute("keyProperty", "id");
	      
	      //批量insert sql
	      Element batchInsertElement = root.addElement("insert");
	      batchInsertElement.addAttribute("id", "batchInsert");
	      batchInsertElement.addAttribute("parameterType", "java.util.List");
	      /**
	      <insert id="batchInsert" parameterType="java.util.List">
			insert into t_sys_menu(name,des,url,pid,level_id,createtime,lasttime,last_user_id,order_num)
			values
			<foreach collection="list" item="item" index="index" separator="," >
			(#{item.name},#{item.des},#{item.url},#{item.pid},#{item.levelId},#{item.createtime},#{item.lasttime},#{item.lastUserId},#{item.orderNum})
			</foreach>
		</insert>
	      **/
	      
	      
	      //插入sql
	      String insertSql = "insert into " + tableName + "("+insertSqlColumn.substring(1)+") values ("+insertSqlProperty.substring(1)+")";
	      insertElement.addText(insertSql);
	      //批量插入sql
	      String batchInsertSql = "insert into " + tableName + "("+insertSqlColumn.substring(1)+") values ";
	      String batchInsertSqlText = batchInsertSql + "<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >"
	    		   + "("+batchInsertSqlProperty.substring(1)+")" + "</foreach>";
	      batchInsertElement.addText(batchInsertSqlText);
	      
	      
	      //动态修改 update sql
	      Element updateElement = root.addElement("update");
	      updateElement.addAttribute("id", "update");
	      String updateWhereId = " <where>id=#{id} limit 1</where>";
	      updateSqlColumn = "<set>" + updateSqlColumn + " </set>";
	      String updateSql = "update "+ tableName+ " " + updateSqlColumn + " " + updateWhereId;
	      updateElement.setText(updateSql);
	      //物理删除 delete sql
	      Element deleteElement = root.addElement("delete");
	      deleteElement.addAttribute("id", "delete");
	      deleteElement.addAttribute("parameterType", "java.lang.Integer");
	      deleteElement.setText("delete from "+tableName+"  <where>id=#{id}</where>");
	      //物理删除多个 deleteByIds sql
	      Element deleteByIdsElement = root.addElement("delete");
	      deleteByIdsElement.addAttribute("id", "deleteByIds");
	      deleteByIdsElement.addAttribute("parameterType", "java.lang.String");
	      deleteByIdsElement.setText("delete from "+tableName+" <where>id in <foreach collection=\"array\" item=\"id\"  open=\"(\" separator=\",\" close=\")\">#{id}</foreach></where>");
	      //逻辑删除 deleteFlag sql
	      Element deleteByLogic = root.addElement("update");
	      deleteByLogic.addAttribute("id", "deleteByLogic");
	      deleteByLogic.setText("update "+ tableName +" <set> deleteflag=1 </set> <where>id=#{id}</where>");
	      //批量逻辑删除持久化对象 deleteObjectsByLogic sql
	      Element deleteObjectsByLogic = root.addElement("update");
	      deleteObjectsByLogic.addAttribute("id", "deleteObjectsByLogic");
	      deleteObjectsByLogic.addAttribute("parameterType", "java.lang.String");
	      deleteObjectsByLogic.setText("update "+ tableName +" <set> deleteflag=1 </set>  <where>id in <foreach collection=\"array\" item=\"id\"  open=\"(\" separator=\",\" close=\")\">#{id}</foreach></where>");
	      //无条件查询所有 selectAll sql
	      Element selectAllElement = root.addElement("select");
	      selectAllElement.addAttribute("id", "selectAll");
	      selectAllElement.addAttribute("parameterType", "java.util.Map");
	      selectAllElement.addAttribute("resultMap", objectName+"Map");
	      selectAllElement.setText("select "+selectSqlColumn+" from "+tableName);
	      //根据id查询单个实体 selectById sql
	      Element selectByIdElement = root.addElement("select");
	      selectByIdElement.addAttribute("id", "selectById");
	      selectByIdElement.addAttribute("parameterType", "java.lang.Integer");
	      selectByIdElement.addAttribute("resultMap", objectName+"Map");

//	      //如果有外键则 用resultMap
//	      if (hasWaiJian) {
//		      selectByIdElement.addAttribute("resultMap", objectName+"Map");
//	      } else {
//	    	  selectByIdElement.addAttribute("resultClass", objectName);
//		  }
	      selectByIdElement.setText("select " + selectSqlColumn+ " from "+tableName+" where id=#{id}");
	      
	      String dynamicOrderBy = "<if test=\"orderBy != null and orderBy!='' \">" +
    			      " order by ${orderBy}" +
    			      "</if>" + 
    			      "<if test=\"asc != null\">" +
    			      " ${asc}" +
    			      "</if>";
	      //无条件查询总数 selectTotal sql
	      Element selectTotalElement = root.addElement("select");
	      selectTotalElement.addAttribute("id", "selectTotal");
	      selectTotalElement.addAttribute("resultType", "java.lang.Integer");
	      selectTotalElement.setText("select count(1) from "+tableName);
	      //无条件分页查询 selectPage sql
	      Element selectPageElement = root.addElement("select");
	      selectPageElement.addAttribute("id", "selectPage");
	      selectPageElement.addAttribute("parameterType", "java.util.Map");
	      selectPageElement.addAttribute("resultMap", objectName+"Map");
	      //两层分页查询 提高效率 适用于不分页 String dynamicSqlNoPage = "SELECT "+selectSqlColumn+" from ( SELECT "+selectSqlColumn+", ROWNUM rownum_ from "+tableName+" WHERE ROWNUM &lt;= #end#  " + dynamicOrderBy + " ) WHERE rownum_ &gt;#start# ";
	      //三层分页查询 解决 order by 的问题
	      String dynamicSqlNoPage = "select "+selectSqlColumn+" from  "+tableName+" limit #{start},#{end}";
	      selectPageElement.setText(dynamicSqlNoPage);

	      //动态有条件查询总数 selectTotalByParam sql
	      String dynamicWhere = "<where>" + whereSqlColumn + "</where>";

	      Element selectTotalByParamElement = root.addElement("select");
	      selectTotalByParamElement.addAttribute("id", "selectTotalByParam");
	      selectTotalByParamElement.addAttribute("parameterType", "java.util.Map");
	      selectTotalByParamElement.addAttribute("resultType", "java.lang.Integer");
	      selectTotalByParamElement.setText("select count(1) from "+tableName + " " + dynamicWhere);
	      
	      //动态有条件分页查询 selectPageByParam sql
	      Element selectPageByParamElement = root.addElement("select");
	      selectPageByParamElement.addAttribute("id", "selectPageByParam");
	      selectPageByParamElement.addAttribute("parameterType", "java.util.Map");
	      selectPageByParamElement.addAttribute("resultMap", objectName+"Map");
	      //两层分页查询 提高效率 适用于不分页String dynamicSql = "SELECT "+selectSqlColumn+" from ( SELECT "+selectSqlColumn+", ROWNUM rownum_ FROM "+tableName+" WHERE ROWNUM &lt;= #end# "+whereSqlColumn+ " " + dynamicOrderBy + " ) WHERE rownum_ &gt;#start# ";
	      //三层分页查询 解决 order by 的问题
	      String dynamicSql = "select "+selectSqlColumn+" from "+tableName+ " "+dynamicWhere+ " " + dynamicOrderBy + " limit #{start},#{end}";

	      selectPageByParamElement.setText(dynamicSql);
	      //按条件不分页查询所有 selectObjectsByParamNoPage  list sql
	      Element selectObjectsByParamNoPage = root.addElement("select");
	      selectObjectsByParamNoPage.addAttribute("id", "selectObjectsByParamNoPage");
	      selectObjectsByParamNoPage.addAttribute("parameterType", "java.util.Map");
	      selectObjectsByParamNoPage.addAttribute("resultMap", objectName+"Map");
	      String dynamicSqlAll = "select "+selectSqlColumn+" from " + tableName + " " + dynamicWhere + " " + dynamicOrderBy ;
	      selectObjectsByParamNoPage.setText(dynamicSqlAll);
	      //按条件不分页查询一条 selectObjectByParam sql
	      Element selectObjectByParam = root.addElement("select");
	      selectObjectByParam.addAttribute("id", "selectObjectByParam");
	      selectObjectByParam.addAttribute("parameterType", "java.util.Map");
	      selectObjectByParam.addAttribute("resultMap", objectName+"Map");
	      String selectObjectByParamSql = "select "+selectSqlColumn+" from " + tableName + " " + dynamicWhere;
	      selectObjectByParam.setText(selectObjectByParamSql);

	      
	      /**写外键的select语句
	      for(String string : (List<String>)waiJianSelectStringList) {
	    	  root.addText(string);
	      }*/
	      //生成到项目下该类的map包下
	      String filePathString = targetFilePath+"\\"+objectName+".sqlMap.xml";
	      //生成XML文件
	      doc2XmlFile(document, filePathString);
	   }

	
	/**
	 * 
	 * @Methods Name listTypeSaveAllTypeAliasResultMapSelectProperties
	 * @Create In Aug 5, 2010 By likang
	 * @param listFanXingTypeString list泛型类型的名字 全包名
	 * @param classNameString       只是类名
	 * @param selectNameString		定义好的select Id名称
	 * @param nameSpace				命名空间
	 * @param thisClassName			该一对多 一方的全类名（带包名）用于在 多方的实体中反射该类型 然后取出该类型的Coulum 拼出sql
	 * @return String
	 */
	private static String listTypeSaveAllTypeAliasResultMapSelectProperties(String listFanXingTypeString,String classNameString,String selectNameString,String nameSpace,String thisClassName) {
		Class clazz = null;
		Object object = null;
		//一对多 多方中对应的 一方的引用的属性名称 的 大写（对应表的列名）
		String keyNameString = "";
		try {
			 clazz = Class.forName(listFanXingTypeString);
			 object = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//先拼出typeAlias
		String typeString = listFanXingTypeString;
		String typeAlias = " <typeAlias alias=\"" + classNameString + "\" type=\"" + typeString + "\" />";
		//同时拼出该引用实体的resultMap
		String resultMapNameString = classNameString + "Map";
		String selectPro = "";
//		 <result property="userinfoid" column="userinfoid" />  
		String resultMap = "<resultMap id=\""+resultMapNameString+"\" class=\""+classNameString+"\">  ";
		Field [] fields = clazz.getDeclaredFields();
		for (Field fieldTemp : fields) {
			//如果不是BaseObject类型
			String superClassName = fieldTemp.getType().getGenericSuperclass().toString();
            selectPro += ","+fieldTemp.getName().toUpperCase();
            resultMap += "<result property=\""+fieldTemp.getName()+"\" column=\""+fieldTemp.getName().toUpperCase()+"\" jdbcType=\""+fieldTemp.getType().getName()+"\"/>";
	    }
		resultMap += "</resultMap> ";
		//外键的<typeAlias>和<resultMap>
	   String back = typeAlias + resultMap;
	   waiJianTypeAliasAndResultMapList.add(back);
       String selectString = "SELECT " + selectPro.substring(1) + " FROM " + getTableName(classNameString) + " WHERE "+keyNameString+"=#value#";
       selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
       waiJianSelectStringList.add(selectString);
       return selectPro.substring(1);
	}
		
		/**
		 * 通过类名解析出tablename
		 * @Methods Name getTableName
		 * @Create In 2015-1-21 By likang
		 * @param classNameString
		 * @return String
		 */
		private static String getTableName(String classNameString) {
		// TODO Auto-generated method stub
		return null;
	}


		/**
		 * 遍历获取所有属性的值 拼接成 属性,属性,、、、
		 * @Methods Name saveAllTypeAliasResultMapSelectProperties
		 * @Create In Aug 3, 2010 By Administrator
		 * @param
		 * @return String
		 */
		private static String saveAllTypeAliasResultMapSelectProperties( Field field,String classNameString,String selectNameString,String nameSpace) {
			//先拼出typeAlias
			String typeString = field.getType().getName();
			String typeAlias = " <typeAlias alias=\"" + classNameString + "\" type=\"" + typeString + "\" />";
			//同时拼出该引用实体的resultMap
			String resultMapNameString = classNameString + "Map";
			String selectPro = "";
//			 <result property="userinfoid" column="userinfoid" />  
			String resultMap = "<resultMap id=\""+resultMapNameString+"\" class=\""+classNameString+"\">  ";
			for (Field fieldTemp : field.getType().getDeclaredFields()) {
				selectPro += ","+fieldTemp.getName().toUpperCase();
				//如果不是BaseObject类型
				String superClassName = "";
				if (fieldTemp.getType().getGenericSuperclass() != null) {
					superClassName = fieldTemp.getType().getGenericSuperclass().toString();
				}
                resultMap += "<result property=\""+fieldTemp.getName()+"\" column=\""+fieldTemp.getName().toUpperCase()+"\" jdbcType=\""+fieldTemp.getType().getName()+"\"/>";
            }
			resultMap += "</resultMap> ";
			//外键的<typeAlias>和<resultMap>
		   String back = typeAlias + resultMap;
		   waiJianTypeAliasAndResultMapList.add(back);
	       String selectString = "SELECT " + selectPro.substring(1) + " FROM " + getTableName(classNameString) + " WHERE ID=#"+field.getName().toUpperCase()+"#";
	       selectString =  "<select id=\""+nameSpace+"."+selectNameString+"\" parameterClass=\"Long\" resultMap=\""+resultMapNameString+"\">" + selectString + "</select>"; 
	       waiJianSelectStringList.add(selectString);
	       return selectPro.substring(1);
		}
	

		/**
		 * 根据文件名加载document对象
		 * @Methods Name load
		 * @Create In Jul 22, 2010 By likang
		 * @param filename
		 * @return Document
		 */
		private static Document load(String filename){ 
	      Document document = null; 
	      try  
	      {  
	          SAXReader saxReader = new SAXReader(); 
	          document = saxReader.read(new File(filename)); 
	      } 
	      catch (Exception ex){ 
	          ex.printStackTrace(); 
	      }   
	      return document; 
	   }
		
		/**
		 * 把document中的内容写入文件中 
		 * @Methods Name doc2XmlFile
		 * @Create In Jul 22, 2010 By likang
		 * @param document
		 * @param filename void
		 */
		private static void doc2XmlFile(Document document,String filename){ 
	      try{ 
	            OutputFormat format = OutputFormat.createPrettyPrint(); 
	            format.setEncoding("UTF-8");//默认编码为UTF-8 
	            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format); 
	            writer.setEscapeText(false);//防止自动转码
	            writer.write(document); 
	            writer.close();             
	       }catch(Exception ex){ 
	            ex.printStackTrace(); 
	       } 
	   }
		
		
		/**
		 * 生成Oracle下自动建表和sequence语句
		 * @Methods Name getCreateSqlForMysql
		 * @Create In Aug 2, 2010 By likang
		 * @param clazz
		 * @param tableName
		 * @return
		 * @throws Exception String
		 */
		
		/**
		 * 拿到数据库连接
		 * @Methods Name getConn
		 * @Create In Jul 22, 2010 By likang
		 * @return Connection
		 * @throws java.io.IOException
		 */
		private static Connection getConn() throws IOException {
			Connection conn = null;
			try {
				Class.forName(DBDRIVER);
				conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
			} catch (Exception e) {
				return null;
			}
			return conn;
		}
		
		
		/**
		 * 关闭数据库连接
		 * @Methods Name colse
		 * @Create In Aug 2, 2010 By likang
		 * @param conn
		 * @param statement void
		 */
		private static void close(Connection conn, Statement statement) {
			if (statement != null) {
				try {
					statement.close();
					statement = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
					conn = null;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		/**
		 * 取出list类型的<>泛型类型名称
		 * @Methods Name getListFanXingType
		 * @Create In Aug 5, 2010 By Administrator
		 * @param field
		 * @return String
		 */
		public static String getListFanXingType(Field field) {
			String wholeTypeString = field.getGenericType().toString();
			wholeTypeString = wholeTypeString.substring(wholeTypeString.indexOf("<")+1, wholeTypeString.length()-1);
			return wholeTypeString;
		}

}
