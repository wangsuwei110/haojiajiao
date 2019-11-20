package com.education.hjj.bz.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSONObject;

public class GetWXOpenIdUtil {
	
	private static Logger logger = LoggerFactory.getLogger(GetWXOpenIdUtil.class);
	
	//appId
	@Value("${wx.appId}")
	private static String APPID;
	
	//默认密码
	@Value("${wx.secret}")
	private static String SECRET;

	public static String getOpenId(String code) {
		
	 logger.info("code = {}" , code);
     /**
      * 构造请求链接
      * https://api.weixin.qq.com/sns/jscode2session?
      * appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
      */
     String url = Constant.ACCESS_TOKEN_URL+"?appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";  
     
     logger.info("url = {}" , url);
     
     Map<String, String> urlData= new HashMap<String, String>();
     
     urlData.put("appid",APPID);//小程序id
     urlData.put("secret",SECRET);//小程序key
     urlData.put("js_code",code);//小程序传过来的code
     urlData.put("grant_type","authorization_code");//固定值这样写就行
     
     String openid="";
     
		try {
			String jsonStr = HttpClientUtils.doGet(url, urlData);
			
			logger.info("jsonStr = {}" , jsonStr);
			
			openid = JSONObject.parseObject(jsonStr).getString("openid");
			
			String unionid = JSONObject.parseObject(jsonStr).getString("unionid");
			logger.info("openid = {}, unionid = {}" , openid , unionid);
			
         return openid;
					
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return openid;
		
	}
}
