package com.education.hjj.bz.mapper;


import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Transactional
public interface StudentDemandMapper {

	StudentDemandVo queryStudentDemands(Integer demandId);
	
	StudentDemandVo queryFirstPayInfos(Integer demandId);
//
//	StudentDemandVo queryStudentDemandDetail(Integer demandId);
	
	TeacherVo queryTeacherStudentDemands(Integer demandId);

	int addStudentDemandByTeacher(StudentDemandForm form);

	Long openDemand(StudentDemandConnectForm demandForm);

	List<StudentDemandVo> listDemand(StudentDemandConnectForm demandForm);

	List<StudentDemandVo> listDemandAndTeacher(StudentDemandConnectForm demandForm);

	StudentDemandVo findDemandByCourseId(Integer sid);

	List<StudentDemandVo> queryAllStudentDemandList(StudentDemandForm form);

	String findOriginPrice(StudentDemandForm form);

	List<StudentDemandVo> queryAllStudentDemandListBy10(StudentDemandForm form);
	
	StudentDemandVo queryStudentDemandDetailBySid(Integer sid);

	List<Integer> listTeacherByOldInfo(Integer demandId);

	StudentDemandVo findStudentDemandInfo(StudentDemandForm form);

	@Transactional
	Long updateOldDemandToNew(StudentDemandForm form);

	@Transactional
	Long updateAppraise(StudentDemandConnectForm form);

	Long findMaxSid();
	
	List<StudentDemandVo> queryNewTrialOrderList(Integer teacherId);
	
	List<StudentDemandVo> queryFitTeacherOrderList();
	
	List<StudentDemandVo> queryUserDemandsList(Map<String, Object> map);
	
	StudentDemandVo queryStudemtDemandDetail(StudentDemandConnectForm demandForm);
	
	int updateNewTrialDemandTime(StudentDemandConnectForm demandForm);
	
	int updateNewTrialDemandStatus(StudentDemandForm studentDemandForm);
	
	List<StudentDemandVo> queryTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int updateDemandSignNum(StudentDemandPo studentDemandPo);

	int endDemand(@Param("demandId") Integer demandId);
	
	List<StudentDemandVo> queryStudentDemandDetailSignStatusBySid(Integer sid);
	
	int updateDemandIsResumption(StudentDemandPo studentDemandPo);
	
	int querySignUpPersonBystatus(StudentDemandForm demandForm);
	
	StudentDemandVo findStudentDemandDetail(StudentDemandPo studentDemandPo);	
}
