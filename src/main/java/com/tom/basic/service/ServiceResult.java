package com.tom.basic.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.tom.basic.serialize.json.GsonUtils;


public class ServiceResult<T> {

	public ServiceResult() {
	}

	private ServiceResult(boolean success, String msg, T obj) {
		this.success = success;
		this.msg = msg;
		this.obj = obj;
	}
	
	public boolean success;

	public String resultCode;

	public String msg;

	public T obj;
	
	public static <W> ServiceResult<W> asSuccess(W obj,String msg){
		return new ServiceResult<W>(true,msg,obj);
	}
	
	public static <W> ServiceResult<W> asSuccess(W obj){
		return new ServiceResult<W>(true,"success",obj);
	}
	
	public static <W> ServiceResult<W> asFail(String msg){
		return new ServiceResult<W>(false,msg,null);
	}
	
	public static <W> ServiceResult<W> asFail(String msg, W obj){
		return new ServiceResult<W>(false,msg,obj);
	}
	
	public String toJson(Class<?>... addtionClassDefine) {
		return toJson(new HashMap<String,String>(),addtionClassDefine);
	}
	
	public String toJson(Map<String,String> addtionParams,Class<?>... addtionClassDefine) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		result.append("\"success\": " + success + ",");
		if (StringUtils.isNotBlank(msg)) {
			result.append("\"msg\": \"" + msg + "\",");
		}
		if (StringUtils.isNotBlank(resultCode)) {
			result.append("\"resultCode\": \"" + resultCode + "\",");
		}
		if(addtionParams != null){
			for(String key : addtionParams.keySet()){
				if (StringUtils.isNotBlank(key)) {
					result.append("\""+key+"\": \"" + addtionParams.get(key) + "\",");
				}
			}
		}
		
		if (obj != null) {
			Class<?> rootClazz = obj.getClass();
			if(rootClazz == Boolean.class || rootClazz == Byte.class ||rootClazz == String.class ||rootClazz == Short.class 
					|| rootClazz == Integer.class  || rootClazz == Long.class  || rootClazz == Float.class 
					|| rootClazz == Double.class || rootClazz == Character.class){
				result.append("\"obj\": \"" + String.valueOf(obj) + "\",");
			}else{
				result.append("\"obj\": " + GsonUtils.toJson(obj,addtionClassDefine) + ",");
			}
		}
		result.append("\"addtion\": \"\"");
		result.append("}");
		return result.toString();
	}
	
//	public String toJson(Class... clazz) {
//		StringBuilder result = new StringBuilder();
//		result.append("{");
//		result.append("\"success\": " + success + ",");
//		if (StringUtils.isNotBlank(msg)) {
//			result.append("\"msg\": \"" + msg + "\",");
//		}
//		if (StringUtils.isNotBlank(resultCode)) {
//			result.append("\"resultCode\": \"" + resultCode + "\",");
//		}
//		if (obj != null) {
//			Class rootClazz = obj.getClass();
//			if(rootClazz == Boolean.class || rootClazz == Byte.class ||rootClazz == String.class ||rootClazz == Short.class 
//					|| rootClazz == Integer.class  || rootClazz == Long.class  || rootClazz == Float.class 
//					|| rootClazz == Double.class || rootClazz == Character.class){
//				result.append("\"obj\": \"" + String.valueOf(obj) + "\",");
//			}else{
//				result.append("\"obj\": " + GsonFactory.build(obj.getClass(),clazz).toJson(obj) + ",");
//			}
//		}
//		result.append("\"addtion\": \"\"");
//		result.append("}");
//		return result.toString();
//	}

}
