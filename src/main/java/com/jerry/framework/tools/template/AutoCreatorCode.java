package com.jerry.framework.tools.template;

import com.jerry.framework.tools.BuildBeanUtilForMysqlOneToOne;
import com.jerry.framework.tools.DBUtil;
import com.jerry.framework.tools.JavaFileLoader;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Class: AutoCreatorCode
 * User: likang
 * Date: 15-11-12
 * Time: 下午6:25
 * To change this template use File | Settings | File Templates.
 */
public class AutoCreatorCode {

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


    public static void createPojo2File(String tableName,String pojoClassName) {
        String PACKAGE = "com.easou.ecom.framework.pojo";
        //生成pojo
        try {
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static String createPojo(String tableName,String pojoClassName) throws Exception {
        String PACKAGE = "com.easou.ecom.framework.pojo";
        return DBUtil.table2pojo(tableName, pojoClassName, PACKAGE);
    }

    public static void createMyabatisXml(Class clazz) {
        //生成mybatis xml
        String targetFile = "E:\\workings\\myeclipse10.6\\MyBatis_Spring3_Maven\\mybatis\\src\\main\\java\\com\\easou\\ecom\\framework\\mapper";
        BuildBeanUtilForMysqlOneToOne.buildSqlXmlForMysql(clazz, targetFile);
    }

    public static void createPage(TplConfig tbc, String tableName) throws Exception {
        String POJOPATH = "E:\\workings\\myeclipse10.6\\MyBatis_Spring3_Maven\\mybatis\\tmp\\";
        List<TableColum> tableColumList = new TplTableInit(tableName).TABLE_COLUM_LIST;
        //jsp首页文件
        TplBuilder tb = new JspIndexPageTplBuilder(tbc,tableColumList);
        stringToFile(POJOPATH + tbc.getJspFilePrefix() + "_index.jsp",tb.build());
//        System.out.println(tb.build());
        //jsp详情页文件
        tb = new JspItemPageTplBuilder(tbc,tableColumList);
        stringToFile(POJOPATH + tbc.getJspFilePrefix() + "_item.jsp",tb.build());
//        System.out.println(tb.build());
        //js首页文件
        tb = new JsIndexPageTplBuilder(tbc,tableColumList);
        stringToFile(POJOPATH + tbc.getJspFilePrefix() + "_index.js",tb.build());
//        System.out.println(tb.build());
        //js详情页文件
        tb = new JsItemPageTplBuilder(tbc,tableColumList);
        stringToFile(POJOPATH + tbc.getJspFilePrefix() + "_item.js",tb.build());
//        System.out.println(tb.build());
        //controller页文件
        tb = new JavaControllerPageTplBuilder(tbc,tableColumList);
        stringToFile(POJOPATH + tbc.getPojoClassName() + "Controller.java",tb.build());
//        System.out.println(tb.build());
    }

    public static void main(String[] args) throws Exception {


        String tableName = "t_sys_dict";
        String pojoClassName = "SysDict";

//        createPojo2File(tableName,pojoClassName);

        String PACKAGE = "com.easou.ecom.framework.pojo";
        String fileSrc = DBUtil.table2pojo(tableName, pojoClassName, PACKAGE);
        Class clazz = JavaFileLoader.javaSrcToClass(PACKAGE + "." + pojoClassName,fileSrc);

        createMyabatisXml(clazz);


//        //生成页面
//        TplConfig tbc = new TplConfig();
//        tbc.setPojoClassName(pojoClassName);
//        tbc.setControllerAndJspUrl("sys/dict");
//        tbc.setJspFileModelNameCn("系统字典");
//        tbc.setJspFilePrefix("dict");
//        createPage(tbc,tableName);

    }
}
