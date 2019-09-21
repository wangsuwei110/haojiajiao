package com.education.hjj.bz.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.education.hjj.bz.mapper.UserMapper;
import com.education.hjj.bz.model.UserDto;
import com.education.hjj.bz.service.IUserService;

/**
 *
 * @author dolyw.com
 * @date 2018/8/9 15:45
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public UserDto selectOne(UserDto userDto) {
		UserDto user = userMapper.selectOne(userDto);
		return user;
	}

	@Override
	public int insertUser(UserDto userDto) {
		int i = userMapper.insertUser(userDto);
		return i;
	}

	
}
