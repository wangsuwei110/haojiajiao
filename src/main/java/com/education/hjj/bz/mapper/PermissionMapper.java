package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.model.PermissionDto;
import com.education.hjj.bz.model.RoleDto;


/**
 * PermissionMapper
 * @author dolyw.com
 * @date 2018/8/31 14:42
 */
@Mapper
public interface PermissionMapper {
    /**
     * 根据Role查询Permission
     * @param roleDto
     * @return java.util.List<com.wang.model.PermissionDto>
     * @author dolyw.com
     * @date 2018/8/31 11:30
     */
    List<PermissionDto> findPermissionByRole(RoleDto roleDto);
}