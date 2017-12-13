package com.jerry.framework.tools.template;

import com.jerry.framework.util.StringUtil;

/**
 * 模板生成代码配置
 * Created with IntelliJ IDEA.
 * Class: TplBuilderConfig
 * User: likang
 * Date: 15-11-4
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
public class TplConfig {

    //引用js path的前缀
    public static final String GLOBAL_JS_PATH_PREFIX_VALUE = "/static/web/js/page/";

    public String getGLOBAL_JS_PATH_PREFIX_VALUE() {
        return GLOBAL_JS_PATH_PREFIX_VALUE;
    }


    /**
     * 用于模板生成代码替换 jsp、js文件名前缀,
     * 例如 t_login_log 表 一般映射为 login_log 作为jspFilePrefix
     * js文件会自动使用LoginLog作为js中类名的前缀
     */
    private String jspFilePrefix;

    /**
     * 用于模板生成代码替换 controller文件名前缀和内部使用的pojo class名称
     * 例如 t_login_log 表 如果是系统功能一般映射为 SysLoginLog
     */
    private String pojoClassName;

    /**
     * 用于模板生成代码替换 controller对应的url,
     * 例如 t_login_log 表 一般映射为 sys/loginLog 作为controllerUrl
     *
     * 用于模板生成代码替换 jsp中引用js的路径
     * 例如 /static/web/js/page/{将替换的内容}/login_log_index.js
     * 例如 t_login_log 表 设置为 sys/loginLog
     */
    private String controllerAndJspUrl;

    /**
     * 用于模板生成代码替换 jsp、js文件中用于描述模块的中文名称,
     * 例如 t_login_log 表 设置为 登陆日志
     */
    private String jspFileModelNameCn;

    public String getJspFileModelNameCn() {
        return jspFileModelNameCn;
    }

    public void setJspFileModelNameCn(String jspFileModelNameCn) {
        this.jspFileModelNameCn = jspFileModelNameCn;
    }

    public String getJspFilePrefix() {

        return jspFilePrefix;
    }

    public void setJspFilePrefix(String jspFilePrefix) {
        this.jspFilePrefix = jspFilePrefix;
    }

    /**
     * js中class名称前缀
     * login_log
     * 若jspFilePrefix中包含下划线转换为驼峰命名作为js中class的名称前缀
     */
    public String getJsClassPrefix() {
        return getJsClassPrefix(jspFilePrefix);
    }

    /**
     * js中class对应变量名称前缀
     * js中class名前缀转换为变量第一个字母小写
     */
    public String getJsValPrefix() {
        return StringUtil.toLowerCaseFirstOne(getJsClassPrefix());
    }

    /**
     * java中class对应变量名称前缀
     * java中class名前缀转换为变量第一个字母小写
     */
    public String getPojoClassValPrefix() {
        return StringUtil.toLowerCaseFirstOne(getPojoClassName());
    }

    private static String getJsClassPrefix(String columnName) {
        // TODO Auto-generated method stub
        StringBuffer sBuffer = new StringBuffer();
        String[] arrStrings = columnName.split("_");
        for (int i = 0; i < arrStrings.length; i++) {
            sBuffer.append(StringUtil.toUpperCaseFirstOne(arrStrings[i]));
        }
        return sBuffer.toString();
    }

    public String getPojoClassName() {
        return pojoClassName;
    }

    public void setPojoClassName(String pojoClassName) {
        this.pojoClassName = pojoClassName;
    }

    public String getControllerAndJspUrl() {
        return controllerAndJspUrl;
    }

    public void setControllerAndJspUrl(String controllerAndJspUrl) {
        this.controllerAndJspUrl = controllerAndJspUrl;
    }


}
