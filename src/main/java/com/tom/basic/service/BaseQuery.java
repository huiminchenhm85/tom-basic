package com.tom.basic.service;

import java.util.Map;

import com.google.common.collect.Maps;

public class BaseQuery {

	private int pageSize = 0;

	private int pageIndex = 1; // 1�?��

	private long totalCount = 0;
	
	private Map<String,Object> commonParams = Maps.newHashMap();
	
	/**
	 * add by Tito 2015-09-08
	 * 添加�?��参数，代表参加排序的字段�?
	 * @author Tito 
	 */
	private String orderBy;
	
	/**
	 * add by Tito 2015-09-08
	 * 添加�?��参数，代表参数排序是正序还是倒序 
	 * @author Tito
	 */
	private String sort;

	//add by Tito 2015-09-08
	public String getOrderBy() {
		return orderBy;
	}
	
	//add by Tito 2015-09-08
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	//add by Tito 2015-09-08
	public String getSort() {
		return sort;
	}

	//add by Tito 2015-09-08
	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (pageSize <= 0) {
			this.pageSize = 20;
		} else {
			this.pageSize = pageSize;
		}
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex == 0) {
			this.pageIndex = 1;
		} else {
			this.pageIndex = pageIndex;
		}
	}

	public int getStartPos() {
		if (pageIndex - 1 < 0) {
			return 0;
		} else {
			return (pageIndex - 1) * pageSize;
		}
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public Map<String, Object> getCommonParams() {
		return commonParams;
	}

	public void setCommonParams(Map<String, Object> commonParams) {
		this.commonParams = commonParams;
	}
	
	public void addCommonParams(String key,Object Value) {
		commonParams.put(key, Value);
	}
	
	

}
