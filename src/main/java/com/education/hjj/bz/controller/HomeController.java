package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "首页" })
@RestController
@RequestMapping(value = "/home")
public class HomeController {
	
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private StudentDemandsService studentDemandsService;
	
	@Autowired
	private UserInfoService userInfoService;
	

	@ApiOperation("查询教员登录首页的内容")
	@RequestMapping(value = "/queryTeacherInfosByHome", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherHomeInfos(@RequestBody StudentDemandForm form) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Integer teacherId = form.getTeacherId();
		
		//教员信息
		TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
		map.put("teacherLevel", teacherVo.getTeacherLevel());
		map.put("employRate",  teacherVo.getEmployRate()+"%");
		map.put("resumptionRate", teacherVo.getResumptionRate()+"%");
		
		//查询新的试讲订单列表
		List<StudentDemandVo> newTrialStudentDemandList = studentDemandsService.queryNewTrialOrderList(teacherId);
		map.put("newTrialStudentDemandList", newTrialStudentDemandList);
		
		//查询适合我的家教需求
		List<StudentDemandVo> fitTeacherOrderList = studentDemandsService.queryFitTeacherOrderList(teacherId);
		
		if(fitTeacherOrderList.size() > 0) {
			map.put("fitTeacherOrderList", fitTeacherOrderList);
		}else {
			//查询所有的订单信息列表
			List<StudentDemandVo> studentDemandList = studentDemandsService.queryAllStudentDemandListBy10(form);
			map.put("studentDemandList", studentDemandList);
		}
		
		return ApiResponse.success(map);
	}
	
	@ApiOperation("查询所有需求订单的内容")
	@RequestMapping(value = "/queryAllStudentDemandListBy10", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryAllStudentDemandList(@RequestBody StudentDemandForm form) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//查询所有的订单信息列表
		PageVo<List<StudentDemandVo>> studentDemandList = studentDemandsService.queryAllStudentDemandList(form);
		map.put("studentDemandList", studentDemandList);
		
		return ApiResponse.success(map);
	}
	
	@ApiOperation("查询学员需求订单详情")
	@RequestMapping(value = "/queryStudentDemandSignUpTeacher", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryStudentDemandSignUpTeacher(@RequestBody StudentDemandConnectForm form) {
		
		Integer sid = form.getDemandId();
		
		Integer teacherId = form.getTeacherId();
		
		//查询学员需求订单详情
		Map<String,Object> map = studentDemandsService.queryStudentDemandDetailBySid(sid , teacherId);
		
		return ApiResponse.success(map);
	}
	
	
}
