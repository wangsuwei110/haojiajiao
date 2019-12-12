package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.entity.vo.StudentDemandVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学员需求关联表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-16 0:00:03
 */
@Mapper
public interface StudentDemandConnectMapper {

	StudentDemandConnectVo load(Long id);

	int insert(StudentDemandConnectForm studentDemandConnect);

	void updateNotNull(StudentDemandConnectForm studentDemandConnect);

	@Transactional
	void updateByDemandId(StudentDemandConnectForm studentDemandConnect);

	Long updateStatus(StudentDemandConnectForm studentDemandConnect);

	/** 试讲通过， 其它报名教员状态改为5*/
	Long updateStatusAndPass(StudentDemandConnectForm studentDemandConnect);

	Long confirmTeacher(StudentDemandConnectForm studentDemandConnect);

	void delete(Long id);

    int getCount(StudentDemandConnectForm form);
    
    int querySignUpPersonByDemandId(StudentDemandConnectForm form);

    List<StudentDemandConnectVo> list(StudentDemandConnectForm form);

    Integer countAlreadyDemand(StudentDemandConnectForm form);

    List<StudentDemandConnectVo> listConnectInfo(@Param("demandId") Integer demandId);
    
    int queryServiceForStudentSuccess(Integer teacherId);
    
    List<StudentDemandConnectVo> queryStudentAppraiseForTeacher(Integer teacherId);

    List<StudentDemandConnectVo> listGoodApprise();

    List<StudentDemandVo> queryServiceForStudentByTeacherId(Integer teacherId);
    
    List<StudentDemandVo> queryAllWaitForTrailTimeDemandOrderList(StudentDemandConnectForm studentDemandConnectForm);
}
