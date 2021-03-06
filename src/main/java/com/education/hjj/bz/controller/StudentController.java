package com.education.hjj.bz.controller;

import com.education.hjj.bz.formBean.StudentForm;
import com.education.hjj.bz.service.StudentService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学员表控制类
 *
 * @创建者：sys
 * @创建时间：2019-9-29 0:30:25
 */
@Api(tags = { "学员端-学员信息表" })
@RestController
@RequestMapping("student")
public class StudentController {


    @Autowired
    private StudentService studentService;

	/**
	 * 查询
	 *
	 */
	@PostMapping("/findStudent")
	@ResponseBody
	public ApiResponse list(@RequestBody final StudentForm queryForm) {
		return studentService.findBySid(queryForm);
    }


	/**
	 * 修改学员信息
	 *
	 */
   	@PostMapping("/update")
	@ResponseBody
	public ApiResponse save(@RequestBody final StudentForm queryForm){

		studentService.updateNotNull(queryForm);
		return ApiResponse.success("保存成功!");
	}

}
