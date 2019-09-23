package com.education.hjj.bz.controller;

import com.education.hjj.bz.service.SubjectService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "科目信息" })
@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
	
	private static Logger logger = LoggerFactory.getLogger(SubjectController.class);

	@Autowired
	private SubjectService subjectService;
	

	@ApiOperation("查询所有的课程")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ApiResponse selectSubjects() {
		return subjectService.findSubjects();

	}
}
