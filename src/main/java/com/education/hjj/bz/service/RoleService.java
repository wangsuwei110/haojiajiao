package com.education.hjj.bz.service;

import java.util.List;

import com.education.hjj.bz.model.RoleDto;
import com.education.hjj.bz.model.UserDto;

public interface RoleService {

	List<RoleDto> findRoleByUser(UserDto userDto);
}
