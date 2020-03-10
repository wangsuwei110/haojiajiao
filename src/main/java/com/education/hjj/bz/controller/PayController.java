package com.education.hjj.bz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.entity.vo.WeekTimeVo;
import com.education.hjj.bz.formBean.*;
import com.education.hjj.bz.mapper.*;
import com.education.hjj.bz.service.IRedisService;
import com.education.hjj.bz.util.*;
import com.education.hjj.bz.util.common.StringUtil;
import com.education.hjj.bz.util.weixinUtil.*;
import com.education.hjj.bz.util.weixinUtil.config.Constant;
import com.education.hjj.bz.util.weixinUtil.vo.Json;
import com.education.hjj.bz.util.weixinUtil.vo.PayInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.WeixinException;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Api(tags = { "微信" })
@RestController
@RequestMapping(value = "/weixin")
public class PayController {

	private static Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private DemandLogMapper demandLogMapper;

	@Autowired
	private DemandCourseInfoMapper demandCourseInfoMapper;

	@Autowired
	private StudentDemandMapper studentDemandMapper;

	@Autowired
	private UserAccountLogMapper userAccountLogMapper;

	@Autowired
	private StudentDemandConnectMapper connectMapper;

	@Resource
	private IRedisService redisService;
	
	@Autowired
	private UserInfoMapper userInfoMapper;


