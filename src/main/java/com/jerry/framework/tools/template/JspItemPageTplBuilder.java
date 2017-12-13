package com.jerry.framework.tools.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Class: JspItemPageTplBuilder
 * User: likang
 * Date: 15-11-12
 * Time: 下午3:53
 * To change this template use File | Settings | File Templates.
 */
public class JspItemPageTplBuilder extends TplBuilder{

    public JspItemPageTplBuilder(TplConfig tbc, List<TableColum> tableColumList) {
        super(tbc, tableColumList);
    }

    @Override
    public String getTemplateName() {
        return "tpl_item.jsp";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
