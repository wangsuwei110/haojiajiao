package com.education.hjj.bz.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class UtilTools {
	
	public static JSONObject mapToJson(Map<String , Object> object) {
		
		
		
		JSONObject json = new JSONObject();
		
		json = new JSONObject(object);
		
		return json;
	} 
	
	public static String map2jsonstr(Map<String,?> map){
        return JSONObject.toJSONString(map);
    }
	
	public static Map<String,?> jsonstr2map(String jsonstr){
        return JSONObject.parseObject(jsonstr);
    }

}