    @Transactional
	@RequestMapping(value = "/prepay", method = RequestMethod.POST)
	@ApiOperation("微信统一下单")
	public ApiResponse prePay(HttpServletRequest request, @RequestBody StudentDemandForm demandForm) {

		if (demandForm.getOrderMoney() == null) {
			logger.info("支付金额不能为空");
			return ApiResponse.error("支付金额不能为空");
		}

		String code = demandForm.getCode();//获取微信服务器授权返回的code值
		
		Json json = new Json();

		System.out.println("code:" + code);

		logger.info("\n======================================================");
		logger.info("code: " + code);

		//订单编号
		String randomOrderId = CommonUtil.getRandomOrderId();
		
		String openId = "";
		String prepay_Id = "";
		StudentDemandVo demandVo = new StudentDemandVo();
		String randomNonceStr = RandomUtils.generateMixString(32);
		try {
			openId = getOpenId(code);
//			String openId = "oWQvd4hQGST1gQz3hQLeEZhDjb8g";

			if (StringUtils.isBlank(openId)) {
				
				logger.error("未获取到openId");
                return ApiResponse.error("未获取到openId,支付失败");
			} else {
				
				openId = openId.replace("\"", "").trim();

				String clientIP = CommonUtil.getClientIp(request);
//				String clientIP = "192.168.1.2";

				logger.error("openId: " + openId + ", clientIP: " + clientIP);

				String wxPayResult = unifiedOrder(openId, clientIP, randomNonceStr ,randomOrderId, demandForm.getOrderMoney());

				Map<String, String> parseResult = CommonUtil.parseXml(wxPayResult);

				String return_code = parseResult.get("return_code");

				logger.info("调试模式_统一下单接口返回状态码 : " + return_code);

				// 返回给移动端需要的参数
				Map<String, Object> response = new HashMap<String, Object>();

				if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {

					String return_msg = parseResult.get("return_msg");
					if (StringUtils.isNotBlank(return_msg) && !return_msg.equals("OK")) {
						// log.error("统一下单错误！");
						return ApiResponse.error("统一下单错误！");
					}

					prepay_Id = parseResult.get("prepay_id");
					
					logger.info("prepay_id = " + prepay_Id);

					response.put("nonceStr", randomNonceStr);
					response.put("package", "prepay_id=" + prepay_Id);

					Long timeStamp = System.currentTimeMillis() / 1000;
					response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误

					String stringSignTemp = "appId=" + Constant.APP_ID + "&nonceStr=" + randomNonceStr
							+ "&package=prepay_id=" + prepay_Id + "&signType=" + Constant.SIGN_TYPE + "&timeStamp="
							+ timeStamp;

					// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
					String paySign = WXUtils.sign(stringSignTemp, Constant.APP_KEY, "utf-8").toUpperCase();

					logger.info("=======================第二次签名：" + paySign + "=====================");

					response.put("paySign", paySign);
                    response.put("signType", Constant.SIGN_TYPE);

				} else {

					return ApiResponse.error("支付失败,请检查订单");
				}

				response.put("appid", Constant.APP_ID);

				json.setSuccess(true);
				json.setData(response);
				
				logger.info("返回的json = " + json);
			}
		} catch (Exception e1) {
			json.setSuccess(false);
			json.setMsg("发起失败");
			e1.printStackTrace();

			logger.error("支付失败");
			return ApiResponse.errorData("支付失败", json);
		}
		redisService.cacheHashValue(RedisConstant.ORDER_SERIAL_NUMBER, randomNonceStr, JSON.toJSONString(demandForm), 60 * 60);

		JSONObject data = new JSONObject();

		Map<String,Object> keyMap1 = new HashMap<String,Object>();
		keyMap1.put("value", randomOrderId);
		//添加客户名称
		data.put("keyword1",keyMap1);

		Map<String,Object> keyMap2 = new HashMap<String,Object>();
		keyMap2.put("value", Constant.SEND_NAME);
		//添加卖方名称
		data.put("keyword2",keyMap2);

		Map<String,Object> keyMap3 = new HashMap<String,Object>();
		keyMap3.put("value",DateUtil.getStandardDayByYear(new Date()));
		//添加对账月份
		data.put("keyword3",keyMap3);

		Map<String,Object> keyMap4 = new HashMap<String,Object>();
		keyMap4.put("value","付款金额：" + demandForm.getOrderMoney() +"元");
		//添加对账月份
		data.put("keyword4",keyMap4);

		Map<String,Object> keyMap5 = new HashMap<String,Object>();
		keyMap5.put("value","商品详情：" + demandVo.getTeachBranchName());
		//添加对账月份
		data.put("keyword5",keyMap5);

		Map<String,Object> keyMap6 = new HashMap<String,Object>();
		keyMap6.put("value","授课讲师：" + demandVo.getTeachName());
		//添加对账月份
		data.put("keyword6",keyMap6);

		Map<String,Object> keyMap7 = new HashMap<String,Object>();
		keyMap7.put("value","已支付");
		//添加对账月份
		data.put("keyword7",keyMap7);

		JSONObject sendRedPackRsult = SendWXMessageUtils.sendMessage(openId, Constant.PAYMENT_SUCCESS_MESSAGE, Constant.COMMON_PAYMENT_SUCCESS_MESSAGE, prepay_Id, data);
		logger.info("提现消息发送的结果： " + sendRedPackRsult.getString("errcode") +" " + sendRedPackRsult.getString("errmsg"));
//
		

		return ApiResponse.success("支付成功", json);
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
	@Transactional
	public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("微信支付的回调接口：/wxNotify");

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
		logger.info("caohuan*****************接收到的报文：" + notityXml);

		Map map = WXUtils.doXMLParse(notityXml);

		String returnCode = (String) map.get("return_code");
        logger.info("caohuan*****************returnCode：" + returnCode);
        //订单编号
		String randomOrderId = (String) map.get("out_trade_no");
        logger.info("caohuan*****************randomOrderId：" + randomOrderId);
		// 订单流水号
		String randomNonceStr = (String) map.get("nonce_str");
        logger.info("caohuan*****************randomNonceStr：" + randomNonceStr);

		logger.info("微信支付的回调结果：" + returnCode);

		StudentDemandVo demandVo = new StudentDemandVo();

		if ("SUCCESS".equals(returnCode)) {
            logger.info("caohuan*******************map" +WXUtils.createLinkString(map));
            logger.info("caohuan*******************sign" +map.get("sign"));
			// 验证签名是否正确
			if (WXUtils.verify(WXUtils.createLinkString(map), (String) map.get("sign"), Constant.APP_KEY, "utf-8")) {

				String demandInfo = redisService.getHashValue(RedisConstant.ORDER_SERIAL_NUMBER, randomNonceStr);
				logger.info("caohuan*************订单流水号" + randomNonceStr);
				if (StringUtil.isBlank(demandInfo)) {
					logger.info("!!!!!!!!!!!!!!!!!支付成功后，获取订单详情失败!!!!!!!!!!!!!!!!!");
					return;
				}
				StudentDemandForm demandForm = JSON.parseObject(demandInfo, StudentDemandForm.class);

				/** 此处添加自己的业务逻辑代码start **/

                // *******************************逻辑信息**********************************
                logger.info("caohuan*********支付代码逻辑***********");
                //更新订单信息
                // 如果是试讲订单，要将试讲订单修改成付费订单
                demandVo = studentDemandMapper.findStudentDemandInfo(demandForm);

                logger.info("caohuan*********记录上个订单***********");
                // 记录上个订单的信息
                Date date = new Date();
                DemandLogForm logForm = new DemandLogForm();
                logForm.setCreateTime(date);
                logForm.setMark(JSON.toJSONString(demandVo));
                logForm.setDemandId(demandForm.getDemandId());
                logForm.setCreateUser(demandVo.getStudentId().toString());

                demandLogMapper.insert(logForm);
                Integer weekDay = DateUtil.getWeekOfDate(date);
                demandForm.setCurrentWeekDay(weekDay);

				Integer teacherId = demandVo.getTeacherId();
				TeacherVo teacherVo = userInfoMapper.queryTeacherHomeInfos(teacherId);

				TeacherPo teacherPo = new TeacherPo();
				teacherPo.setTeacherId(teacherId);
				// 记录当前支付时教员的费用
				demandForm.setChargesStandard(teacherVo.getChargesStandard());

                logger.info("caohuan*********修改上个订单***********");
                // 修改当前订单成新订单
                demandForm.setOrderType(2);
                demandForm.setStatus(2); // 支付时变成已接单
                demandForm.setOrderMoney(demandForm.getOrderMoney());
                demandForm.setPaymentStreamId(randomOrderId);
                demandForm.setOrderStart(date);
                demandForm.setUpdateTime(date);
                Long sid = studentDemandMapper.updateOldDemandToNew(demandForm);

                logger.info("caohuan*********修改订单状态至续课状态：状态变成4***********");
                // 修改订单状态至续课状态：状态变成4
                StudentDemandConnectForm connectForm = new StudentDemandConnectForm();
                connectForm.setTeacherId(demandVo.getTeacherId());
                connectForm.setDemandId(demandVo.getSid());
                connectForm.setStatus(4);
                connectForm.setUpdateTime(date);
                connectMapper.updateByDemandId(connectForm);

                logger.info("caohuan*********则将其它报名的订单全部改成5***********");
				// 试讲通过，则将其它报名的订单全部改成5
				connectForm.setTeacherId(demandVo.getTeacherId());
				connectForm.setStatus(5);
				connectMapper.updateStatusAndPass(connectForm);
				


				
				
				Integer isResumption = demandForm.getIsResumption();
				
				if(isResumption != null && isResumption == 1) {
					
					logger.info("续课支付...isResumption = "+isResumption);
					
					StudentDemandVo demandVos = studentDemandMapper.queryStudentDemandDetailBySid(demandForm.getDemandId());
					
					StudentDemandPo studentDemandPo = new StudentDemandPo();
					
					int resumption = demandVos.getIsResumption();
					//判断该订单是否已经续课，0未续课，1已续课
					
					logger.info("续课支付...resumption = "+resumption);
					logger.info(" 是否是第一次续课？{} " , resumption == 0?"是":"否");
					
					if(resumption == 0) {
						
						studentDemandPo.setIsResumption(1);
						
						studentDemandPo.setDemandId(demandForm.getDemandId());
						studentDemandPo.setUpdateTime(new Date());
			
						studentDemandMapper.updateDemandIsResumption(studentDemandPo);
						
						teacherPo.setResumptionCount(teacherVo.getResumptionCount()+1);
						
						double employCount = teacherVo.getEmployCount();
						
						double resumptionCount = teacherVo.getResumptionCount();
			
						double newRate = (resumptionCount+1) / employCount;
			
						logger.info(" employCount={} , resumptionCount={} , newRate={}", employCount,
								resumptionCount, newRate);
			
						BigDecimal bg = new BigDecimal(newRate).setScale(4, RoundingMode.UP);
						logger.info("employRate = {}", RegUtils.doubleToPersent().format(bg));
			
						teacherPo.setResumptionRate(RegUtils.doubleToPersent().format(bg));
						
						userInfoMapper.updateUserInfo(teacherPo);
					}
					
				}

				if(isResumption != null && isResumption == 0) {
					logger.info("正常支付...isResumption = " + isResumption);
					
					// 更新教员对所有报名订单的数量
	        		
	        		teacherPo.setEmployCount(teacherVo.getEmployCount() + 1);

	        		double chooseCount = teacherVo.getChooseCount();

					double newRate = 0;
					if (chooseCount != 0) {
						newRate = (teacherVo.getEmployCount() + 1) / chooseCount;
					}

	        		logger.info("employCount={} , chooseCount={} , newRate={}",  teacherVo.getEmployCount() + 1,
	        				chooseCount, newRate);

	        		BigDecimal bg = new BigDecimal(newRate).setScale(4, RoundingMode.UP);
	        		logger.info("employRate = {}", RegUtils.doubleToPersent().format(bg));
	        		// 更新该教员的聘用率
	        		teacherPo.setEmployRate(RegUtils.doubleToPersent().format(bg));
	        		
	        		
	        		//更新教员的续课率
					double resumptionCount = teacherVo.getResumptionCount();
					
					double resumptionRate = (resumptionCount) / (teacherVo.getEmployCount() + 1);
					
					logger.info(" employCount={} , resumptionCount={} , newRate={}", teacherVo.getEmployCount(),
							resumptionCount, newRate);
		
					BigDecimal bgr = new BigDecimal(resumptionRate).setScale(4, RoundingMode.UP);
					logger.info("employRate = {}", RegUtils.doubleToPersent().format(bgr));
		
					teacherPo.setResumptionRate(RegUtils.doubleToPersent().format(bgr));
	        		
	        		userInfoMapper.updateUserInfo(teacherPo);
				}

        		

                List<DemandCourseInfoForm> courseInfoFormList = new ArrayList<>();
                StudentDemandVo demand = demandVo;

                // 根据订单插入每个节课时
                for (int i = 0; i < demandForm.getWeekNum(); i++) {
                    List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);

                    final Integer weekNum = i;
                    list.forEach(w -> {
                        DemandCourseInfoForm courseInfoForm = new DemandCourseInfoForm();
                        if (weekDay >= w.getWeek()) {
                            Integer week = DateUtil.getWeekOfDate(DateUtil.addDay(date, 7 + 7*weekNum));
                            courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, 7 + 7*weekNum + w.getWeek() - week));
                        } else {
                            courseInfoForm.setOrderTeachTime(DateUtil.addDay(date, w.getWeek() - weekDay + (7*weekNum)));
                        }

                        courseInfoForm.setStatus(0);
                        courseInfoForm.setDeleteStatus(0);
                        courseInfoForm.setCreateTime(date);
                        courseInfoForm.setUpdateTime(date);
                        courseInfoForm.setWeekNum(w.getWeek());
                        courseInfoForm.setTimeNum(w.getTime());
                        courseInfoForm.setDemandId(demandForm.getDemandId());
                        courseInfoForm.setStudentId(demand.getStudentId());
                        courseInfoForm.setTeacherId(demand.getTeacherId());
                        courseInfoForm.setCreateUser(demand.getStudentId().toString());

                        courseInfoFormList.add(courseInfoForm);
                    });
                }

