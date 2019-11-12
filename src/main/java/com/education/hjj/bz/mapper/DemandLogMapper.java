package com.education.hjj.bz.mapper;

import com.education.hjj.bz.entity.vo.DemandLogVo;
import com.education.hjj.bz.formBean.DemandLogForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 需求日志表数据访问接口
 *
 * @创建者：sys
 * @创建时间：2019-11-10 23:32:09
 */
@Mapper
public interface DemandLogMapper {

	DemandLogVo load(Long id);

	void insert(DemandLogForm demandLog);
	
    List<DemandLogVo> list(DemandLogForm form);
}
