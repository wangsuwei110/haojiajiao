package com.education.hjj.bz.mapper;


/**
*
* @author：sys
* @创建时间：2017-8-29 16:27:17
*/
public interface BaseMapper<T> {

    int deleteByPrimaryKey(Long sid);

    int insertSelective(T t);

    T selectByPrimaryKey(Long sid);

    int updateByPrimaryKeySelective(T t);

}