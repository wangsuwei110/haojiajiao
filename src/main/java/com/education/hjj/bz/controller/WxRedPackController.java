package com.education.hjj.bz.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.RedPackEnum;
import com.education.hjj.bz.formBean.TeacherAccountForm;
import com.education.hjj.bz.service.UserAccountLogService;
import com.education.hjj.bz.service.UserAccountService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.GetWXOpenIdUtil;
import com.education.hjj.bz.util.HttpClientUtils;
import com.education.hjj.bz.util.weixinUtil.CommonUtil;
import com.education.hjj.bz.util.weixinUtil.PayUtils;
import com.education.hjj.bz.util.weixinUtil.RandomUtils;
import com.education.hjj.bz.util.weixinUtil.WXUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;
import com.education.hjj.bz.util.weixinUtil.vo.CheckRedPackPo;
import com.education.hjj.bz.util.weixinUtil.vo.CheckRedPackRequestPo;
import com.education.hjj.bz.util.weixinUtil.vo.Json;
import com.education.hjj.bz.util.weixinUtil.vo.RedpackRequestPo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "微信-小程序红包" })
@RestController
@RequestMapping(value = "/wxRedPack")
@Transactional
public class WxRedPackController {
	
	private static Logger logger = LoggerFactory.getLogger(WxRedPackController.class);

	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserAccountLogService userAccountLogService;
	
