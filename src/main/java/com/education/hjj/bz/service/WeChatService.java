package com.education.hjj.bz.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.weixin4j.model.message.template.TemplateData;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.util.SendTemplateMessage;
import com.education.hjj.bz.util.Token;
import com.education.hjj.bz.util.UrlUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;

@Service
public class WeChatService {

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
		String ret = sendPost(Constant.SEND_TEMPLATE_MESSAGE + accessToken, json);
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

}
