package com.education.hjj.bz.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.RedPackEnum;
import com.education.hjj.bz.formBean.TeacherAccountForm;
import com.education.hjj.bz.service.IRedisService;
import com.education.hjj.bz.service.UserAccountLogService;
import com.education.hjj.bz.service.UserAccountService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.HttpClientUtils;
import com.education.hjj.bz.util.SendWXMessageUtils;
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
	
	@Resource
    private IRedisService redisService;
	
	@ResponseBody
	@RequestMapping(value = "/sendRedPack", method = RequestMethod.POST)
	@ApiOperation("微信发红包")
	@Transactional
	public ApiResponse addRedPack(@RequestBody TeacherAccountForm teacherAccountForm) {
		
		Json json = new Json();
		
		Integer teacherId = teacherAccountForm.getTeacherId();
		
		//表单提交的formId，发送消息通知用
		String formId = teacherAccountForm.getFormId();
		
		logger.info("提现的formId: " + formId);
		
		logger.info("当前提现教员的id: {}" , teacherId);
		
		TeacherVo teacherVo =  userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
		
		String databaseOpenid = teacherVo.getOpenId();
		
		String telephone = teacherVo.getTelephone();
		
		//String code = teacherAccountForm.getCode();//获取微信服务器授权返回的code值
		
		//logger.info("code: " + code);
		
		//String openId = GetWXOpenIdUtil.getOpenId(code);
		
		//String openId =getOpenId(code);
//		if(openId == null) {
//			openId = teacherAccountForm.getOpenId();
//		}
		String openId = teacherAccountForm.getOpenId();
		logger.info("当前提现教员的openid: {} , 注册时的openid；{}" ,openId , databaseOpenid);
		
		//接收红包用户的openid
//		String openId = "oWQvd4hQGST1gQz3hQLeEZhDjb8g";
		
		if(databaseOpenid == null || openId == null || !databaseOpenid.equalsIgnoreCase(openId)) {
			logger.error("当前登录用户的openid和注册时的openid不一致，不允许提现！");
			return ApiResponse.error("提现失败,请稍后再试！");
		}
		
		String cashOut = teacherAccountForm.getCashOut();
		
		logger.info("当前用户要提现的金额: " + cashOut +" 元");
		
		BigDecimal cashOutData = new BigDecimal(cashOut);
		
		if(cashOutData.compareTo(new BigDecimal(1000)) == 1) {
			logger.error("单日提现不能超过¥1000，不允许提现！");
			return ApiResponse.error("单日提现不能超过¥1000");
		}
		
		logger.info("当前用户要提现的金额: " + cashOutData +" 元");
		
		BigDecimal  rebateData = new BigDecimal(100 * 0.95);//转化为分
		
		
		int cash_out = cashOutData.multiply(rebateData).intValue();//实际到账金额（分）
		logger.info("当前用户要提现的金额,转化为分: " + cash_out +" 分");
		
		BigDecimal cash = new BigDecimal(cash_out).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);//实际到账金额（元） 
		logger.info("当前用户要提现的金额,转化为元: " + cashOutData +" 元");
		
		logger.info("当前用户实际到账金额: " + cash +" 元");
		
		BigDecimal commission_cash = cashOutData.subtract(cash).setScale(2, BigDecimal.ROUND_HALF_UP);//手续费金额（元）
		logger.info("当前用户要提现的金额手续费: " + commission_cash +" 元");
		
		//教员用户账户余额扣除
		
		TeacherAccountVo  teacherAccountVo = userAccountService.queryTeacherAccount(teacherId);
		
		BigDecimal teacherSurplusMoney = new BigDecimal(0);
		
		if(teacherAccountVo == null) {
			
			logger.error("当前用户账户为空，请充值后再试！");
			json.setSuccess(false);
			json.setMsg("提现失败,当前用户账户为空，请充值后再试！");
			return ApiResponse.error("提现失败,当前用户账户为空，请充值后再试！");
			
		}else {
			
			teacherSurplusMoney = teacherAccountVo.getSurplusMoney();
			
			if(teacherSurplusMoney.intValue() <= 0) {
				logger.error("当前用户账户余额小于提现余额，请充值后再试！");
				json.setSuccess(false);
				json.setMsg("提现失败,当前用户账户余额小于提现余额，请充值后再试！");
				return ApiResponse.error("提现失败,当前用户账户余额小于提现余额，请充值后再试！");
			}
		}
		
		
		logger.info("当前用户可提现的金额: " + teacherSurplusMoney +" 元");
		
		BigDecimal surplusMoney = teacherSurplusMoney.subtract(new BigDecimal(cashOut)).setScale(2, BigDecimal.ROUND_HALF_UP) ;
		logger.info("当前用户提现后的账户余额: " + surplusMoney +" 元");
		
		RedpackRequestPo redpackRequestPo =new RedpackRequestPo();
		
		//商户订单号
		String mchBillno =  Constant.MCH_ID + DateUtil.getStandardDayByNum(new Date())+RandomUtils.getRandomNickname();
		
		String redisValue = redisService.getValue(telephone+"_redPack");
		logger.info("缓存中存储的商户号： " + redisValue );
		
		String nonceStr = "";
		
		if(redisValue != null || StringUtils.isNotBlank(redisValue)) {
			redpackRequestPo = JSON.parseObject(redisValue, RedpackRequestPo.class);
//			redpackRequestPo.setTotal_amount(Integer.valueOf(redpackRequestPo.getTotal_amount()));
//			redpackRequestPo.setTotal_num(Integer.valueOf(redpackRequestPo.getTotal_num()));
			
		}else {
			//流水单号
			nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
			redpackRequestPo.setAct_name(Constant.ACT_NAME);
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
//			redpackRequestPo.setClient_ip("112.65.13.31");
			
			String sign ="";
			
			try {
				
				sign = PayUtils.getSign(redpackRequestPo);
				
				logger.info("加密后的sign = " + sign);
				
				redpackRequestPo.setSign(sign);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		String wxPayResult = null;
		
		try {
			//小程序红包
			//String url = Constant.SEND_RED_PACK;
			
			//现金红包
			String url = Constant.SEND_RED_PACK;
			
			logger.info("调用获取证书的方法 " + url);
			
			wxPayResult = WXUtils.httpsXMLPostPay(url , redpackRequestPo , Constant.CERT_PATH , Constant.MCH_ID);
			
			logger.info("调试模式_统一下单接口返回XML数据: " + wxPayResult);
			
			Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);
			
			logger.info("调试模式_统一下单接口返回package: " + parseResult.get("package"));
			
			String return_code = parseResult.get("return_code");

			logger.info("调试模式_统一下单接口返回状态码 : " + return_code);
			
			// 返回给移动端需要的参数
			Map<String, Object> response = new HashMap<String, Object>();
			
			if(StringUtils.isNotBlank(return_code) && "SUCCESS".equalsIgnoreCase(return_code)) {
				
				String result_code = parseResult.get("result_code");
				
				if(StringUtils.isNotBlank(result_code) && "SUCCESS".equalsIgnoreCase(result_code)) {
					
					String randomNonceStr = RandomUtils.generateMixString(32);
					
					Long timeStamp = System.currentTimeMillis() / 1000;
					
//					String prepay_Id = parseResult.get("prepay_id");
					
//					String stringSignTemp = "appId=" + Constant.APP_ID + "&nonceStr=" + randomNonceStr
//							+ "&package=prepay_id=" + prepay_Id + "&signType=" + Constant.SIGN_TYPE + "&timeStamp="
//							+ timeStamp;
					
					String packageMessage = parseResult.get("package");
					
					packageMessage = packageMessage.substring(0 , packageMessage.lastIndexOf("&"))+"&appid="+Constant.APP_ID;
					
					logger.info("重新拼接后的package: " + packageMessage);
					
					String stringSignTemp = "appId=" + Constant.APP_ID + "&nonceStr=" + randomNonceStr
							+ "&package=" + packageMessage + "&timeStamp="
							+ timeStamp;
					// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
					String paySign = WXUtils.sign(stringSignTemp, Constant.APP_KEY, "utf-8");
					
					response.put("nonceStr", randomNonceStr);
					response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
					response.put("signType", Constant.SIGN_TYPE);
					response.put("paySign", paySign);//此处获取红包发放时的签名
					response.put("package", packageMessage);
					response.put("mchBillno", mchBillno);
					
					json.setSuccess(true);
					json.setData(response);
					
					//扣款
					int i = -1;
					
					if(surplusMoney.intValue() >= 0) {
						
						TeacherAccountPo teacherAccountPo = new TeacherAccountPo();
						teacherAccountPo.setSurplusMoney(surplusMoney);
						teacherAccountPo.setUpdateTime(new Date());
						teacherAccountPo.setTeacherId(teacherVo.getTeacherId());
						
						i = userAccountService.updateTeacherAccount(teacherAccountPo);
					}
					
					if(i <= 0) {
						
						logger.error("当前用户账户扣除失败，请稍后再试！");
						json.setSuccess(false);
						json.setMsg("提现失败,请稍后再试！");
						return ApiResponse.error("提现失败,请稍后再试！");
					}
					
					//插入账户日志表
					TeacherAccountOperateLogPo userAccountOperateLogPo = new TeacherAccountOperateLogPo();
					userAccountOperateLogPo.setPaymentStreamId(nonceStr);
					userAccountOperateLogPo.setPaymentPersonId(teacherId);
					userAccountOperateLogPo.setPaymentPersonName(teacherVo.getName());
					userAccountOperateLogPo.setPaymentType(1);
					userAccountOperateLogPo.setPaymentAccount(new BigDecimal(cashOut).setScale(2, BigDecimal.ROUND_HALF_UP));
					userAccountOperateLogPo.setPaymentDesc("提现");
					userAccountOperateLogPo.setStatus(1);
					userAccountOperateLogPo.setCreateTime(new Date());
					userAccountOperateLogPo.setCreateUser(teacherVo.getName());
					userAccountOperateLogPo.setUpdateTime(new Date());
					userAccountOperateLogPo.setUpdateUser(teacherVo.getName());
					
					logger.info("nonceStr = "+nonceStr +" teacherId = "+teacherId +" name= "+teacherVo.getName()+" cashOut= "+cashOut);
					
					int userAccountFlag = userAccountLogService.insertUserAccountLog(userAccountOperateLogPo);
					
					if(userAccountFlag <= 0) {
						
						json.setSuccess(false);
						json.setMsg("插入教员收支表日志记录失败");
						logger.error("系统异常，请稍后再试！");
						
						return ApiResponse.error("系统异常，请稍后再试！");
					}
					
					JSONObject data = new JSONObject();

					Map<String,Object> keyMap1 = new HashMap<String,Object>();
					keyMap1.put("value", mchBillno);
					//添加客户名称
					data.put("keyword1", keyMap1);

					Map<String,Object> keyMap2 = new HashMap<String,Object>();
					keyMap2.put("value", DateUtil.covertFromDateToString(new Date()));
					//添加卖方名称
					data.put("keyword2", keyMap2);

					Map<String,Object> keyMap3 = new HashMap<String,Object>();
					keyMap3.put("value", "¥"+cashOutData.setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"元");
					//提现金额
					data.put("keyword3",keyMap3);
					
					Map<String,Object> keyMap4 = new HashMap<String,Object>();
					keyMap4.put("value", commission_cash.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					//提现手续费
					data.put("keyword4",keyMap4);
					
					Map<String,Object> keyMap5 = new HashMap<String,Object>();
					keyMap5.put("value", cash.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					//到账金额
					data.put("keyword5",keyMap5);
					
					Map<String,Object> keyMap6 = new HashMap<String,Object>();
					keyMap6.put("value", "提现到微信");
					//提现方式
					data.put("keyword6",keyMap6);
					
					Map<String,Object> keyMap7 = new HashMap<String,Object>();
					keyMap7.put("value","现金红包");
					//到账类型
					data.put("keyword7",keyMap7);
					
					Map<String,Object> keyMap8 = new HashMap<String,Object>();
					keyMap8.put("value","提现1~3个工作日到账金额");
					//温馨提示
					data.put("keyword8",keyMap8);
					
					logger.info("发送提现成功的消息提醒......");
					JSONObject sendRedPackRsult = SendWXMessageUtils.sendMessage(openId, Constant.CASH_OUT_TO_ACCOUNT_MESSAGE, Constant.COMMON_CASH_OUT_TO_ACCOUNT_MESSAGE, formId, data);
					logger.info("提现消息发送的结果： " + sendRedPackRsult.getString("errcode") +" " + sendRedPackRsult.getString("errmsg"));
					
					
					if(redisValue != null && StringUtils.isNotBlank(redisValue)) {
						redisService.delete(telephone+"_redPack");
					}
					
					return ApiResponse.success("提现成功！", json);
					
				}else {
					
					redisService.setKey(telephone+"_redPack", JSON.toJSONString(redpackRequestPo));
					
					json.setSuccess(false);
					json.setMsg("体现失败 ,原因： "+parseResult.get("return_msg"));
					
					logger.error("错误代码  :{}, 错误代码描述:{}" , parseResult.get("err_code") , parseResult.get("err_code_des") );
					return ApiResponse.errorData("提现失败 ！", json);
				}
				
				
			}else{
				
				json.setSuccess(false);
				json.setMsg("提现失败 ,原因： "+parseResult.get("return_msg"));
				
				logger.error("返回信息: " + parseResult.get("return_msg"));
				return ApiResponse.errorData("提现失败 ！", json);
			}
		
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("提现失败 ");
			logger.error("微信红包提现异常，请稍后再试！");
			e.printStackTrace();
			return ApiResponse.errorData("提现失败 ！", json);
		}
		
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
	public ApiResponse addRedPack(@RequestBody CheckRedPackPo checkRedPackPo) {
		
		Json json = new Json();
		
		String mchBillno = checkRedPackPo.getMch_billno();
		
		CheckRedPackRequestPo checkRedPackRequestPo = new CheckRedPackRequestPo();
		
		logger.info("商户发放红包的商户订单号: {}" , mchBillno);
		
		try {
			checkRedPackRequestPo.setMch_id(Constant.MCH_ID);
			checkRedPackRequestPo.setAppid(Constant.APP_ID);
			checkRedPackRequestPo.setBill_type(Constant.BILL_TYPE);
			checkRedPackRequestPo.setNonce_str(UUID.randomUUID().toString().replaceAll("-", ""));
			checkRedPackRequestPo.setMch_billno(mchBillno);
			
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
					json.setSuccess(false);
					json.setMsg(parseResult.get("return_msg"));
					ApiResponse.error("查询微信红包异常，请稍后再试！");
				}
				
				
			}else{
				logger.error("返回信息: " + parseResult.get("return_msg"));
				json.setSuccess(false);
				json.setMsg(parseResult.get("return_msg"));
				ApiResponse.error("查询微信红包异常，请稍后再试！");
			}
			
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("查询失败");
			logger.error("查询微信红包异常，请稍后再试！");
			e.printStackTrace();
			ApiResponse.error("查询微信红包异常，请稍后再试！");
		}
		
		return ApiResponse.success("查询成功！", json);
	}

}
