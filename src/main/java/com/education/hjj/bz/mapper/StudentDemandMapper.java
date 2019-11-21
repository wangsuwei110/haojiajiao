package com.education.hjj.bz.mapper;


import com.education.hjj.bz.entity.StudentDemandPo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentDemandMapper {

//	List<StudentDemandVo> queryStudentDemands(StudentDemandPo studentDemandPo);
//
//	StudentDemandVo queryStudentDemandDetail(Integer demandId);
	
	TeacherVo queryTeacherStudentDemands(Integer demandId);
	
	Long addStudentDemandByTeacher(StudentDemandForm form);

	Long openDemand(StudentDemandConnectForm demandForm);

	List<StudentDemandVo> listDemand(StudentDemandConnectForm demandForm);

	List<StudentDemandVo> listDemandAndTeacher(StudentDemandConnectForm demandForm);
	
	List<StudentDemandVo> queryAllStudentDemandList(StudentDemandForm form);
	
	List<StudentDemandVo> queryAllStudentDemandListBy10(StudentDemandForm form);
	
	StudentDemandVo queryStudentDemandDetailBySid(Integer sid);

	StudentDemandVo findStudentDemandInfo(Integer demandId);

	Long updateOldDemandToNew(StudentDemandForm form);

	Long findMaxSid();
	
	List<StudentDemandVo> queryNewTrialOrderList(Integer teacherId);
	
	List<StudentDemandVo> queryFitTeacherOrderList(Integer teacherId);
	
	List<StudentDemandVo> queryUserDemandsList(Map<String, Object> map);
	
	StudentDemandVo queryStudemtDemandDetail(StudentDemandConnectForm demandForm);
	
	int updateNewTrialDemandTime(StudentDemandConnectForm demandForm);
	
	int updateNewTrialDemandStatus(StudentDemandForm studentDemandForm);
	
	List<StudentDemandVo> queryTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int updateTimeTableByTeacherId(StudentDemandPo studentDemandPo);
	
	int updateDemandSignNum(StudentDemandPo studentDemandPo);
}
