package com.education.hjj.bz.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.education.hjj.bz.entity.PointsLogPo;
import com.education.hjj.bz.entity.TeacherPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.StudentLogVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.enums.PonitsLog;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.service.PointsLogService;
import com.education.hjj.bz.service.StudentDemandsService;
import com.education.hjj.bz.service.StudentLogService;
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
	
	@Autowired
	private StudentLogService studentLogService;
	
	@Autowired
	private PointsLogService pointsLogService;
	

	@ApiOperation("查询教员登录首页的内容")
	@RequestMapping(value = "/queryTeacherInfosByHome", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse queryTeacherHomeInfos(@RequestBody StudentDemandForm form) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Integer teacherId = form.getTeacherId();
		
		logger.info("teacherId : {}" , teacherId);
		
		List<StudentDemandVo> studentDemandList = null;
		List<StudentDemandVo> fitTeacherOrderTargetList = new ArrayList<StudentDemandVo>();
		if(teacherId != null) {
			
			//教员信息
			TeacherVo teacherVo = userInfoService.queryTeacherHomeInfos(String.valueOf(teacherId));
			
			PointsLogPo pointsLogPo = new PointsLogPo();
			pointsLogPo.setTeacherId(teacherId);
			pointsLogPo.setGetPointsCounts(PonitsLog.OPEN_SYSTEM.getType());
			pointsLogPo.setGetPointsType(0);
			pointsLogPo.setGetPointsDesc(PonitsLog.OPEN_SYSTEM.getValue());
			pointsLogPo.setStatus(1);
			pointsLogPo.setCreateTime(new Date());
			pointsLogPo.setCreateUser(teacherVo.getName());
			pointsLogPo.setUpdateTime(new Date());
			pointsLogPo.setUpdateUser(teacherVo.getName());
			
			logger.info("记录用户登录时获取的积分日志");
			int p = pointsLogService.addTeacherPointsLog(teacherId , pointsLogPo);
			
			if(p < 0 ) {
				logger.info("记录用户登录时获取的积分日志失败...");
			}
			
			//增加教员积分
			TeacherPo teacher = new TeacherPo();
			teacher.setTeacherId(teacherId);
			teacher.setTeacherPoints(teacherVo.getTeacherPoints() + 10);
			teacher.setUpdateTime(new Date());
			teacher.setUpdateUser(teacherVo.getName());
			
			userInfoService.updateUserInfo(teacher);
			
			map.put("teacherLevel", teacherVo.getTeacherLevel());
			map.put("auditStatus", teacherVo.getAuditStatus());
			map.put("logonStatus", teacherVo.getLogonStatus());
			map.put("employRate",  teacherVo.getEmployRate()+"%");
			map.put("resumptionRate", teacherVo.getResumptionRate()+"%");
			
			//查询新的试讲订单列表
			List<StudentDemandVo> newTrialStudentDemandList = studentDemandsService.queryNewTrialOrderList(teacherId);
			map.put("newTrialStudentDemandList", newTrialStudentDemandList);
			
			//查询适合我的家教需求
			List<StudentDemandVo> fitTeacherOrderList = studentDemandsService.queryFitTeacherOrderList();
			
			String teachAddress = teacherVo.getTeachAddress();
			String teachBranch = teacherVo.getTeachBrance() + ","+teacherVo.getTeachBranchSlave();
			String teachGrade = teacherVo.getTeachGrade();
			
			for(StudentDemandVo fsdv:fitTeacherOrderList) {
				
				Integer subject = fsdv.getSubjectId();
				Integer demandGrade = fsdv.getDemandGrade();
				Integer addressId = fsdv.getParameterId();
				
				logger.info("教员教学区域：{}，教学科目：{}，教学年级{}，需求单的教学区域：{}，需求单的教学科目：{}，需求单的教学年级：{}" , teachAddress , teachBranch , teachGrade
						, addressId , subject , demandGrade);
				
				if(teachAddress.contains(String.valueOf(addressId)) && teachBranch.contains(String.valueOf(subject)) 
						&& teachGrade.contains(String.valueOf(demandGrade))) {
					
					fitTeacherOrderTargetList.add(fsdv);
				}
				
			}
			
			if(fitTeacherOrderTargetList.size() > 0) {
				map.put("fitTeacherOrderList", fitTeacherOrderTargetList);
			}else {
				//查询所有的订单信息列表
				 studentDemandList = studentDemandsService.queryAllStudentDemandListBy10(form);
				 map.put("studentDemandList", studentDemandList);
			}
		}else {
			
			//查询所有的订单信息列表
			studentDemandList = studentDemandsService.queryAllStudentDemandListBy10(form);
			
			map.put("studentDemandList", studentDemandList);
		}
		
		//教员端首页查看学员日志
		List<StudentLogVo> StudentLogVoList = studentLogService.queryStudentLogByHome10();
		map.put("StudentLogVoList", StudentLogVoList);
		
		return ApiResponse.success(map);
	}
	
	@ApiOperation("查询所有需求订单的内容")
	@RequestMapping(value = "/queryAllStudentDemandList", method = RequestMethod.POST)
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
		
		logger.info("订单id:{} , 教员id:{}" , sid , teacherId);
		
		//查询学员需求订单详情
		Map<String,Object> map = studentDemandsService.queryStudentDemandDetailBySid(sid , teacherId);
		
		return ApiResponse.success(map);
	}
	
	
}
