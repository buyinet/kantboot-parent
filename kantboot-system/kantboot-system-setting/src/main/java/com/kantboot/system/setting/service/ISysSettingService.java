package com.kantboot.system.setting.service;

import com.kantboot.system.setting.domain.entity.SysSetting;

import java.util.HashMap;

/**
 * 系统设置分组服务层接口
 *
 * @author 方某方
 */
public interface ISysSettingService {

    /**
     * 根据分组获取所有设置，以map形式返回
     *
     * @param groupCode 分组编码
     * @return 所有设置
     */
    HashMap<String, String> getMapByGroupCode(String groupCode);

    /**
     * 根据分组和编码获取值
     * 修改成了将code设成唯一索引，不再需要groupCode
     *
     * @param groupCode 分组编码
     * @param code      编码
     * @return 设置
     */
    @Deprecated
    String getValue(String groupCode, String code);

    /**
     * 根据编码获取值
     *
     * @param code 编码
     * @return 设置
     */
    String getValue(String code);

    /**
     * 根據编码获取值（不抛出异常）
     * @param code 编码
     *             Code
     * @return 设置
     */
    String getValueNoException(String code);

    SysSetting save(SysSetting sysSetting);

    /**
     * 设置值
     *
     * @param groupCode 分组编码
     * @param code      编码
     * @param value     值
     */
    void setValue(String groupCode, String code, String value);

    /**
     * 设置值
     *
     * @param code  编码
     * @param value 值
     */
    void setValue(String code, String value);
}
