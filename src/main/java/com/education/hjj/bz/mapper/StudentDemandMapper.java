package com.education.hjj.bz.mapper;


import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.entity.vo.TeacherVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import com.education.hjj.bz.formBean.StudentDemandForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDemandMapper {

//	List<StudentDemandVo> queryStudentDemands(StudentDemandPo studentDemandPo);
//
//	StudentDemandVo queryStudentDemandDetail(Integer demandId);
	
	TeacherVo queryTeacherStudentDemands(Integer demandId);
	
	Long addStudentDemandByTeacher(StudentDemandForm form);

	List<StudentDemandVo> listDemand(StudentDemandConnectForm demandForm);
}
