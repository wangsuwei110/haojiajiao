package com.education.hjj.bz.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.education.hjj.bz.formBean.TeacherAccountForm;
import com.education.hjj.bz.service.IRedisService;
import com.education.hjj.bz.service.UserAccountLogService;
import com.education.hjj.bz.service.UserAccountService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.DateUtil;
import com.education.hjj.bz.util.SendWXMessageUtils;
import com.education.hjj.bz.util.weixinUtil.CommonUtil;
import com.education.hjj.bz.util.weixinUtil.HttpUtil;
import com.education.hjj.bz.util.weixinUtil.MD5Utils;
import com.education.hjj.bz.util.weixinUtil.ObjectToMapUntils;
import com.education.hjj.bz.util.weixinUtil.RandomUtils;
import com.education.hjj.bz.util.weixinUtil.WXUtils;
import com.education.hjj.bz.util.weixinUtil.config.Constant;
import com.education.hjj.bz.util.weixinUtil.vo.Json;
import com.education.hjj.bz.util.weixinUtil.vo.PayCashPo;
import com.education.hjj.bz.util.weixinUtil.vo.PayInfo;
import com.education.hjj.bz.util.weixinUtil.vo.RedpackRequestPo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "微信-提现到零钱" })
@RestController
@RequestMapping(value = "/payment")
@Transactional
public class PayCashController {

	private static Logger logger = LoggerFactory.getLogger(PayCashController.class);

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private UserAccountLogService userAccountLogService;

	@Resource
	private IRedisService redisService;

	@ResponseBody
	@RequestMapping(value = "/paymentToPocketMoney", method = RequestMethod.POST)
	@ApiOperation("微信提现到零钱")
	@Transactional
	public ApiResponse payCash(HttpServletRequest request, @RequestBody TeacherAccountForm teacherAccountForm) {

		Json json = new Json();

		Integer teacherId = teacherAccountForm.getTeacherId();

		// 表单提交的formId，发送消息通知用
		String formId = teacherAccountForm.getFormId();

		logger.info("提现的formId: " + formId);

		logger.info("当前提现教员的id: {}", teacherId);

		TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));

		String databaseOpenid = teacherVo.getOpenId();

		String telephone = teacherVo.getTelephone();

		String openId = teacherAccountForm.getOpenId();
		logger.info("当前提现教员的openid: {} , 注册时的openid；{}", openId, databaseOpenid);

		// 接收红包用户的openid
