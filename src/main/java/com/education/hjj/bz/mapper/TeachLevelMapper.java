package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.CodeVo;
import com.education.hjj.bz.entity.vo.TeachLevelVo;
import com.education.hjj.bz.formBean.TeachLevelForm;
import com.education.hjj.bz.formBean.TeachScreenForm;
import com.education.hjj.bz.formBean.TeachUniversityForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 教学学段表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-10-13 13:31:41
 */
@Mapper
public interface TeachLevelMapper {

	TeachLevelVo load(Long id);

	void insert(TeachLevelForm teachLevel);

	void updateNotNull(TeachLevelForm teachLevel);

	void delete(Long id);

    int getCount(TeachLevelForm form);

    List<TeachLevelVo> list(TeachLevelForm form);

    List<CodeVo>  listSubject(TeachScreenForm form);

    List<CodeVo>  findAllSubject(TeachScreenForm form);

    CodeVo  findSubject(TeachScreenForm form);

    List<CodeVo>  listUniversity(TeachUniversityForm form);

    List<TeachLevelVo> queryAllTeachLevel();
}
