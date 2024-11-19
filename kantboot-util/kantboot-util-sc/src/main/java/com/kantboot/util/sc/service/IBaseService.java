package com.kantboot.util.sc.service;



import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;

import java.util.List;

public interface IBaseService<T,ID> {

    /**
     * 通用查询
     * 查询所有
     */
    List<T> getAllHasCondition(ConditionGlobeEntity operatorGlobe);

    /**
     * 通用查询
     * 没有条件
     */
    List<T> getAll();

    /**
     * 通用查询
     * 分页查询
     */
    PageResult getBodyData(PageParam<ConditionGlobeEntity> pageParam);

    /**
     * 添加数据
     */
    T save(T entity);

    /**
     * 批量保存
     */
    void saveBatch(List<T> entityList);


    /**
     * 根据id删除
     */
    void remove(T entity);

    /**
     * 批量删除
     */
    void removeBatch(List<T> entityList);
}