//		String openId = "oWQvd4hQGST1gQz3hQLeEZhDjb8g";

		if (databaseOpenid == null || openId == null || !databaseOpenid.equalsIgnoreCase(openId)) {
			logger.error("当前登录用户的openid和注册时的openid不一致，不允许提现！");
			return ApiResponse.error("提现失败,请稍后再试！");
		}

		String cashOut = teacherAccountForm.getCashOut();

		logger.info("当前用户要提现的金额: " + cashOut + " 元");

		BigDecimal cashOutData = new BigDecimal(cashOut);

		logger.info("当前用户要提现的金额: " + cashOutData + " 元");

		BigDecimal rebateData = new BigDecimal(100 * 0.95);// 转化为分

		int cash_out = cashOutData.multiply(rebateData).intValue();// 实际到账金额（分）
		logger.info("当前用户要提现的金额,转化为分: " + cash_out + " 分");

		BigDecimal cash = new BigDecimal(cash_out).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);// 实际到账金额（元）
		logger.info("当前用户要提现的金额,转化为元: " + cashOutData + " 元");

		logger.info("当前用户实际到账金额: " + cash + " 元");

		BigDecimal commission_cash = cashOutData.subtract(cash).setScale(2, BigDecimal.ROUND_HALF_UP);// 手续费金额（元）
		logger.info("当前用户要提现的金额手续费: " + commission_cash + " 元");

		// 教员用户账户余额扣除

		TeacherAccountVo teacherAccountVo = userAccountService.queryTeacherAccount(teacherId);

		BigDecimal teacherSurplusMoney = new BigDecimal(0);

		if (teacherAccountVo == null) {

			logger.error("当前用户账户为空，请充值后再试！");
			json.setSuccess(false);
			json.setMsg("提现失败,当前用户账户为空，请充值后再试！");
			return ApiResponse.error("提现失败,当前用户账户为空，请充值后再试！");

		} else {

			teacherSurplusMoney = teacherAccountVo.getSurplusMoney();

			if (teacherSurplusMoney.intValue() <= 0) {
				logger.error("当前用户账户余额小于提现余额，请充值后再试！");
				json.setSuccess(false);
				json.setMsg("提现失败,当前用户账户余额小于提现余额，请充值后再试！");
				return ApiResponse.error("提现失败,当前用户账户余额小于提现余额，请充值后再试！");
			}
		}

		logger.info("当前用户可提现的金额: " + teacherSurplusMoney + " 元");

		BigDecimal surplusMoney = teacherSurplusMoney.subtract(new BigDecimal(cashOut)).setScale(2,
				BigDecimal.ROUND_HALF_UP);
		logger.info("当前用户提现后的账户余额: " + surplusMoney + " 元");

		// 随机字符串
		String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("随机字符串 v: " + nonceStr);

		// 商户订单号
		String partnerTradeNo = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("商户订单号： " + partnerTradeNo);
		// 设备的IP地址
		String clientIP = CommonUtil.getClientIp(request);
		//String clientIP ="112.64.61.153";
		logger.info("设备的IP： " + clientIP);

		String redisValue = redisService.getValue(telephone + "_payCash");
		logger.info("缓存中存储的商户号： " + redisValue);

		PayCashPo payCashPo = new PayCashPo();

		if (redisValue != null || StringUtils.isNotBlank(redisValue)) {
			payCashPo = JSON.parseObject(redisValue, PayCashPo.class);
		} else {

			payCashPo.setMch_appid(Constant.APP_ID);
			payCashPo.setMchid(Constant.MCH_ID);
			payCashPo.setNonce_str(nonceStr);
			payCashPo.setPartner_trade_no(partnerTradeNo);
			payCashPo.setOpenid(openId);
			payCashPo.setCheck_name("NO_CHECK");
			payCashPo.setAmount(cash_out);
			payCashPo.setDesc("来家教账户提现");
			payCashPo.setSpbill_create_ip(clientIP);

			try {
				String md5 = getSign(payCashPo);
				payCashPo.setSign(md5);

			} catch (Exception e) {
				json.setSuccess(false);
				json.setMsg("提现失败 ");
				logger.error("微信提现异常,获取签名失败，请稍后再试！");
				e.printStackTrace();
				return ApiResponse.errorData("提现失败 ！", json);
			}
		}

		String url = Constant.PAY_CASH;

		logger.info("调用获取证书的方法 " + url);

		try {
			String wxPayResult = WXUtils.httpsXMLPostPay(url, payCashPo, Constant.CERT_PATH, Constant.MCH_ID);

			logger.info("调试模式_统一下单接口返回XML数据: " + wxPayResult);

			Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);

			logger.info("调试模式_统一下单接口返回package: " + parseResult.get("package"));

			String return_code = parseResult.get("return_code");

			logger.info("调试模式_统一下单接口返回状态码 : " + return_code);

			if (StringUtils.isNotBlank(return_code) && "SUCCESS".equalsIgnoreCase(return_code)) {

				String result_code = parseResult.get("result_code");

				if (StringUtils.isNotBlank(result_code) && "SUCCESS".equalsIgnoreCase(result_code)) {

					// 扣款
					int i = -1;

					if (surplusMoney.intValue() >= 0) {

						TeacherAccountPo teacherAccountPo = new TeacherAccountPo();
						teacherAccountPo.setSurplusMoney(surplusMoney);
						teacherAccountPo.setUpdateTime(new Date());
						teacherAccountPo.setTeacherId(teacherVo.getTeacherId());

						i = userAccountService.updateTeacherAccount(teacherAccountPo);
					}

					if (i <= 0) {

						logger.error("当前用户账户扣除失败，请稍后再试！");
						json.setSuccess(false);
						json.setMsg("提现失败,请稍后再试！");
						return ApiResponse.error("提现失败,请稍后再试！");
					}

					// 插入账户日志表
					TeacherAccountOperateLogPo userAccountOperateLogPo = new TeacherAccountOperateLogPo();
					userAccountOperateLogPo.setPaymentStreamId(nonceStr);
					userAccountOperateLogPo.setPaymentPersonId(teacherId);
					userAccountOperateLogPo.setPaymentPersonName(teacherVo.getName());
					userAccountOperateLogPo.setPaymentType(1);
					userAccountOperateLogPo
							.setPaymentAccount(new BigDecimal(cashOut).setScale(2, BigDecimal.ROUND_HALF_UP));
					userAccountOperateLogPo.setPaymentDesc("提现");
					userAccountOperateLogPo.setStatus(1);
					userAccountOperateLogPo.setCreateTime(new Date());
					userAccountOperateLogPo.setCreateUser(teacherVo.getName());
					userAccountOperateLogPo.setUpdateTime(new Date());
					userAccountOperateLogPo.setUpdateUser(teacherVo.getName());

					logger.info("nonceStr = " + nonceStr + " teacherId = " + teacherId + " name= " + teacherVo.getName()
							+ " cashOut= " + cashOut);

					int userAccountFlag = userAccountLogService.insertUserAccountLog(userAccountOperateLogPo);

					if (userAccountFlag <= 0) {

						json.setSuccess(false);
						json.setMsg("插入教员收支表日志记录失败");
						logger.error("系统异常，请稍后再试！");

						return ApiResponse.error("系统异常，请稍后再试！");
					}

					JSONObject data = new JSONObject();

					Map<String, Object> keyMap1 = new HashMap<String, Object>();
					keyMap1.put("value", partnerTradeNo);
					// 添加客户名称
					data.put("keyword1", keyMap1);

					Map<String, Object> keyMap2 = new HashMap<String, Object>();
					keyMap2.put("value", DateUtil.covertFromDateToString(new Date()));
					// 添加卖方名称
					data.put("keyword2", keyMap2);

					Map<String, Object> keyMap3 = new HashMap<String, Object>();
					keyMap3.put("value", cashOutData.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					// 提现金额
					data.put("keyword3", keyMap3);

					Map<String, Object> keyMap4 = new HashMap<String, Object>();
					keyMap4.put("value", commission_cash.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					// 提现手续费
					data.put("keyword4", keyMap4);

					Map<String, Object> keyMap5 = new HashMap<String, Object>();
					keyMap5.put("value", cash.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					// 到账金额
					data.put("keyword5", keyMap5);

					Map<String, Object> keyMap6 = new HashMap<String, Object>();
					keyMap6.put("value", "提现到微信零钱");
					// 提现方式
					data.put("keyword6", keyMap6);

					Map<String, Object> keyMap7 = new HashMap<String, Object>();
					keyMap7.put("value", "微信零钱");
					// 到账类型
					data.put("keyword7", keyMap7);

					Map<String, Object> keyMap8 = new HashMap<String, Object>();
					keyMap8.put("value", "提现1~3个工作日到账金额");
					// 温馨提示
					data.put("keyword8", keyMap8);

					logger.info("发送提现成功的消息提醒......");
//					JSONObject sendRedPackRsult = SendWXMessageUtils.sendMessage(openId,
//							Constant.CASH_OUT_TO_ACCOUNT_MESSAGE, Constant.COMMON_CASH_OUT_TO_ACCOUNT_MESSAGE, formId,
//							data);
					
					JSONObject sendRedPackRsult = SendWXMessageUtils.sendMessage(openId,
							"", Constant.COMMON_CASH_OUT_TO_ACCOUNT_MESSAGE, formId,
							data);
					
					logger.info("提现消息发送的结果： " + sendRedPackRsult.getString("errcode") + " "
							+ sendRedPackRsult.getString("errmsg"));

					if (redisValue != null && StringUtils.isNotBlank(redisValue)) {
						redisService.delete(telephone + "_payCash");
					}

					return ApiResponse.success("提现成功！", json);

				} else {

					redisService.setKey(telephone + "_payCash", JSON.toJSONString(payCashPo));

					json.setSuccess(false);
					json.setMsg("提现失败 ,原因： " + parseResult.get("return_msg"));

					logger.error("错误代码  :{}, 错误代码描述:{}", parseResult.get("err_code"), parseResult.get("err_code_des"));
					return ApiResponse.errorData("提现失败 ！", json);
				}
			} else {

				json.setSuccess(false);
				json.setMsg("提现失败 ,原因： " + parseResult.get("return_msg"));

				logger.error("返回信息: " + parseResult.get("return_msg"));
				return ApiResponse.errorData("提现失败 ！", json);
			}
		} catch (Exception e) {
			json.setSuccess(false);
			json.setMsg("提现失败 ");
			logger.error("微信提现异常，请稍后再试！");
			e.printStackTrace();
			return ApiResponse.errorData("提现失败 ！", json);
		}

	}

	@Transactional
	private String getSign(PayCashPo payCashPo) throws Exception {

		String stringSignTemp = null;
		try {
			Map<String, String> stringObjectMap = ObjectToMapUntils.objectToMap(payCashPo);
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