                demandCourseInfoMapper.insert(courseInfoFormList);

                // 插入一条日志信息，记录结课/支付记录
                TeacherAccountOperateLogPo paymentLog = new TeacherAccountOperateLogPo();
                paymentLog.setOrderId(randomOrderId);
                paymentLog.setPaymentStreamId(randomNonceStr);
                paymentLog.setPaymentPersonId(demandVo.getStudentId());
                paymentLog.setPaymentPersonName(demandVo.getStudentName());
                // 续课
                if (isResumption != null && isResumption == 1) {
					paymentLog.setPaymentType(4);
				} else {
					paymentLog.setPaymentType(2);
				}

                // 统计了课时
				List<WeekTimeVo> list = JSON.parseArray(demandForm.getTimeRange(), WeekTimeVo.class);
                paymentLog.setPaymentDesc("购买"+ demandForm.getWeekNum() + "周" + demandForm.getWeekNum() * list.size() + "课时");
                paymentLog.setStatus(1);
				paymentLog.setDemandId(demandForm.getDemandId());
                paymentLog.setCreateTime(date);
                paymentLog.setCreateUser(demandVo.getStudentName());
                paymentLog.setPaymentAccount(new BigDecimal(demandForm.getOrderMoney()));
                paymentLog.setUpdateTime(date);
                paymentLog.setUpdateUser(demandVo.getStudentName());
                userAccountLogMapper.insertUserAccountLog(paymentLog);
                
