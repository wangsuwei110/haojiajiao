package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.StudentDemandConnectVo;
import com.education.hjj.bz.formBean.StudentDemandConnectForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	void insert(StudentDemandConnectForm studentDemandConnect);

	void updateNotNull(StudentDemandConnectForm studentDemandConnect);

	void delete(Long id);

    int getCount(StudentDemandConnectForm form);

    List<StudentDemandConnectVo> list(StudentDemandConnectForm form);

    List<StudentDemandConnectVo> listConnectInfo(@Param("demandId") Integer demandId);
}
