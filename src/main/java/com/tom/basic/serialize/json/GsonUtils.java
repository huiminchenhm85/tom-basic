package com.tom.basic.serialize.json;

import java.util.List;

import com.google.gson.GsonBuilder;

public class GsonUtils {

//	public static Gson build(Class rootClass,Class... clazz) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.registerTypeAdapter(rootClass, new GsonAdaptorWithGetter());
//		if(clazz != null){
//			for(Class each : clazz){
//				builder.registerTypeAdapter(each, new GsonAdaptorWithGetter());
//			}
//		}
//		return builder.create();
//	}
//	
//	public static <T> String toJson(T obj ,boolean disableHtmlEscaping) {
//		GsonBuilder builder = new GsonBuilder();
//		builder.registerTypeAdapter(obj.getClass(), new GsonAdaptorWithGetter<T>());
//		if(disableHtmlEscaping){
//			builder.disableHtmlEscaping();
//		}
//		return builder.create().toJson(obj);
//	}
	
	@SuppressWarnings("rawtypes")
	public static <T> String toJson(List<T> obj,Class<?>... addtionClassDefine){
		if(obj == null || obj.isEmpty()){
			return "[]";
		}
		Class<?> clazz = obj.get(0).getClass();
		
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(clazz, new GsonAdaptorWithGetter<T>());
		for(Class<?> each : addtionClassDefine){
			builder.registerTypeAdapter(each, new GsonAdaptorWithGetter());
		}
		
		return builder.create().toJson(obj);
	}
	
	@SuppressWarnings("rawtypes")
	public static <T> String toJson(T obj,Class<?>... addtionClassDefine){
		GsonBuilder builder = new GsonBuilder();
		Class<?> clazz = obj.getClass();
		
		builder.registerTypeAdapter(clazz, new GsonAdaptorWithGetter<T>());
		for(Class<?> each : addtionClassDefine){
			builder.registerTypeAdapter(each, new GsonAdaptorWithGetter());
		}
		
		return builder.create().toJson(obj);
	}
}