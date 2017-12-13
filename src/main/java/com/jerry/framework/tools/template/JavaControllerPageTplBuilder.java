package com.jerry.framework.tools.template;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Class: JavaControllerPageTplBuilder
 * User: likang
 * Date: 15-11-12
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class JavaControllerPageTplBuilder extends TplBuilder {

    public JavaControllerPageTplBuilder(TplConfig tbc, List<TableColum> tableColumList) {
        super(tbc, tableColumList);
    }

    @Override
    public String getTemplateName() {
        return "tplController.tpl";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
