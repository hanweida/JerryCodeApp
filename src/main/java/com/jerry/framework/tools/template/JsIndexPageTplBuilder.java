package com.jerry.framework.tools.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Class: JspTplBuilder
 * User: likang
 * Date: 15-11-4
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class JsIndexPageTplBuilder extends TplBuilder{

    public JsIndexPageTplBuilder(TplConfig tbc, List<TableColum> tableColumList) {
        super(tbc, tableColumList);
    }

    @Override
    public String getTemplateName() {
        return "tpl_index.js";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
