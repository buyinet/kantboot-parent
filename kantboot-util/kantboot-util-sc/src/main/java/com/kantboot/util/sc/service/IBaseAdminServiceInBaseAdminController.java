package com.kantboot.util.sc.service;



import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;

import java.io.Serializable;
import java.util.List;

public interface IBaseAdminServiceInBaseAdminController<T extends Serializable,ID> {

    /**
     * 通用查询
     * 查询所有
     */
    List<T> getAll(ConditionGlobeEntity operatorGlobe, Class<T> tClass);

    /**
     * 通用查询
     * 分页查询
     */
    PageResult getBodyData(PageParam<ConditionGlobeEntity> pageParam, Class<T> tClass);

    /**
     * 添加数据
     */
    T save(T entity,Class<T> tClass);

    /**
     * 批量保存
     */
    void saveBatch(List<T> entityList,Class<T> tClass);


    /**
     * 根据id删除
     */
    void remove(T entity,Class<T> tClass);

    /**
     * 批量删除
     */
    void removeBatch(List<T> entityList,Class<T> tClass);

    /**
     * 根据id获取
     */
    T getById(ID id,Class<T> tClass);

}
