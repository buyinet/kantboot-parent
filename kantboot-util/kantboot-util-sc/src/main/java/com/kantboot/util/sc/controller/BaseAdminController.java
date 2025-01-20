package com.kantboot.util.sc.controller;

import com.alibaba.fastjson2.JSON;
import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.service.impl.BaseAdminServiceImplInBaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@RestController
public class BaseAdminController<T extends Serializable,ID> {

    @Resource
    private BaseAdminServiceImplInBaseAdminController<T,ID> service;

    /**
     * 获取第一个泛型的类型
     */
    private Class<T> getTClass() {
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            // 强制不检查转换
            // noinspection unchecked
            return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
        return null;
    }

    @PostMapping("/getAll")
    public RestResult<List<T>> getAll(@RequestBody ConditionGlobeEntity param){
        return RestResult.success(service.getAll(param,getTClass()), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @PostMapping("/getBodyData")
    public RestResult<PageResult> getBodyData(@RequestBody PageParam<ConditionGlobeEntity> pageParam){
        return RestResult.success(service.getBodyData(pageParam,getTClass()),CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @PostMapping("/save")
    public RestResult<Object> save(@RequestBody T t){
        return RestResult.success(
                JSON.parseObject(JSON.toJSONString(service.save(t,getTClass())))
                , CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    @PostMapping("/saveBatch")
    public RestResult<T> saveBatch(@RequestBody List<T> tList){
        service.saveBatch(tList,getTClass());
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    @PostMapping("/remove")
    public RestResult<T> remove(@RequestBody T t){
        service.remove(t,getTClass());
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    @PostMapping("/removeBatch")
    public RestResult<T> removeBatch(@RequestBody List<T> tList){
        service.removeBatch(tList,getTClass());
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 根据id获取
     */
    @PostMapping("/getById")
    public RestResult<T> getById(@RequestBody ID id){
        return RestResult.success(service.getById(id,getTClass()),CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