                // 插入一条日志信息，记录被支付人
                TeacherAccountOperateLogPo teacherPaymentLog = new TeacherAccountOperateLogPo();
                teacherPaymentLog.setOrderId(randomOrderId);
                teacherPaymentLog.setPaymentStreamId(randomNonceStr);
                teacherPaymentLog.setPaymentPersonId(teacherId);
                teacherPaymentLog.setPaymentPersonName(teacherVo.getName());
                teacherPaymentLog.setPaymentType(0);
                teacherPaymentLog.setPaymentAccount(new BigDecimal(demandForm.getOrderMoney()));
                teacherPaymentLog.setPaymentDesc("购买"+ demandForm.getWeekNum() + "周" + demandForm.getWeekNum() * list.size() + "课时");;
                teacherPaymentLog.setStatus(1);
                teacherPaymentLog.setCreateTime(new Date());
                teacherPaymentLog.setCreateUser(demandVo.getStudentName());
				teacherPaymentLog.setUpdateTime(date);
				teacherPaymentLog.setUpdateUser(demandVo.getStudentName());
                userAccountLogMapper.insertUserAccountLog(teacherPaymentLog);
                
                //开始发送支付订阅消息
                int demandId = demandForm.getDemandId();
                
                StudentDemandVo studentDemandVo  = studentDemandMapper.queryFirstPayInfos(demandId);
        		