	@ResponseBody
	@RequestMapping(value = "/sendRedPack", method = RequestMethod.POST)
	@ApiOperation("微信发红包")
	public Json addRedPack(@RequestBody TeacherAccountForm teacherAccountForm) {
		
		Json json = new Json();
		
		Integer teacherId = teacherAccountForm.getTeacherId();
		
		logger.info("当前提现教员的id: {}" , teacherId);
		
		TeacherVo teacherVo =  userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
		
		String databaseOpenid = teacherVo.getOpenId();
		
		String code = teacherAccountForm.getCode();//获取微信服务器授权返回的code值
		
		logger.info("code: " + code);
		
		//String openId = GetWXOpenIdUtil.getOpenId(code);
		
		String openId =getOpenId(code);
		
		//接收红包用户的openid
//		String openId = "oWQvd4hQGST1gQz3hQLeEZhDjb8g";
		
		if(databaseOpenid == null || openId == null || !databaseOpenid.equalsIgnoreCase(openId)) {
			logger.error("当前登录用户的openid和注册时的openid不一致，不允许提现！");
			json.setSuccess(false);
			json.setMsg("提现失败,请稍后再试！");
			return json;
		}
		
		String cashOut = teacherAccountForm.getCashOut();
		
		logger.info("当前用户要提现的金额: " + cashOut +" 元");
		
		BigDecimal cashOutData = new BigDecimal(cashOut);
		
		BigDecimal  rebateData = new BigDecimal(100 * 0.95);
		
		int cash_out = cashOutData.multiply(rebateData).setScale(2, BigDecimal.ROUND_HALF_UP).intValue();
		
		logger.info("当前用户要提现的金额扣除手续费后: " + cashOut +" 元");
		
		
		//教员用户账户余额扣除
		
		TeacherAccountVo  teacherAccountVo = userAccountService.queryTeacherAccount(teacherId);
		
		BigDecimal teacherSurplusMoney = teacherAccountVo.getSurplusMoney();
		logger.info("当前用户可提现的金额: " + teacherSurplusMoney +" 元");
		
		TeacherAccountPo teacherAccountPo = new TeacherAccountPo();
		teacherAccountPo.setSurplusMoney(new BigDecimal(cashOut));
		teacherAccountPo.setUpdateTime(new Date());
		teacherAccountPo.setTeacherId(teacherVo.getTeacherId());
		
		int i = userAccountService.updateTeacherAccount(teacherAccountPo);
		
		if(i <= 0) {
			
			logger.error("当前用户账户扣除失败，请稍后再试！");
			json.setSuccess(false);
			json.setMsg("提现失败,请稍后再试！");
			return json;
		}
		
		
		RedpackRequestPo redpackRequestPo =new RedpackRequestPo();
		
		redpackRequestPo.setAct_name(Constant.ACT_NAME);
		//商户订单号
		String mchBillno = Constant.MCH_ID + DateUtil.getStandardDayByNum(new Date())+new Random().nextInt(10);
		//流水单号
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		
		redpackRequestPo.setMch_billno(mchBillno);		
		redpackRequestPo.setMch_id(Constant.MCH_ID);
		
		redpackRequestPo.setNonce_str(nonceStr);
		redpackRequestPo.setNotify_way(Constant.NOTIFY_WAY);
		redpackRequestPo.setRe_openid(openId);
		redpackRequestPo.setRemark(Constant.ACT_NAME);
		redpackRequestPo.setSend_name(Constant.SEND_NAME);
		redpackRequestPo.setTotal_amount(cash_out);//金额分
		redpackRequestPo.setTotal_num(1);//一人份
		redpackRequestPo.setWishing(Constant.WISHING);
		redpackRequestPo.setWxappid(Constant.APP_ID);
		redpackRequestPo.setScene_id(RedPackEnum.PRODUCT_4.getValue());
		
		//普通红包
//		redpackRequestPo.setClient_ip("112.65.13.31");
		try {
			
			String sign = PayUtils.getSign(redpackRequestPo);
			
			logger.info("加密后的sign = " + sign);
			
			redpackRequestPo.setSign(sign);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		String wxPayResult = null;
		
		try {
			//小程序红包
			//String url = Constant.SEND_RED_PACK;
			
			//现金红包
			String url = Constant.SEND_RED_PACK;
			
			wxPayResult = WXUtils.httpsXMLPostPay(url , redpackRequestPo , Constant.CERT_PATH , Constant.MCH_ID);
			
			logger.info("调试模式_统一下单接口返回XML数据: " + wxPayResult);
			
			Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);

			String return_code = parseResult.get("return_code");

			logger.info("调试模式_统一下单接口返回状态码 : " + return_code);
			
			// 返回给移动端需要的参数
			Map<String, Object> response = new HashMap<String, Object>();
			
			if(StringUtils.isNotBlank(return_code) && "SUCCESS".equalsIgnoreCase(return_code)) {
				
				String result_code = parseResult.get("result_code");
				
				if(StringUtils.isNotBlank(result_code) && "SUCCESS".equalsIgnoreCase(result_code)) {
					
					String randomNonceStr = RandomUtils.generateMixString(32);
					
					Long timeStamp = System.currentTimeMillis() / 1000;
					
					String prepay_Id = parseResult.get("prepay_id");
					
					String stringSignTemp = "appId=" + Constant.APP_ID + "&nonceStr=" + randomNonceStr
							+ "&package=prepay_id=" + prepay_Id + "&signType=" + Constant.SIGN_TYPE + "&timeStamp="
							+ timeStamp;
					// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
					String paySign = WXUtils.sign(stringSignTemp, Constant.APP_KEY, "utf-8").toUpperCase();
					
					response.put("nonceStr", randomNonceStr);
					response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
					response.put("signType", Constant.SIGN_TYPE);
					response.put("paySign", paySign);
					response.put("package", parseResult.get("package"));
					
					json.setSuccess(true);
					json.setData(response);
					
					TeacherAccountOperateLogPo userAccountOperateLogPo = new TeacherAccountOperateLogPo();
					userAccountOperateLogPo.setPaymentStreamId(nonceStr);
					userAccountOperateLogPo.setPaymentPersonId(teacherId);
					userAccountOperateLogPo.setPaymentPersonName(teacherVo.getName());
					userAccountOperateLogPo.setPaymentType(1);
					userAccountOperateLogPo.setPaymentAccount(new BigDecimal(cashOut));
					userAccountOperateLogPo.setPaymentDesc("提现");
					userAccountOperateLogPo.setStatus(1);
					userAccountOperateLogPo.setCreateTime(new Date());
					userAccountOperateLogPo.setCreateUser(teacherVo.getName());
					userAccountOperateLogPo.setUpdateTime(new Date());
					userAccountOperateLogPo.setUpdateUser(teacherVo.getName());
					
					int userAccountFlag = userAccountLogService.insertUserAccountLog(userAccountOperateLogPo);
					
					if(userAccountFlag <= 0) {
						
						json.setSuccess(false);
						json.setMsg("插入教员收支表日志记录失败");
						logger.error("系统异常，请稍后再试！");
						
						return json;
					}
					
					
				}else {
					
					logger.error("错误代码  :{}, 错误代码描述:{}" , parseResult.get("err_code") , parseResult.get("err_code_des") );
				}
				
				
			}else{
				logger.error("返回信息: " + parseResult.get("return_msg"));
			}
		
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("体现失败");
			logger.error("微信红包提现异常，请稍后再试！");
			e.printStackTrace();
		}
			 
		
		return json;
		
		
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
	
	@ResponseBody
	@RequestMapping(value = "/checkRedPackLog", method = RequestMethod.POST)
	@ApiOperation("查询微信红包记录")
	public Json addRedPack(@RequestBody CheckRedPackPo checkRedPackPo) {
		
		Json json = new Json();
		
		String mchBillno = checkRedPackPo.getMch_billno();
		
		CheckRedPackRequestPo checkRedPackRequestPo = new CheckRedPackRequestPo();
		
		logger.info("商户发放红包的商户订单号: {}" , mchBillno);
		
		try {
			checkRedPackRequestPo.setMch_id(Constant.MCH_ID);
			checkRedPackRequestPo.setAppid(Constant.APP_ID);
			checkRedPackRequestPo.setBill_type(Constant.BILL_TYPE);
			checkRedPackRequestPo.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
			
			String sign = PayUtils.getSign(checkRedPackRequestPo);
			checkRedPackRequestPo.setSign(sign);
			
			String wxPayResult = WXUtils.httpsXMLPostPay(Constant.CHECK_RED_PACK , checkRedPackRequestPo , Constant.CERT_PATH , Constant.MCH_ID);
			
			logger.info("调试模式_统一下单接口返回XML数据: " + wxPayResult);
			
			Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);

			String return_code = parseResult.get("return_code");

			logger.info("调试模式_统一下单接口返回状态码 : " + return_code);
			
			// 返回给移动端需要的参数
			Map<String, Object> response = new HashMap<String, Object>();
			
			if(StringUtils.isNotBlank(return_code) && "SUCCESS".equalsIgnoreCase(return_code)) {
				
				String result_code = parseResult.get("result_code");
				
				if(StringUtils.isNotBlank(result_code) && "SUCCESS".equalsIgnoreCase(result_code)) {
					
					response.put("mch_billno", parseResult.get("mch_billno"));
					response.put("mch_id", parseResult.get("mch_id"));
					response.put("detail_id", parseResult.get("detail_id"));
					response.put("status", parseResult.get("status"));
					response.put("send_type", parseResult.get("send_type"));
					response.put("hb_type", parseResult.get("hb_type"));
					response.put("total_num", parseResult.get("total_num"));
					response.put("total_amount", parseResult.get("total_amount"));
					response.put("send_time", parseResult.get("send_time"));
					response.put("openid", parseResult.get("openid"));
					response.put("amount", parseResult.get("amount"));
					response.put("rcv_time", parseResult.get("rcv_time"));
					
					json.setSuccess(true);
					json.setData(response);
					
				}else {
					
					logger.error("错误代码  :{}, 错误代码描述:{}" , parseResult.get("err_code") , parseResult.get("err_code_des") );
				}
				
				
			}else{
				logger.error("返回信息: " + parseResult.get("return_msg"));
			}
			
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查询失败");
			logger.error("查询微信红包异常，请稍后再试！");
			e.printStackTrace();
		}
		
		return json;
	}

}
