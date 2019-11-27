package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.util.ApiResponse;

import java.util.List;
import java.util.Map;

public interface StudentDemandsService {

	PageVo<List<StudentDemandVo>> queryAllStudentDemandList(StudentDemandForm studentDemandForm);
	
	List<StudentDemandVo> queryAllStudentDemandListBy10(StudentDemandForm form);
	
	Map<String,Object> queryStudentDemandDetail(String demandId);

	ApiResponse addStudentDemandByTeacher(StudentDemandForm form);

	ApiResponse listDemand(StudentDemandConnectForm demandForm);

	ApiResponse listTeacher(StudentDemandConnectForm demandForm);

	/**
	 * 确定教员，确定预约时间
	 **/
	ApiResponse confirmTeacher(StudentDemandConnectForm demandForm);

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

	Map<String , Object> queryStudentDemandDetailBySid(Integer sid , Integer teacherId);
	
	List<StudentDemandVo> queryNewTrialOrderList(Integer teacherId);
	
	List<StudentDemandVo> queryUserDemandsList(StudentDemandConnectForm demandForm);
	
	StudentDemandVo queryStudemtDemandDetail(StudentDemandConnectForm demandForm);
	
	List<StudentDemandVo> queryFitTeacherOrderList(Integer teacherId);
	
	int updateNewTrialDemand(StudentDemandConnectForm demandForm);
	
	List<StudentDemandVo> queryTimeTableByTeacherId(StudentDemandConnectForm demandForm);
	
	int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int insert(StudentDemandConnectForm studentDemandConnect);
	
	Map<String , Object> validateSignParameters(StudentDemandConnectForm demandForm);
}
