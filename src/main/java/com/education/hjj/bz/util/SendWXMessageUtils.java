package com.education.hjj.bz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weixin4j.model.message.template.TemplateData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.service.WeChatService;
import com.education.hjj.bz.util.weixinUtil.config.Constant;

public class SendWXMessageUtils {

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
	 * 
	 * @param url
	 * @param jsonObj
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static JSONObject post(String url,JSONObject jsonObj) throws Exception{
		
		logger.info("url= "+url+" jsonObj = "+jsonObj);
		
		HttpPost httpPost = new HttpPost(url); 
		StringEntity entity = new StringEntity(jsonObj.toString(), "utf-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse response=httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		
		logger.info("result= "+result);
		
		//输出调用结果
		if(response != null && response.getStatusLine().getStatusCode() == 200) { 
			// 生成 JSON 对象
			JSONObject obj = JSON.parseObject(result);
			return obj;
		}
		return null;
		
	}
	
	/**
	 * 发送统一消息服务
	 * @param toUser 被发送方的openId
	 * @param template_id 小程序消息模板id
	 * @param common_template_id 公众号消息模板id
	 * @param formid 提交请求时的formId或者支付结果的perpay_id
	 * @param data 封装的数据
	 * @return
	 */
	public static JSONObject sendMessage(String toUser , String template_id , String common_template_id , String formid , JSONObject data) {
		
		String accessToken = getAccessToken();
		String url = Constant.SEND_WX_COMMON_TEMPLATE_MESSAGE + accessToken;
		
		
		JSONObject obj = new JSONObject();
		JSONObject weapp_template_msg = new JSONObject();
		
		JSONObject mp_template_msg = new JSONObject();
		JSONObject miniprogram = new JSONObject();
		
		JSONObject result = new JSONObject();
		
		try {
			
			if(template_id != null && StringUtils.isNotBlank(template_id)) {
				obj.put("touser", toUser);
				weapp_template_msg.put("template_id", template_id);
				weapp_template_msg.put("page", "");
				weapp_template_msg.put("form_id", formid);
				weapp_template_msg.put("data", data);
				weapp_template_msg.put("emphasis_keyword", data.getJSONObject("keyword1").getString("value"));
				obj.put("weapp_template_msg", weapp_template_msg);
			}
			
			
			mp_template_msg.put("appid", Constant.COMMON_APP_ID);
			mp_template_msg.put("template_id", common_template_id);
			mp_template_msg.put("url","");
			miniprogram.put("appid", Constant.APP_ID);
			miniprogram.put("pagepath", "");
			mp_template_msg.put("miniprogram", miniprogram);
			mp_template_msg.put("data", data);
			obj.put("mp_template_msg", mp_template_msg);
			
			logger.info("obj = "+obj);
			
			result = post(url, obj);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
}