        		JSONObject data = new JSONObject();
        		
        		Map<String, Object> keyMap1 = new HashMap<String, Object>();
        		keyMap1.put("value", studentDemandVo.getTeachBranchName()+" （学员："+studentDemandVo.getStudentName()+"，总课时："+studentDemandVo.getWeekNum() * studentDemandVo.getClassNum()+"）");
        		// 课程名称
        		data.put("thing2", keyMap1);
        		
        		if(isResumption != null && isResumption == 0) {
                	
        			Map<String, Object> keyMap2 = new HashMap<String, Object>();
            		keyMap2.put("value", studentDemandVo.getOrderMoney()+" 元");
            		// 上课时间
            		data.put("amount3", keyMap2);
                }
        		
        		if(isResumption != null && isResumption == 1) {
                	
        			Map<String, Object> keyMap2 = new HashMap<String, Object>();
            		keyMap2.put("value", studentDemandVo.getOrderMoney()+" 元(续课)");
            		// 上课时间
            		data.put("amount3", keyMap2);
                }
        		
        		Map<String, Object> keyMap3 = new HashMap<String, Object>();
        		keyMap3.put("value", DateUtil.covertFromDateToShortString(date));
        		// 上课地点
        		data.put("date4", keyMap3);
        		
        		logger.info("课程名称：  " + studentDemandVo.getTeachBranchName() + " 学生姓名： "
        				+ studentDemandVo.getStudentName() +" 购买周数： "+studentDemandVo.getWeekNum() 
        				+" 每周上课次数： "+studentDemandVo.getClassNum()+" 支付金额： "+studentDemandVo.getOrderMoney()+" 元 "
        				+" 订单购买时间： "+DateUtil.covertFromDateToShortString(date));
        		
