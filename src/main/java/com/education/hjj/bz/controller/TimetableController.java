package com.education.hjj.bz.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.formBean.UserInfoForm;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员课表" })
@RestController
@RequestMapping(value = "/Timetable")
public class TimetableController {

	
	@ApiOperation("查询教员课程信息表")
	@RequestMapping(value = "/queryTimeTableByTeacherId", method = RequestMethod.POST)
	public ApiResponse queryTimeTableByTeacherId(@RequestBody  UserInfoForm userInfoForm) {
		

		return null;
	}
}
