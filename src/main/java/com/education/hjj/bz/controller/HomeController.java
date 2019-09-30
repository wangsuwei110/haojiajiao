package com.education.hjj.bz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.education.hjj.bz.entity.vo.StudentOrderVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.LoginForm;
import com.education.hjj.bz.formBean.StudentOrderForm;
import com.education.hjj.bz.service.OrderService;
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
	private UserInfoService userInfoService;
	
	@Autowired
	private OrderService orderService;

	@ApiOperation("查询教员登录首页的内容")
	@RequestMapping(value = "/queryTeacherInfosByHome", method = RequestMethod.POST)
	public ApiResponse queryTeacherHomeInfos(@RequestBody LoginForm LoginForm) {
		
		String telephone = LoginForm.getLoginPhone();
		
		Map<String,Object> map = new HashMap<String,Object>();
		//查询出教员首页信息
		TeacherVo teacherVo = userInfoService.queryTeacherInfosByTelephone(telephone);
		map.put("teacherInfo", teacherVo);
		
		//查询所有的订单信息
		StudentOrderForm studentOrderForm = new StudentOrderForm();
		List<StudentOrderVo> orderList = orderService.queryAllOrders(studentOrderForm);
		map.put("orderList", orderList);
		
		//查询试讲订单,订单状态（0新订单，1已报名待试讲订单，2已确认试讲时间，3试讲通过，4试讲未通过，5未支付订单，6已支付订单）
		
		studentOrderForm.setOrderStatus("1");
		studentOrderForm.setTeacherId(teacherVo.getTeacherId().toString());
		List<StudentOrderVo> trailOrderlist =orderService.queryTrailOrdersList(studentOrderForm);
		map.put("trailOrderlist", trailOrderlist);
		
		return ApiResponse.success(map);
	}
}
