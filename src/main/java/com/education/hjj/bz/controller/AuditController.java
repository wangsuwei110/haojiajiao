package com.education.hjj.bz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.education.hjj.bz.formBean.TeacherInfoForm;
import com.education.hjj.bz.service.UserInfoService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员信息审核" })
@RestController
@RequestMapping(value = "/audit")
public class AuditController {

	@Autowired
	private UserInfoService userInfoService;

	@ApiOperation("身份证认证")
	@RequestMapping(value = "/auditIdCard", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"audit:edit","audit:view"})
	public ApiResponse auditIdCard(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		
		MultipartHttpServletRequest req =(MultipartHttpServletRequest)request; 
		MultipartFile multipartFiles = req.getFile("file");
		
		String logonStatus=request.getParameter("logonStatus");//取出form-data中a的值
		String idCard=request.getParameter("idCard");//取出form-data中a的值
		String teacherName=request.getParameter("teacherName");//取出form-data中a的值
		String teacherId = request.getParameter("teacherId");//取出form-data中a的值

		TeacherInfoForm teacherInfoForm = new TeacherInfoForm();
		teacherInfoForm.setTeacherId(teacherId);
		teacherInfoForm.setName(teacherName);
		teacherInfoForm.setLogonStatus(logonStatus);
		teacherInfoForm.setIdCard(idCard);
		
		
		int i = userInfoService.auditUserIdCardInfo(teacherInfoForm , multipartFiles);

		return ApiResponse.success(i);
	}
	
	
	@ApiOperation("教员信息审核")
	@RequestMapping(value = "/auditTeacherInfo", method = RequestMethod.POST)
	@RequiresPermissions(logical = Logical.AND, value = {"audit:edit","audit:view"})
	public ApiResponse auditTeacherInfo(@RequestBody TeacherInfoForm teacherInfoForm) {

		int i = userInfoService.auditTeacherInfo(teacherInfoForm);

		return ApiResponse.success(i);
	}

}
