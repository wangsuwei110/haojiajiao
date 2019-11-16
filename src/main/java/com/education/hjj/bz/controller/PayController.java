package com.education.hjj.bz.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.WeixinException;

import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.util.HttpClientUtils;
import com.education.hjj.bz.util.weixinUtil.CommonUtil;
import com.education.hjj.bz.util.weixinUtil.HttpUtil;
import com.education.hjj.bz.util.weixinUtil.MD5Utils;
import com.education.hjj.bz.util.weixinUtil.ObjectToMapUntils;
import com.education.hjj.bz.util.weixinUtil.RandomUtils;
import com.education.hjj.bz.util.weixinUtil.StringUtils;
import com.education.hjj.bz.util.weixinUtil.TimeUtils;
import com.education.hjj.bz.util.weixinUtil.WXUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;
import com.education.hjj.bz.util.weixinUtil.vo.Json;
import com.education.hjj.bz.util.weixinUtil.vo.PayInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "微信" })
@RestController
@RequestMapping(value = "/weixin")
public class PayController {

	private static Logger logger = LoggerFactory.getLogger(PayController.class);

	@ResponseBody
	@RequestMapping(value = "/prepay", method = RequestMethod.POST)
	@ApiOperation("微信统一下单")
	public Json prePay(@RequestBody LoginForm loginForm, HttpServletRequest request) {

		String code = loginForm.getCode();//获取微信服务器授权返回的code值
		
		Json json = new Json();

		System.out.println("code:" + code);

		logger.info("\n======================================================");
		logger.info("code: " + code);

		try {
			String openId = getOpenId(code);
//			String openId = "oWQvd4hQGST1gQz3hQLeEZhDjb8g";

			if (StringUtils.isBlank(openId)) {
				
				logger.error("获取到openId : " + openId);
				
			} else {
				
				openId = openId.replace("\"", "").trim();

				String clientIP = CommonUtil.getClientIp(request);
//				String clientIP = "192.168.1.2";

				logger.error("openId: " + openId + ", clientIP: " + clientIP);

				String randomNonceStr = RandomUtils.generateMixString(32);

				String wxPayResult = unifiedOrder(openId, clientIP, randomNonceStr);

				Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);

				String return_code = parseResult.get("return_code");

				logger.info("调试模式_统一下单接口返回状态码 : " + return_code);

				// 返回给移动端需要的参数
				Map<String, Object> response = new HashMap<String, Object>();

				if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

					String return_msg = parseResult.get("return_msg");
					if (StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
						// log.error("统一下单错误！");
						return null;
					}

					String prepay_Id = parseResult.get("prepay_id");

					response.put("nonceStr", randomNonceStr);
					response.put("package", "prepay_id = " + prepay_Id);

					Long timeStamp = System.currentTimeMillis() / 1000;
					response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

					String stringSignTemp = "appId=" + Constant.APP_ID + "&nonceStr=" + randomNonceStr
							+ "&package=prepay_id=" + prepay_Id + "&signType=" + Constant.SIGN_TYPE + "&timeStamp="
							+ timeStamp;

					// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
					String paySign = WXUtils.sign(stringSignTemp, Constant.APP_KEY, "utf-8").toUpperCase();

					logger.info("=======================第二次签名：" + paySign + "=====================");

					response.put("paySign", paySign);

					//更新订单信息
	                //业务逻辑代码
					// TODO

				} else {

					return null;
				}

				response.put("appid", Constant.APP_ID);

				json.setSuccess(true);
				json.setData(response);
			}
		} catch (Exception e1) {
			json.setSuccess(false);
			json.setMsg("发起失败");
			e1.printStackTrace();
		}

		return json;
	}

	/**
	 * @Description:微信支付
	 * @return
	 * @author dzg
	 * @throws Exception
	 * @throws WeixinException
	 * @date 2016年12月2日
	 */
	@RequestMapping(value = "/wxNotify", method = RequestMethod.POST)
