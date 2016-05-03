package com.tom.basic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tom.basic.serialize.json.GsonUtils;


public class ServiceQueryResult<T> {

	public ServiceQueryResult() {
	}

	public ServiceQueryResult(boolean success, String msg) {
		this.success = success;
		this.msg = msg;
	}
	
	public static <W> ServiceQueryResult<W> asFail(String msg){
		return new ServiceQueryResult<W>(false,msg);
	}
	
	public static <W> ServiceQueryResult<W> asSuccess(List<W> data){
		ServiceQueryResult<W> result = new ServiceQueryResult<W>();
		result.success = true;
		result.obj = data;
		return result;
	} 

	public List<T> obj = new ArrayList<T>();

	public long totalCount;

	public boolean success;

	public String resultCode;

	public String msg;

	public BaseQuery pageQuery;
	

	public String toJson(Class<?>... addtionClassDefine) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("\"totalCount\": " + totalCount + ",");
		if (StringUtils.isNotBlank(msg)) {
			result.append("\"msg\": \"" + msg + "\",");
		}
		if (StringUtils.isNotBlank(resultCode)) {
			result.append("\"resultCode\": " + resultCode + ",");
		}
		if (obj != null) {
			String objJson = GsonUtils.toJson(obj,addtionClassDefine);
			result.append("\"obj\": " + objJson + ",");
		}
		result.append("\"success\": " + success + "");

		result.append("}");
		return result.toString();
	}
	
	
	public String toJson(Map<String,String> appendKV,Class<?>... addtionClassDefine) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("\"totalCount\": " + totalCount + ",");
		if (StringUtils.isNotBlank(msg)) {
			result.append("\"msg\": " + msg + ",");
		}
		if (StringUtils.isNotBlank(resultCode)) {
			result.append("\"resultCode\": " + resultCode + ",");
		}
		if (obj != null) {
			String objJson = GsonUtils.toJson(obj,addtionClassDefine);
			result.append("\"obj\": " + objJson + ",");
		}
		if (appendKV != null) {
			for(String key : appendKV.keySet()){
				result.append("\""+key+"\": \"" + appendKV.get(key) + "\",");
			}
		}
		result.append("\"success\": " + success + "");

		result.append("}");
		return result.toString();
	}

	public List<PagerData> getPagerData() {
		List<PagerData> result = new ArrayList<PagerData>();
		for (int i = 1; i <= getTotalPage(); i++) {
			PagerData data = new PagerData();
			data.pageNum = i;
			if (i == pageQuery.getPageIndex()) {
				data.isCurrent = true;
			}
			result.add(data);
		}
		return result;
	}

	public BaseQuery getPageQuery() {
		return pageQuery;
	}

	public void setPageQuery(BaseQuery pageQuery) {
		this.pageQuery = pageQuery;
	}

	public List<T> getObj() {
		return obj;
	}

	public void setObj(List<T> obj) {
		this.obj = obj;
	}

	public int getTotalPage() {
		if (this.totalCount == 0) {
			return 0;
		}
		if (this.totalCount % pageQuery.getPageSize() == 0) {
			return (int) (this.totalCount / pageQuery.getPageSize());
		} else {
			return (int) (this.totalCount / pageQuery.getPageSize()) + 1;
		}

	}
}
