package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.service.ISmsService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;

@RestController
@RequestMapping("/smsVerifityCode")
@Api(tags = { "手机验证码" })
public class SmsVerifityCodeController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
    private ISmsService smsService;
	
	/**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/sendMessage" , method = RequestMethod.POST)
    @ApiOperation("发送手机验证码")
    public ApiResponse sendMessage(@RequestBody LoginForm LoginForm) {
    	
    	logger.info("发送验证码手机号：{}",LoginForm.getLoginPhone());
        return ApiResponse.success(smsService.sendMessage(LoginForm.getLoginPhone()));
    }
    
    
    /**
     * 判断验证码是否正确
     *
     * @param mobile
     * @param identifyCode
     * @return
     */
    @RequestMapping(value ="/checkIsCorrectCode" , method = RequestMethod.POST)
    @ApiOperation("校验手机验证码" )
    public ApiResponse checkIsCorrectCode(@RequestParam("telephone") String telephone, @RequestParam("identifyCode") String identifyCode) {
        return ApiResponse.success(smsService.checkIsCorrectCode(telephone, identifyCode));
    }

}
