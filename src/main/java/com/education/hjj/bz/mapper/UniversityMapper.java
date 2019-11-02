package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.UniversityVo;
import com.education.hjj.bz.formBean.UniversityForm;

import java.util.List;

/**
 * 大学表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-11-2 10:31:18
 */
public interface UniversityMapper {

	UniversityVo load(Long id);

	void insert(UniversityForm university);
	
	void updateNotNull(UniversityForm university);

	void delete(Long id);

    int getCount(UniversityForm form);

    List<UniversityVo> list(UniversityForm form);
}
