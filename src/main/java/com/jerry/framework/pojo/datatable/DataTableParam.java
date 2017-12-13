package com.jerry.framework.pojo.datatable;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * datatable请求对象
 * @Class Name DataTableParam
 * @Author likang
 * @Create In 2015-3-24
 */
public class DataTableParam {
	
	private Integer pageNo;
	private Integer pageSize;
	private String orderBy;	//默认排序字段
	private Integer echo;	//控件查询次数，js控制自增
	private Boolean isAsc; //是否升序
	private HashMap<String, String> paramMap; //所有参数
	
	public DataTableParam(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String aoData = request.getParameter("aoData"); //查询参数json数组
		if (StringUtils.isNotBlank(aoData)) {
			aoData = aoData.replace("&quot;", "\""); 
			this.paramMap = new HashMap<String, String>();
			//配合jquery-datatable封装所有查询参数到map
			List<Map> list = JSON.parseArray(aoData, Map.class);
			for (Map map:list) {
				this.paramMap.put(String.valueOf(map.get("name")), String.valueOf(map.get("value")));
			}
			this.orderBy = this.paramMap.get("mDataProp_" + this.paramMap.get("iSortCol_0"));//被排序的列名
			String order = this.paramMap.get("sSortDir_0");	//排序的方向 "desc" 或者 "asc"
			if (StringUtils.isNotBlank(order)) {
				this.isAsc = order.equals("desc") ? false : true;
			}
            int echo = 0;
            if (paramMap.containsKey("sEcho")) {
                echo = Integer.parseInt(paramMap.get("sEcho")); //控件查询次数，js控制自增
            }
            int index = 0;
            if (paramMap.containsKey("iDisplayStart")) {
                index = Integer.parseInt(paramMap.get("iDisplayStart"));//起始索引
            }

            if(paramMap.containsKey("iDisplayLength")) {
                this.pageSize = Integer.parseInt(paramMap.get("iDisplayLength"));//分页量
            } else {
                this.pageSize = 10;
            }

			this.pageNo = index/this.pageSize + 1;//页码
		}
	}
	
	/**
	 * 验证参数
	 * @Methods Name vaildateRight
	 * @Create In 2015-3-24 By likang
	 * @return Boolean
	 */
	public Boolean vaildateRight() {
		if (this.pageSize > 0 && this.pageNo > 0 && !this.paramMap.isEmpty()) {
			return true;
		}
        return false;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}

	private void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	private void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderBy() {
		return orderBy;
	}

	private void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getEcho() {
		return echo;
	}

	private void setEcho(Integer echo) {
		this.echo = echo;
	}

	public Boolean getIsAsc() {
		return isAsc;
	}

	private void setIsAsc(Boolean isAsc) {
		this.isAsc = isAsc;
	}

	public HashMap<String, String> getParamMap() {
		return paramMap;
	}

	private void setParamMap(HashMap<String, String> paramMap) {
		this.paramMap = paramMap;
	}
}