//	@ResponseBody
	@ApiOperation("微信支付")
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));

		String line = null;

		StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		// sb为微信返回的xml
		String notityXml = sb.toString();
		String resXml = "";
		System.out.println("接收到的报文：" + notityXml);

		Map map = WXUtils.doXMLParse(notityXml);

		String returnCode = (String) map.get("return_code");
		if ("SUCCESS".equals(returnCode)) {
			// 验证签名是否正确
			if (WXUtils.verify(WXUtils.createLinkString(map), (String) map.get("sign"), Constant.APP_KEY, "utf-8")) {
				/** 此处添加自己的业务逻辑代码start **/

				/** 此处添加自己的业务逻辑代码end **/

				// 通知微信服务器已经支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		System.out.println(resXml);
		System.out.println("微信支付回调数据结束");

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}

	private String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.APP_ID + "&secret="
				+ Constant.APP_SECRET + "&code=" + code + "&grant_type=authorization_code";

		Map<String, String> urlData = new HashMap<String, String>();

		urlData.put("appid", Constant.APP_ID);// 小程序id
		urlData.put("secret", Constant.APP_SECRET);// 小程序key
		urlData.put("js_code", code);// 小程序传过来的code
		urlData.put("grant_type", "authorization_code");// 固定值这样写就行

		String openid;

		try {
			String jsonStr = HttpClientUtils.doGet(url, urlData);

			logger.info("jsonStr = {}", jsonStr);

			openid = JSONObject.parseObject(jsonStr).getString("openid");

			String unionid = JSONObject.parseObject(jsonStr).getString("unionid");
			logger.info("openid = {}, unionid = {}", openid, unionid);

			return openid;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 调用统一下单接口
	 * 
	 * @param openId
	 */
	private String unifiedOrder(String openId, String clientIP, String randomNonceStr) {

		try {

			String url = Constant.URL_UNIFIED_ORDER;

			PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr);
			String md5 = getSign(payInfo);
			payInfo.setSign(md5);

			logger.info("md5 value: " + md5);

			String xml = CommonUtil.payInfoToXML(payInfo);
			xml = xml.replace("__", "_").replace("<![CDATA[1]]>", "1");
			logger.info("调试模式_统一下单接口请求XML数据: {}", xml);

			StringBuffer buffer = HttpUtil.httpsRequest(url, "POST", xml);

			logger.info("调试模式_统一下单接口返回XML数据: " + buffer.toString());

			return buffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr) {

		Date date = new Date();
		String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
		String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

		String randomOrderId = CommonUtil.getRandomOrderId();

		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(Constant.APP_ID);
		payInfo.setMch_id(Constant.MCH_ID);
		payInfo.setDetail(
				"{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"iphone\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"iphone\" } ] }");
		payInfo.setDevice_info("WEB");
		payInfo.setNonce_str(randomNonceStr);
		payInfo.setSign_type(Constant.SIGN_TYPE); // 默认即为MD5
		payInfo.setBody("JSAPItest");
		payInfo.setAttach("test4luluteam");
		payInfo.setOut_trade_no(randomOrderId);
		payInfo.setTotal_fee(1);
		payInfo.setSpbill_create_ip(clientIP);
		payInfo.setTime_start(timeStart);
		payInfo.setTime_expire(timeExpire);
		payInfo.setNotify_url(Constant.URL_NOTIFY);
		payInfo.setTrade_type("JSAPI");
		payInfo.setLimit_pay("no_credit");
		payInfo.setOpenid(openId);

		return payInfo;
	}

	private String getSign(PayInfo payInfo) throws Exception {

		String stringSignTemp = null;
		try {
			Map<String, String> stringObjectMap = ObjectToMapUntils.objectToMap(payInfo);
			// 参数ASCLL码字典序,并将结果转为URL参数串类型
			String stringA = WXUtils.formatUrlMap(stringObjectMap, false, false);
			String stringSignTempmode = stringA + "&key=" + Constant.APP_KEY;//
			logger.info("拼接key后的参数串：" + stringSignTempmode);
			stringSignTemp = MD5Utils.MD5(stringSignTempmode);
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("加密后的参数串：" + stringSignTemp);

		return stringSignTemp;
	}
}