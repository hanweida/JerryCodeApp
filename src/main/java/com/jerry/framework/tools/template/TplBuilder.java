package com.jerry.framework.tools.template;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 其他js jsp controller等继承
 * 定义好要替换的变量和模板名称
 * 共用build方法生成
 * Created with IntelliJ IDEA.
 * Class: TplBuilder
 * User: likang
 * Date: 15-11-9
 * Time: 下午6:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class TplBuilder {

    /**
     * 定义模版名
     * @return
     */
    public abstract String getTemplateName();

    private List<TableColum> tableColumList;

    private TplConfig tbc;

    public TplBuilder(TplConfig tbc,List<TableColum> tableColumList) {
        this.tableColumList = tableColumList;
        this.tbc = tbc;
    }

    /**
     * 定义模板中要替的换变量
     * 如有需要可重写覆盖自定义实现
     * @return
     */
    public Map<String, Object> init() {
        Map<String,Object> dataMap = new HashMap<String,Object>();
        dataMap.put("tableColumList",tableColumList);
        dataMap.put("config",tbc);
        return dataMap;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String build() throws Exception {
        Map<String,Object> dataMap = init();
        Template template = null;
        try {
            template = FreemarkerConfigHolder.CONFIG.getTemplate(getTemplateName(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        template.process(dataMap ,new OutputStreamWriter(byteArrayOutputStream, Charset.forName("UTF-8")));
        return byteArrayOutputStream.toString( Charset.forName("UTF-8").name());
    }

    public static class FreemarkerConfigHolder {

        public final static Configuration CONFIG = new Configuration();

        static {
            CONFIG.setDefaultEncoding("UTF-8");
            CONFIG.setClassForTemplateLoading(FreemarkerConfigHolder.class, "/template");
        }
    }


}

