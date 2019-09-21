package com.education.hjj.bz.service;

import com.education.hjj.bz.model.UserDto;

/**
 *
 * @author dolyw.com
 * @date 2018/8/9 15:44
 */

public interface IUserService{
	UserDto selectOne(UserDto userDto);
	
	int insertUser(UserDto userDto);
}
