package com.education.hjj.bz.controller;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = { "学员端-学生需求信息" })
@RestController
@RequestMapping(value = "/StudentDemand")
public class StudentDemandsController {

	@Autowired
	private StudentDemandsService studentDemandsService;

	
	@ApiOperation("学生需求信息详情")
	@RequestMapping(value = "/queryStudentDemandDetail", method = RequestMethod.GET)
	@RequiresPermissions(logical = Logical.AND, value = {"teacher:view" , "student:view"})
	public ApiResponse queryStudentDemandDetail(@RequestParam("demandId") String demandId) {

		Map<String, Object> map = studentDemandsService.queryStudentDemandDetail(demandId);

		return ApiResponse.success(map);
	}
	
	@ApiOperation("学员发布需求信息")
	@RequestMapping(value = "/addStudentDemand", method = RequestMethod.POST)
	public ApiResponse addStudentDemandByTeacher(@RequestBody StudentDemandForm demandForm) {
		return studentDemandsService.addStudentDemandByTeacher(demandForm);
	}

	@ApiOperation("服务订单列表信息")
	@RequestMapping(value = "/demandList", method = RequestMethod.POST)
	public ApiResponse listDemand(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.listDemand(demandForm);
	}

	@ApiOperation("预约教员列表查询")
	@RequestMapping(value = "/listTeacher", method = RequestMethod.POST)
	public ApiResponse listTeacher(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.listTeacher(demandForm);
	}

	@ApiOperation("确定预约教员与时间")
	@RequestMapping(value = "/confirmTeacher", method = RequestMethod.POST)
	public ApiResponse confirmTeacher(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.confirmTeacher(demandForm);
	}

	@ApiOperation("试讲通过或者不通过")
	@RequestMapping(value = "/updateAdoptStatus", method = RequestMethod.POST)
	public ApiResponse updateAdoptStatus(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.updateAdoptStatus(demandForm);
	}

	@ApiOperation("开放订单")
	@RequestMapping(value = "/openDemand", method = RequestMethod.POST)
	public ApiResponse openDemand(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.openDemand(demandForm);
	}
	
	@ApiOperation("教员确定订单试讲时间")
	@RequestMapping(value = "/updateNewTrialDemand", method = RequestMethod.POST)
	public ApiResponse updateNewTrialDemand(@RequestBody StudentDemandConnectForm demandForm) {
		
		int i = studentDemandsService.updateNewTrialDemand(demandForm);
		
		if(i > 0) {
			return ApiResponse.success("更新成功!");
		}else {
			return ApiResponse.error("更新失败！");
		}
		
		 
	}
	
}
