package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.education.hjj.bz.entity.PermitPo;

import java.util.List;

/**
 * Copyright: Copyright (c) 2018
 *
 * @Description: 该类的功能描述
 * @version: v1.0.0
 * @author: ruyi
 * @date: 2018/11/7 20:43
 * <p>
 * Modification History:
 * Date         Author          Version            Description
 * ---------------------------------------------------------*
 * 2018/11/7     ruyi           v1.0.0               修改原因
 */
@Repository
public interface PermitMapper extends BaseMapper<PermitPo> {
    int deletePermitById(Long sid);

    List<PermitPo> getAll();

    List<PermitPo> getByUserId(@Param("userId") Long userId);

    Long getPermitByCode(@Param("code") String code);

    List<Long> selectSidListByCode(@Param("codeList")List<String> codeList);

    List<PermitPo> selectListBySidList(@Param("idList")List<Long> idList);

    PermitPo getByCode(@Param("code") String code);
}
