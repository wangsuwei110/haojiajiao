package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.DemandCourseInfoVo;
import com.education.hjj.bz.formBean.DemandCourseInfoForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程详情表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-11-10 2:02:14
 */
@Mapper
public interface DemandCourseInfoMapper {

	DemandCourseInfoVo load(Long id);

	void insert(@Param("demandCourseInfos") List<DemandCourseInfoForm> demandCourseInfos);

	void updateNotNull(DemandCourseInfoForm demandCourseInfo);

	void delete(Long id);

    int getCount(DemandCourseInfoForm form);

    List<DemandCourseInfoVo> list(DemandCourseInfoForm form);

    List<DemandCourseInfoVo> listMyCourseList(DemandCourseInfoForm form);
    
    int queryServiceForHours(Integer teacherId);
}
