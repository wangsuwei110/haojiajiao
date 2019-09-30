//package com.education.hjj.bz.controller;
//
//import com.education.hjj.bz.formBean.StudentDetailForm;
//import com.education.hjj.bz.mapper.StudentDetailMapper;
//import com.education.hjj.bz.service.StudentDetailService;
//import com.education.hjj.bz.service.SubjectService;
//import com.education.hjj.bz.util.ApiResponse;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(tags = { "学员端-学员信息" })
//@RestController
//@RequestMapping(value = "/student")
//public class StudentDetailController {
//
//	private static Logger logger = LoggerFactory.getLogger(StudentDetailController.class);
//
//	@Autowired
//	private StudentDetailService studentDetailService;
//
//
//	@ApiOperation("查询所有的课程")
//	@RequestMapping(value = "/list", method = RequestMethod.POST)
//	public ApiResponse selectSubjects(@RequestBody StudentDetailForm form) {
//		return ApiResponse.success(studentDetailService.queryStudentDetail(form));
//
//	}
//}
