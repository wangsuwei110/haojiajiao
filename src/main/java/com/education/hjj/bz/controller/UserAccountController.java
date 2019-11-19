package com.education.hjj.bz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.TeacherAccountPo;
import com.education.hjj.bz.entity.vo.TeacherAccountVo;
import com.education.hjj.bz.service.UserAccountService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员账户管理" })
@RestController
@RequestMapping(value = "/userAccount")
public class UserAccountController {
	
	private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	@Autowired
	private UserAccountService userAccountService;
	
	@ApiOperation("教员账户余额查询")
	@RequestMapping(value = "/queryUserAccount", method = RequestMethod.POST)
	public ApiResponse queryUserAccount(@RequestBody TeacherAccountPo teacherAccountPo) {
		
		Integer teacherId = teacherAccountPo.getTeacherId();

		logger.info("teacherId = {}" , teacherId);
		
		TeacherAccountVo teacherAccountVo = userAccountService.queryTeacherAccount(teacherId);
		
		if(teacherAccountVo != null) {
			return ApiResponse.success("操作成功" , JSON.toJSON(teacherAccountVo));
		}

		return ApiResponse.error("暂无数据");
	}
}
