package com.education.hjj.bz.service;

import com.education.hjj.bz.entity.vo.PageVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import com.education.hjj.bz.util.ApiResponse;

import java.util.List;
import java.util.Map;

public interface StudentDemandsService {

	PageVo<List<StudentDemandVo>> queryStudentDemands(StudentDemandForm studentDemandForm);
	
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
	 * 开放订单
	 **/
	ApiResponse openDemand(StudentDemandConnectForm demandForm);
}
