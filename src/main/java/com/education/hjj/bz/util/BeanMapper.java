package com.education.hjj.bz.util;


import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 复制对象属性的工具类.
 * 
 * @author elvin
 */
public class BeanMapper {

	private static MapperFacade mapper;

	private static class BeanMapperInstance {
		private static final BeanMapper instance = new BeanMapper();
	}

	private BeanMapper() {
		super();
		initMapper();
	}

	public static BeanMapper getInstance() {
		return BeanMapperInstance.instance;
	}

	private static void initMapper() {
		// 如果src中属性为null，就不复制到dest
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
		// 如果属性是Object，就只复制引用，不复制值，可以避免循环复制
		mapperFactory.getConverterFactory().registerConverter(new PassThroughConverter(Object.class));
		mapper = mapperFactory.getMapperFacade();
	}

	/**
	 * 把src中的值复制到dest中.
	 */
	public <S, D> D copy(S src, D dest) {
		if (src == null) {
			return null;
		}
		mapper.map(src, dest);

		return dest;
	}

	public <S, D> List<D> copyList(List<S> src, Class<D> clz) {
		if (src == null) {
			return null;
		}
		return mapper.mapAsList(src, clz);
	}

}