        		JSONObject sendRedPackRsult = SendWXMessageUtils.sendSubscribeMessage(teacherVo.getOpenId(), Constant.BUY_CLASS_SUCCESS_MESSAGE, data);
        		
        		logger.info("发出正式上课提醒给学生的结果： " + sendRedPackRsult.getString("errcode") + " "
        				+ sendRedPackRsult.getString("errmsg"));
                

				/** 此处添加自己的业务逻辑代码end **/

				// 通知微信服务器已经支付成功
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			}
		} else {
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		logger.info(resXml);
		logger.info("微信支付回调数据结束");

		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(resXml.getBytes());
		out.flush();
		out.close();
	}
	
	@Transactional
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
	@Transactional
	private String unifiedOrder(String openId, String clientIP, String randomNonceStr , String randomOrderId, String orderMoney) {

		try {

			String url = Constant.URL_UNIFIED_ORDER;

			PayInfo payInfo = createPayInfo(openId, clientIP, randomNonceStr , randomOrderId, orderMoney);
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

	@Transactional
	private PayInfo createPayInfo(String openId, String clientIP, String randomNonceStr , String randomOrderId, String orderMoney) {

		Date date = new Date();
		String timeStart = TimeUtils.getFormatTime(date, Constant.TIME_FORMAT);
		String timeExpire = TimeUtils.getFormatTime(TimeUtils.addDay(date, Constant.TIME_EXPIRE), Constant.TIME_FORMAT);

		PayInfo payInfo = new PayInfo();
		payInfo.setAppid(Constant.APP_ID);
		payInfo.setMch_id(Constant.MCH_ID);
		payInfo.setDetail(
				"{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"iphone\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"iphone\" } ] }");
		payInfo.setDevice_info("WEB");
		payInfo.setNonce_str(randomNonceStr);
		payInfo.setSign_type(Constant.SIGN_TYPE); // 默认即为MD5
		payInfo.setBody("课时费");
		payInfo.setAttach("test4luluteam");
		payInfo.setOut_trade_no(randomOrderId);
		String money = String.valueOf(new BigDecimal(orderMoney).multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP));
		logger.info("金额============="+ Integer.valueOf(money));
		payInfo.setTotal_fee(Integer.valueOf(money)); // 单位：分
		payInfo.setSpbill_create_ip(clientIP);
		payInfo.setTime_start(timeStart);
		payInfo.setTime_expire(timeExpire);
		payInfo.setNotify_url(Constant.URL_NOTIFY);
		payInfo.setTrade_type("JSAPI");
		payInfo.setLimit_pay("no_credit");
		payInfo.setOpenid(openId);
		payInfo.setNotify_url("https://www.laijiajiaosh.com/weixin/wxNotify");

		return payInfo;
	}

	@Transactional
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
