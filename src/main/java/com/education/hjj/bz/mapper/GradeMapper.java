package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.GradeVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 年级常量表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-6 0:20:11
 */
@Mapper
public interface GradeMapper {

	List<GradeVo> listAll();
}
