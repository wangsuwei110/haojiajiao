package com.education.hjj.bz.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.education.hjj.bz.model.UserDto;

@Mapper
public interface UserMapper {

	UserDto selectOne(UserDto userDto);
	
	int insertUser(UserDto userDto);
}
