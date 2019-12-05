package com.education.hjj.bz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.TeacherAccountOperateLogPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.TeacherAccountOperateLogVo;
import com.education.hjj.bz.formBean.TeacherAccountLogForm;
import com.education.hjj.bz.service.UserAccountLogService;
import com.education.hjj.bz.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "教员收支管理" })
@RestController
@RequestMapping(value = "/userAccountLog")
public class UserAccountLogController {
	
	private static Logger logger = LoggerFactory.getLogger(UserAccountLogController.class);
	
	@Autowired
	private UserAccountLogService userAccountLogService;
	
	@ResponseBody
	@RequestMapping(value = "/queryUserAccountLogList", method = RequestMethod.POST)
	@ApiOperation("教员收支列表查询")
	public ApiResponse queryUserAccountLogList(@RequestBody TeacherAccountLogForm teacherAccountLogForm) {
		
		PageVo p = new PageVo();
		
		Integer teacherId = teacherAccountLogForm.getTeacherId();
		Integer paymentType = teacherAccountLogForm.getPaymentType();
		
		logger.info("教员ID： {} , 收支类型：{}" , teacherId , paymentType);
		
		TeacherAccountOperateLogPo teacherAccountOperateLogPo = new TeacherAccountOperateLogPo();
		teacherAccountOperateLogPo.setPaymentPersonId(teacherId);
		teacherAccountOperateLogPo.setPaymentType(paymentType);
		
		List<TeacherAccountOperateLogVo>  teacherAccountOperateLogVo  = userAccountLogService.queryUserAccountLogList(teacherAccountOperateLogPo);
		
		p.setDataList(teacherAccountOperateLogVo);
		p.setTotal(teacherAccountOperateLogVo.size());
		
		return ApiResponse.success("查询成功", JSON.toJSON(p));
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryUserAccountLogDetail", method = RequestMethod.POST)
	@ApiOperation("教员收支明细查询")
	public ApiResponse queryUserAccountLogDetail(@RequestBody TeacherAccountLogForm teacherAccountLogForm) {
		
		Integer paymentId = teacherAccountLogForm.getPaymentId();
		
		logger.info("收支明细id：{}" , paymentId );
		
		TeacherAccountOperateLogVo  teacherAccountOperateLogVo  = userAccountLogService.queryUserAccountLogDetail(paymentId);
		
		return ApiResponse.success("查询成功", JSON.toJSON(teacherAccountOperateLogVo));
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryUserAccountLogListByEducational", method = RequestMethod.POST)
	@ApiOperation("教员收支明细查询")
	public ApiResponse queryUserAccountLogListByEducational(@RequestBody TeacherAccountLogForm teacherAccountLogForm) {
		
		
		
		List<TeacherAccountOperateLogVo> list  = userAccountLogService.queryUserAccountLogListByEducational(teacherAccountLogForm);
		
		if(list != null && list.size() > 0) {
			return ApiResponse.success("查询成功", JSON.toJSON(list));
		}else {
			return ApiResponse.success("暂无数据！");
		}
		
		
	}
	
	

}
