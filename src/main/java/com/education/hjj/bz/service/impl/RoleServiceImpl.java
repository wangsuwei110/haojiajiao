package com.education.hjj.bz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.mapper.RoleMapper;
import com.education.hjj.bz.model.RoleDto;
import com.education.hjj.bz.model.UserDto;
import com.education.hjj.bz.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<RoleDto> findRoleByUser(UserDto userDto) {
		
		List<RoleDto> list = roleMapper.findRoleByUser(userDto);
		
		return list;
	}

}
