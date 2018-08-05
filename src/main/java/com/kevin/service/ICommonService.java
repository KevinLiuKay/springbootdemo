package com.kevin.service;

import java.util.List;

/**
 * 所有的service接口都需要继承这个接口，提供了5个基础方法
 * @author lzk
 * @param <T>
 */
public interface ICommonService<T> {

    /**
     * 保存
     * @param record
     * @return
     */
    int save(T record);
    
    /**
     * 逻辑删除
     * @param id
     * @return
     */
    int logicallyDeleteById(String id);
    
    /**
     * 物理删除
     * @param id
     * @return
     */
    int deleteById(String id);


    /**
     * 查询基础列表
     * @param t
     * @param orderByClause
     * @return
     */
    List<T> queryList(T t, String orderByClause);
    
    /**
     * 根据主键获取一条记录
     * @param id
     * @return
     */
    T getById(String id);
    
}
