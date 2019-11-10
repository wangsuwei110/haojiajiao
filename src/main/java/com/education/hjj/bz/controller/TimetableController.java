package com.education.hjj.bz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.UserInfoForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员课表" })
@RestController
@RequestMapping(value = "/Timetable")
public class TimetableController {
	
	private static Logger logger = LoggerFactory.getLogger(TimetableController.class);

	@Autowired
	private StudentDemandsService studentDemandsService;
	
	@ApiOperation("查询教员课程信息表")
	@RequestMapping(value = "/queryTimeTableByTeacherId", method = RequestMethod.POST)
	public ApiResponse queryTimeTableByTeacherId(@RequestBody  StudentDemandConnectForm demandForm) {
		
		List<StudentDemandVo> list = studentDemandsService.queryTimeTableByTeacherId(demandForm);
		
		return ApiResponse.success(list);
	}
	
	
	@ApiOperation("教员课程信息表--打卡-结课")
	@RequestMapping(value = "/updateTimeTableByTeacherId", method = RequestMethod.POST)
	public ApiResponse updateTimeTableByTeacherId(@RequestBody  StudentDemandPo studentDemandPo) {
		
		int i = studentDemandsService.updateTimeTableByTeacherId(studentDemandPo);
		
		if(i > 0 ) {
			return ApiResponse.success("打卡成功！");
		}
		
		return ApiResponse.success("打卡失败！");
	}
}
