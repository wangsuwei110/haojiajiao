package com.education.hjj.bz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.model.RoleDto;
import com.education.hjj.bz.model.UserDto;

@Mapper
public interface RoleMapper {

	List<RoleDto> findRoleByUser(UserDto userDto);
}
