package com.education.hjj.bz.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.education.hjj.bz.service.IRedisService;
import com.education.hjj.bz.service.ISmsService;
import com.education.hjj.bz.util.ApiResponse;
import com.education.hjj.bz.util.Constant;
import com.education.hjj.bz.util.IdentifyCodeUtil;
import com.education.hjj.bz.util.RegUtils;

@Service
public class SmsServiceImpl implements ISmsService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Value("${aliyun.sms.product}")
    private String PRODUCT; 
    
    @Value("${aliyun.sms.domain}")
    private String DOMAIN; 
 
    @Value("${aliyun.sms.accessKeyId}")
    private  String accessKeyId;
    @Value("${aliyun.sms.accessKeySecret}")
    private  String accessKeySecret;
    
    @Value("${aliyun.sms.templateCode}")
    private  String TEMPLATECODE;
    
    private static final String signName = "来家教";
    
 
    @Resource
    private IRedisService redisService;

	@Override
	public SendSmsResponse sendSms(String phoneNums, String signName, String templeteCode, String templateParam,
			String outId) throws ClientException {

		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
 
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
 
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNums);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);//众评众测
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templeteCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);//{"code":"152745"}
 
        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
 
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(outId);//zpzc
 
        //hint 此处可能会抛出异常，注意catch
 
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        acsClient.getAcsResponse(request);
        return sendSmsResponse;

	}

	@Override
	public QuerySendDetailsResponse querySendDetails(String phoneNumber, String bizId) throws ClientException {
		//可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
 
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
 
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
 
        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
	}

	@Override
	public Map<String, String> sendMessage(String mobile) {
		
		Map<String, String> map = new HashMap<String, String>();
    	
    	if (StringUtils.isEmpty(mobile)) {
			map.put("msg", "输入的手机号为空，请重新输入！");
			return map;
        }
    	
    	if (!RegUtils.isMoblie(mobile)) {
			map.put("msg", "输入的手机号格式不正确，请重新输入！");
			return map;
        }
		
		String identifyCode;
        //1. 判断是否缓存该账号验证码
        String returnCode = redisService.getValue(Constant.SMS_LOGIN_IDENTIFY_CODE+mobile);
        if (!StringUtils.isEmpty(returnCode)) {
            identifyCode = returnCode;
        } else {
            identifyCode = IdentifyCodeUtil.getRandom();
        }
        
      //2.发送短信
        Map<String, String> codeMap = new HashMap<>();
        codeMap.put("code", identifyCode);
        SendSmsResponse response;
        try {
            response = sendSms(mobile, signName, TEMPLATECODE, JSON.toJSONString(codeMap), null);
            //短信发送成功后存入redis
            if (response != null && Constant.SMS_SEND_STATUS_OK.equalsIgnoreCase(response.getCode()) && StringUtils.isEmpty(returnCode)) {
                redisService.setKey( Constant.SMS_LOGIN_IDENTIFY_CODE+mobile, identifyCode);
            }
            map.put("msg", "短信发送成功！");
            return map;
        } catch (Exception e) {
            logger.error("sendMessage method invoke error: {}", e.getMessage());
        }
        map.put("msg", "短信发送失败！");
        return map;

	}

	@Override
	public Map<String, Object> checkIsCorrectCode(String mobile, String identifyCode) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(mobile)) {
			map.put("msg", "您输入的手机号为空，请重新输入！");
			map.put("code", Constant.FAILED);
	        return map;
        }
		
		if (!RegUtils.isMoblie(mobile)) {
			map.put("msg", "您输入的手机号格式不正确，请重新输入！");
			map.put("code", Constant.FAILED);
			return map;
        }
		
		if (StringUtils.isEmpty(identifyCode)) {
			map.put("msg", "您输入的手机验证码为空，请重新输入！");
			map.put("code", Constant.FAILED);
	        return map;
        }
		
        String returnCode = redisService.getValue( Constant.SMS_LOGIN_IDENTIFY_CODE+mobile);
        if (!StringUtils.isEmpty(returnCode) && returnCode.equals(identifyCode)) {
        	map.put("msg", "验证成功！");
        	map.put("code", Constant.SUCCESS);
	        return map;
        }
        return map;
	}

}
