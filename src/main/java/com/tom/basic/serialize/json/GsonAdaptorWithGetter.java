package com.tom.basic.serialize.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonAdaptorWithGetter<T> implements JsonSerializer<T> {

	private final static class CachedReflectionInfo {
		List<Field> toCheckFields = new ArrayList<Field>();
		Map<String, Method> toCheckMethods = new HashMap<String, Method>();

		public CachedReflectionInfo(Class<?> inputClazz) {
			List<Class<?>> tracingClass = new ArrayList<Class<?>>();
			Class<?> current = inputClazz;
			while (!current.equals(Object.class)) {
				if (!tracingClass.contains(current)) {
					tracingClass.add(current);
				}
				if (current.getInterfaces() != null) {
					for (Class<?> i : current.getInterfaces()) {
						if (!tracingClass.contains(i)) {
							tracingClass.add(i);
						}
					}
				}
				GsonRootClass rootClass = current.getAnnotation(GsonRootClass.class);
				if(rootClass != null){
					break;
				}
				
				current = current.getSuperclass();
			}
			for (Class<?> clazz : tracingClass) {
				for (Method method : clazz.getDeclaredMethods()) {
					if (method.getParameterTypes().length > 0) {
						continue;
					}
					GsonField def = method.getAnnotation(GsonField.class);
					if (def != null) {
						method.setAccessible(true);
						if (!toCheckMethods.containsKey(def.name())) {
							toCheckMethods.put(def.name(), method);
						}
					}
				}
			}
			current = inputClazz;
			while (current != null && !current.equals(Object.class)) {
				for (Field field : current.getDeclaredFields()) {
					GsonSkip gk = field.getAnnotation(GsonSkip.class);
					if (gk == null) {
						field.setAccessible(true);
						toCheckFields.add(field);
					}
				}
				GsonRootClass rootClass = current.getAnnotation(GsonRootClass.class);
				if(rootClass != null){
					break;
				}
				
				current = current.getSuperclass();
			}
		}
	}

	private static ConcurrentHashMap<Class<?>, CachedReflectionInfo> cache = new ConcurrentHashMap<Class<?>, CachedReflectionInfo>();

	@Override
	//
	public JsonElement serialize(T src, Type typeOfSrc,
			JsonSerializationContext context) {
		JsonObject object = new JsonObject();

		CachedReflectionInfo info = null;
		if (cache.containsKey(src.getClass())) {
			info = cache.get(src.getClass());
		} else {
			info = new CachedReflectionInfo(src.getClass());
			cache.putIfAbsent(src.getClass(), info);
		}
		
		for (Entry<String, Method> entry : info.toCheckMethods.entrySet()) {
			String name = entry.getKey();
			Method method = entry.getValue();
			if (object.has(name)) {
				continue;
			}
			try {
				object.add(name, context.serialize(method.invoke(src)));
			} catch (Exception e) {
				object.add(name, null);
			}

		}

		for (Field field : info.toCheckFields) {
			if (object.has(field.getName())) {
				continue;
			}
			try {

				object.add(field.getName(), context.serialize(field.get(src)));
			} catch (Exception e) {
				object.add(field.getName(), null);
			}
		}
		return object;
	}

}
