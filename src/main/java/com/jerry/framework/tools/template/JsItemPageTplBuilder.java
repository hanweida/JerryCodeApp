package com.jerry.framework.tools.template;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Class: JsItemPageTplBuilder
 * User: likang
 * Date: 15-11-12
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */
public class JsItemPageTplBuilder extends TplBuilder{

    public JsItemPageTplBuilder(TplConfig tbc, List<TableColum> tableColumList) {
        super(tbc, tableColumList);
    }

    @Override
    public String getTemplateName() {
        return "tpl_item.js";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
