package com.jerry.framework.pojo.datatable;

import com.jerry.framework.dao.support.Page;

import java.util.List;

/**
 * datatable返回json对象
 * @Class Name DataTableResult
 * @Author likang
 * @Create In 2015-3-17
 */
public class DataTableResult {
	
	private Integer iTotalRecords;
	private Integer iTotalDisplayRecords;
	private Integer echo;
	private List<Object> data;
	
	public DataTableResult(Integer iTotalRecords, Integer iTotalDisplayRecords,
			Integer echo, List<Object> data) {
		super();
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalDisplayRecords;
		this.echo = echo;
		this.data = data;
	}
	public DataTableResult(Page page,Integer echo) {
		super();
		if (page != null) {
			this.iTotalRecords = page.getTotalRows();
			this.iTotalDisplayRecords = page.getTotalRows();
			this.data = page.getData();
		}
		this.echo = echo;
	}
	
	public Integer getEcho() {
		return echo;
	}
	public void setEcho(Integer echo) {
		this.echo = echo;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	public Integer getITotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Integer getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
}
