package com.jxrt.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

	public static Map getMapFromJson(String jsonString) {

		/*
		 * JSONObject jsonObject = new JSONObject(jsonString);
		 * 
		 * Map map = new HashMap(); for (Iterator iter = jsonObject.keys();
		 * iter.hasNext();) { String key = (String) iter.next(); map.put(key,
		 * jsonObject.get(key)); } return map;
		 */

		return null;

	}

	public static String bean2json(Object bean) {
		return JsonHolder.gson.toJson(bean);
	}

	public static <T> T json2bean(String json, Type type) {
		return (T) JsonHolder.gson.fromJson(json, type);
	}

	private static class JsonHolder {
		private static Gson gson = new GsonBuilder()
				.setDateFormat(DateFormat.LONG)
				.enableComplexMapKeySerialization().disableHtmlEscaping()
				// .serializeNulls()
				// .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
				.setVersion(1.0).create();
	}

}
