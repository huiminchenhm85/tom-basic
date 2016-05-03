package com.tom.basic.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public abstract class SortAbleQuery extends BaseQuery{
	
	/**
	 * 返回从VO字段到DO字段的映射关�?
	 * 
	 * @return
	 */
	public abstract Map<String,String> getSortMate();
	
	public void setSort(String sort){
		if(StringUtils.isNotBlank(sort) && getSortMate() != null){
			String sortStr = sort.replaceAll("\\[|\\]|\\{|\\}|\"", "");
			String orderBy = sortStr.split(",")[0].split(":")[1];
			String sortBy = sortStr.split(",")[1].split(":")[1];
			super.setOrderBy(getSortMate().get(orderBy));
			super.setSort(sortBy);
		}
	}
	
	public void setCustomSort(String sort){
		if(StringUtils.isNotBlank(sort)){
			String sortStr = sort.replaceAll("\\[|\\]|\\{|\\}|\"", "");
			String orderBy = sortStr.split(",")[0].split(":")[1];
			String sortBy = sortStr.split(",")[1].split(":")[1];
			super.setOrderBy(orderBy);
			super.setSort(sortBy);
		}
	}
	
	public boolean canCustomSort(){
		return StringUtils.isNotBlank(this.getOrderBy()) && StringUtils.isNotBlank(this.getSort());
	}
}
