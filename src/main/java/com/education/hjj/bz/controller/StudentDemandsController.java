package com.education.hjj.bz.controller;

import com.alibaba.fastjson.JSON;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

@Api(tags = { "学员端-学生需求信息" })
@RestController
@RequestMapping(value = "/StudentDemand")
public class StudentDemandsController {

	private static Logger logger = LoggerFactory.getLogger(StudentDemandsController.class);

	@Autowired
	private StudentDemandsService studentDemandsService;

	
	@ApiOperation("学生需求信息详情")
	@RequestMapping(value = "/queryStudentDemandDetail", method = RequestMethod.GET)
	public ApiResponse queryStudentDemandDetail(@RequestParam("demandId") Integer demandId) {

		return ApiResponse.success(studentDemandsService.queryStudentDemandDetail(demandId));
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
	@Transactional
	public ApiResponse confirmTeacher(@RequestBody StudentDemandConnectForm demandForm) throws ParseException {
		return studentDemandsService.confirmTeacher(demandForm);
	}

	@ApiOperation("试讲不通过")
	@RequestMapping(value = "/updateAdoptStatus", method = RequestMethod.POST)
	@Transactional
	public ApiResponse updateAdoptStatus(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.updateAdoptStatus(demandForm);
	}

	@ApiOperation("支付/续课")
	@RequestMapping(value = "/payDemand", method = RequestMethod.POST)
	@Transactional
	public ApiResponse payDemand(@RequestBody StudentDemandForm demandForm) {
		return studentDemandsService.payDemand(demandForm);
	}

    @ApiOperation("续课时获取订单的初始教员单价")
    @RequestMapping(value = "/continuePay", method = RequestMethod.POST)
    @Transactional
    public ApiResponse continuePay(@RequestBody StudentDemandForm demandForm) {
        return studentDemandsService.continuePay(demandForm);
    }

	@ApiOperation("结课")
	@RequestMapping(value = "/conclusion", method = RequestMethod.POST)
	@Transactional
	public ApiResponse conclusion(@RequestBody DemandCourseInfoForm demandForm) {

		logger.info("begin conclusion class.....");
		return studentDemandsService.conclusion(demandForm);
	}

	@ApiOperation("我的课程")
	@RequestMapping(value = "/listMyCourse", method = RequestMethod.POST)
	public ApiResponse listMyCourse(@RequestBody DemandCourseInfoForm courseInfoForm) {
		return studentDemandsService.listMyCourse(courseInfoForm);
	}

	@ApiOperation("开放订单")
	@RequestMapping(value = "/openDemand", method = RequestMethod.POST)
	public ApiResponse openDemand(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.openDemand(demandForm);
	}

    @ApiOperation("支付记录")
    @RequestMapping(value = "/payLog", method = RequestMethod.POST)
    public ApiResponse payLog(@RequestBody StudentDemandConnectForm demandForm) {
        return studentDemandsService.payLog(demandForm);
    }

	@ApiOperation("结束订单")
	@RequestMapping(value = "/endDemand", method = RequestMethod.POST)
	public ApiResponse endDemand(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.endDemand(demandForm);
	}

	@ApiOperation("教员确定订单试讲时间")
	@RequestMapping(value = "/updateNewTrialDemand", method = RequestMethod.POST)
	@Transactional
	public ApiResponse updateNewTrialDemand(@RequestBody StudentDemandConnectForm demandForm) {
		
		int i = studentDemandsService.updateNewTrialDemand(demandForm);
		
		logger.info("教员确定订单试讲时间,试讲时间：{}" , JSON.toJSONString(demandForm.getTimeList().get(0).getTime()));
		
		if(i > 0) {
			return ApiResponse.success("更新成功!");
		}else {
			return ApiResponse.error("更新失败！");
		}
	}

	@ApiOperation("主页信息")
	@RequestMapping(value = "/homepageInfo", method = RequestMethod.POST)
	public ApiResponse homepageInfo() {
		return studentDemandsService.homepageInfo();
	}

	@ApiOperation("订单评价")
	@RequestMapping(value = "/appraise", method = RequestMethod.POST)
	public ApiResponse appraise(@RequestBody StudentDemandConnectForm demandForm) {
		return studentDemandsService.appraise(demandForm);
	}
	
	@ApiOperation("教务端教员未确定订单试讲时间超过一小时订单列表")
	@RequestMapping(value = "/queryAllNewTrialDemandTimeOut", method = RequestMethod.POST)
	@Transactional
	public ApiResponse queryAllNewTrialDemandTimeOut(@RequestBody StudentDemandConnectForm studentDemandConnectForm) {
		
		List<StudentDemandVo> list = studentDemandsService.queryAllWaitForTrailTimeDemandOrderList(studentDemandConnectForm);
		
		studentDemandConnectForm.setPageSize(Integer.MAX_VALUE);
		
		List<StudentDemandVo> listCount = studentDemandsService.queryAllWaitForTrailTimeDemandOrderList(studentDemandConnectForm);
		
		PageVo pageVo = new PageVo();
		
		pageVo.setDataList(list);
		pageVo.setTotal(list.size());
		
		Map<String , Object> map = new HashMap<>();
		map.put("dataInfo", pageVo);
		map.put("count", listCount.size());
		
		if(list.size() > 0) {
			return ApiResponse.success("查询成功!" , JSON.toJSON(map));
		}else {
			return ApiResponse.success("暂无数据！");
		}
	}
	
	@ApiOperation("教务端确定待试讲的订单是否联系")
	@RequestMapping(value = "/updateStudentDemandConnectByStatus", method = RequestMethod.POST)
	@Transactional
	public ApiResponse updateStudentDemandConnectByStatus(@RequestBody StudentDemandConnectForm studentDemandConnectForm) {
		
		int i = studentDemandsService.updateStudentDemandConnectByStatus(studentDemandConnectForm);
		
		if(i > 0) {
			return ApiResponse.success("操作成功!");
		}else {
			return ApiResponse.error("操作失败！");
		}
	}

	@ApiOperation("最新发布的好评")
	@RequestMapping(value = "/queryGoodApprise", method = RequestMethod.POST)
	@Transactional
	public ApiResponse queryGoodApprise() {

		return ApiResponse.success(studentDemandsService.queryGoodApprise());
	}
}
