package com.education.hjj.bz.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "学生需求信息" })
@RestController
@RequestMapping(value = "/StudentDemand")
public class StudentDemandsController {

	@Autowired
	private StudentDemandsService studentDemandsService;

	
	@ApiOperation("学生需求信息列表")
	@RequestMapping(value = "/queryStudentDemandsList", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse queryStudentDemandsList(@RequestBody StudentDemandForm studentDemandForm) {

		PageVo<List<StudentDemandVo>> list = studentDemandsService.queryStudentDemands(studentDemandForm);

		return ApiResponse.success(list);
	}
	
	@ApiOperation("学生需求信息详情")
	@RequestMapping(value = "/queryStudentDemandDetail", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse queryStudentDemandDetail(@RequestParam("demandId") String demandId) {

		Map<String, Object> map = studentDemandsService.queryStudentDemandDetail(demandId);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("教员报名学生需求")
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "teacher:edit"})
	@RequestMapping(value = "/addStudentDemandByTeacher", method = RequestMethod.POST)
	public ApiResponse addStudentDemandByTeacher(@RequestParam("demandId")String demandId ,@RequestBody TeacherVo teacher) {
		
		
		int i = studentDemandsService.addStudentDemandByTeacher(demandId , teacher);

		return ApiResponse.success(i);
	}

}
