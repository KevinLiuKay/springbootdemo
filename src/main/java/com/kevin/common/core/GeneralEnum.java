package com.kevin.common.core;

/**
 * 用于枚举需要实现的接口
 * @author lzk
 */
public interface GeneralEnum<K> {

	/**
	 * 得到枚举对应的id,一般保存这个id至数据库
	 * @return
	 */
	public K getId();

	/**
	 * 得到枚举描述
	 * @return
	 */
	public String getName();

	/**
	 * 枚举名称
	 * @return
	 */
	public String name();

}
