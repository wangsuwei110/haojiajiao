package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.util.ApiResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface StudentDemandsService {

	PageVo<List<StudentDemandVo>> queryAllStudentDemandList(StudentDemandForm studentDemandForm);
	
	List<StudentDemandVo> queryAllStudentDemandListBy10(StudentDemandForm form);

	StudentDemandVo queryStudentDemandDetail(Integer demandId);

	ApiResponse addStudentDemandByTeacher(StudentDemandForm form);

	ApiResponse listDemand(StudentDemandConnectForm demandForm);

	ApiResponse listTeacher(StudentDemandConnectForm demandForm);

	/**
	 * 确定教员，确定预约时间
	 **/
	ApiResponse confirmTeacher(StudentDemandConnectForm demandForm) throws ParseException;

	/**
	 * 试讲通过或不通过
	 **/
	ApiResponse updateAdoptStatus(StudentDemandConnectForm demandForm);

	/**
	 * 支付或续课
	 **/
	ApiResponse payDemand(StudentDemandForm demandForm);

	/**
	 * 结课
	 **/
	ApiResponse conclusion(DemandCourseInfoForm courseInfoForm);

	/**
	 * 查询当前学员的当前周的课程
	 **/
	ApiResponse listMyCourse(DemandCourseInfoForm demandForm);

	/**
	 * 开放订单
	 **/
	ApiResponse openDemand(StudentDemandConnectForm demandForm);

	/**
	 * 支付记录
	 **/
	ApiResponse payLog(StudentDemandConnectForm demandForm);

	/**
	 * 结束订单
	 **/
	ApiResponse endDemand(StudentDemandConnectForm demandForm);

	/**
	 * 主页信息
	 **/
	ApiResponse homepageInfo();

	/**
	 * 主页信息
	 **/
	ApiResponse appraise(StudentDemandConnectForm demandForm);

	Map<String , Object> queryStudentDemandDetailBySid(Integer sid , Integer teacherId);
	
	List<StudentDemandVo> queryNewTrialOrderList(Integer teacherId);
	
	List<StudentDemandVo> queryUserDemandsList(StudentDemandConnectForm demandForm);
	
	StudentDemandVo queryStudemtDemandDetail(StudentDemandConnectForm demandForm);
	
	List<StudentDemandVo> queryFitTeacherOrderList();
	
	int updateNewTrialDemand(StudentDemandConnectForm demandForm);
	
	List<StudentDemandVo> queryTimeTableByTeacherId(StudentDemandConnectForm demandForm);
	
	int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int insert(StudentDemandConnectForm studentDemandConnect);
	
	Map<String , Object> validateSignParameters(StudentDemandConnectForm demandForm);
	
	//教务端确定待试讲的订单未确定试讲时间超过一小时的订单
	List<StudentDemandVo> queryAllWaitForTrailTimeDemandOrderList(StudentDemandConnectForm studentDemandConnectForm);

	ApiResponse queryGoodApprise();
	
	//教务端确定待试讲的订单是否联系
    int updateStudentDemandConnectByStatus(StudentDemandConnectForm studentDemandConnectForm);
}
