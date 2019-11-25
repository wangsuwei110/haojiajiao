package com.education.hjj.bz.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.weixin4j.model.message.template.TemplateData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.util.HttpClientUtils;
import com.education.hjj.bz.util.SendTemplateMessage;
import com.education.hjj.bz.util.SendWXMessageUtils;
import com.education.hjj.bz.util.Token;
import com.education.hjj.bz.util.UrlUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;

@Service
public class WeChatService {
	
	private static Logger logger = LoggerFactory.getLogger(WeChatService.class);

	/**
	 * 发送模板消息sendTemplateMessage 小程序模板消息,发送服务通知
	 * 
	 * @param touser      接收者（用户）的 openid
	 * @param template_id 所需下发的模板消息的id
	 * @param page        点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
	 * @param formid      表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 * @return
	 */
	public static JSONObject sendTemplateMessage(String touser, String template_id, String page, String formid,
			Map<String, TemplateData> map) {
		String accessToken = getAccessToken();
		SendTemplateMessage sendTemplateMessage = new SendTemplateMessage();
		// 拼接数据
		sendTemplateMessage.setTouser(touser);
		sendTemplateMessage.setTemplate_id(template_id);
		sendTemplateMessage.setPage(page);
		sendTemplateMessage.setForm_id(formid);
		sendTemplateMessage.setData(map);
		sendTemplateMessage.setEmphasis_keyword("");
		String json = JSONObject.toJSONString(sendTemplateMessage);
		String ret = sendPost(Constant.SEND_WX_COMMON_TEMPLATE_MESSAGE + accessToken, json);
		return JSON.parseObject(ret);
	}

	public static String getAccessToken() {
		String param = "grant_type=client_credential&appid=" + Constant.APP_ID + "&secret=" + Constant.APP_SECRET;
		String result = UrlUtils.sendGet(Constant.ACCESS_TOKEN, param);
		JSONObject demoJson = JSONObject.parseObject(result);
		String accessToken = demoJson.getString("access_token");
		String expiresIn = demoJson.getString("expires_in");
		Token token = Token.getInstance();
		token.setAccessToken(accessToken);
		// 过期时间的毫秒数
		token.setExpiryTime(System.currentTimeMillis() + 1000 * 60 * 100L);
		return accessToken;
	}

	/**
	 * 发送post请求 json格式
	 * 
	 * @param url
	 * @param param
	 * @return
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常!" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 统一服务消息
	 * 小程序模板消息,发送服务通知
	 * @param token 小程序ACCESS_TOKEN
	 * @param touser 用户openid，可以是小程序的openid，也可以是公众号的openid
	 * @param template_id 小程序模板消息模板id
	 * @param page 小程序页面路径
	 * @param formid 小程序模板消息formid
	 * @param data 小程序模板消息formid
	 * @param emphasis_keyword 小程序模板放大关键词
	 * @return
	 * @author HGL
	 */
	public static JSONObject sendWeappMessage(String token,String touser,String template_id,
			String page,String formid,JSONObject data){
		JSONObject obj = new JSONObject();
		JSONObject weapp_template_msg = new JSONObject();
		JSONObject result = new JSONObject();
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="+token;
			obj.put("touser", touser);
			weapp_template_msg.put("template_id", template_id);
			weapp_template_msg.put("page", page);
			weapp_template_msg.put("form_id", formid);
			weapp_template_msg.put("data", data);
			weapp_template_msg.put("emphasis_keyword", data.getJSONObject("keyword1").getString("value"));
			obj.put("weapp_template_msg", weapp_template_msg);
			result = SendWXMessageUtils.post(url, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 统一服务消息
	 * 公众号模板消息,发送公众号通知
	 * @param token 小程序ACCESS_TOKEN
	 * @param touser 用户openid，可以是小程序的openid，也可以是公众号的openid
	 * @param appid 公众号appid
	 * @param template_id 公众号模板消息模板id
	 * @param url 公众号模板消息所要跳转的url
	 * @param weappid 公众号模板消息所要跳转的小程序appid，小程序的必须与公众号具有绑定关系
	 * @param pagepath 公众号模板消息所要跳转的小程序页面
	 * @param data 公众号模板消息的数据
	 * @return
	 * @author HGL
	 */
	public static JSONObject sendMpMessage(String token,String touser,String appid,
			String template_id,String url,String weappid,String pagepath,JSONObject data){
		JSONObject result = new JSONObject();
		JSONObject obj = new JSONObject();
		JSONObject mp_template_msg = new JSONObject();
		JSONObject miniprogram = new JSONObject();
		try {
			String path = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="+token;
			obj.put("touser", touser);
			mp_template_msg.put("appid",appid);
			mp_template_msg.put("template_id", template_id);
			mp_template_msg.put("url",url);
			miniprogram.put("appid", weappid);
			miniprogram.put("pagepath", pagepath);
			mp_template_msg.put("miniprogram", miniprogram);
			mp_template_msg.put("data", data);
			obj.put("mp_template_msg", mp_template_msg);
			result = SendWXMessageUtils.post(path, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		
		String accessToken = getAccessToken();
		String toUser = "oWQvd4rFQFjChqwaKvWfPd1vXT9w";
		String template_id = "I1-wAFCoM_VPlpIGCagwEvXEv7bCCf--52EoIU39aK0";
		
		String common_template_id ="vBLV5D0zQNq4mZNYIvQ8xo9oqDvnJlwvMYEWEw1atRc";
		String page = "";
		String formid = "wx260011530182890ff6231e831530510400";
		String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="+accessToken;
		
		
		JSONObject data = new JSONObject();

		Map<String,Object> keyMap1 = new HashMap<String,Object>();
		keyMap1.put("value","王教员");
		//添加客户名称
		data.put("keyword1",keyMap1);

		Map<String,Object> keyMap2 = new HashMap<String,Object>();
		keyMap2.put("value","上海人");
		//添加卖方名称
		data.put("keyword2",keyMap2);

		Map<String,Object> keyMap3 = new HashMap<String,Object>();
		keyMap3.put("value","浦东新区张江镇");
		//添加对账月份
		data.put("keyword3",keyMap3);
		
//		String aa = data.getJSONObject("keyword1").getString("value");
//		
//		System.out.println(aa);
		
		JSONObject obj = new JSONObject();
		JSONObject weapp_template_msg = new JSONObject();
		
		JSONObject mp_template_msg = new JSONObject();
		JSONObject miniprogram = new JSONObject();
		JSONObject result = new JSONObject();
		
		try {
			obj.put("touser", toUser);
			weapp_template_msg.put("template_id", template_id);
			weapp_template_msg.put("page", page);
			weapp_template_msg.put("form_id", formid);
			weapp_template_msg.put("data", data);
			weapp_template_msg.put("emphasis_keyword", data.getJSONObject("keyword1").getString("value"));
			obj.put("weapp_template_msg", weapp_template_msg);
			
			
			
			mp_template_msg.put("appid", Constant.COMMON_APP_ID);
			mp_template_msg.put("template_id", common_template_id);
			mp_template_msg.put("url","");
			miniprogram.put("appid", Constant.APP_ID);
			miniprogram.put("pagepath", "");
			mp_template_msg.put("miniprogram", miniprogram);
			mp_template_msg.put("data", data);
			obj.put("mp_template_msg", mp_template_msg);
			
			logger.info("obj = "+obj);
			
			result = SendWXMessageUtils.post(url, obj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(JSONObject.toJSONString(result));
	}
	
}
