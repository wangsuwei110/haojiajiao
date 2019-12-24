package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.StudentDemandPo;
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

    List<Integer> findAllApprise();

    List<StudentDemandVo> queryServiceForStudentByTeacherId(Integer teacherId);
    
    //教务端确定待试讲的订单未确定试讲时间超过一小时的订单
    List<StudentDemandVo> queryAllWaitForTrailTimeDemandOrderList(StudentDemandConnectForm studentDemandConnectForm);
    
    //教务端确定待试讲的订单是否联系
    int updateStudentDemandConnectByStatus(StudentDemandConnectForm studentDemandConnectForm);
    
    //查询所有已确认试讲时间但未开始试讲的订单
    List<StudentDemandVo> queryAllTrailDemandOrderListNotBegin(StudentDemandPo studentDemandPo);
    
    //查询所有已下单支付但未开始讲课的订单
    List<StudentDemandVo> queryAllDemandOrderListNotBegin(StudentDemandPo studentDemandPo);
    
    int queryAppraiseCountByTeacherId(StudentDemandConnectForm form);
}
