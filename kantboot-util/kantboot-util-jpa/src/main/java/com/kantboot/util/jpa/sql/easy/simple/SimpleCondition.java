package com.kantboot.util.jpa.sql.easy.simple;


import com.kantboot.util.jpa.sql.easy.entity.ConditionEntity;
import com.kantboot.util.jpa.sql.easy.entity.ConditionGlobeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简单条件实体类
 */
public class SimpleCondition {


    /**
     * 获取对应的条件值
     */
    public static List<ConditionEntity> getConditionEntities(Map<String, Object> conditionMap) {
        List<ConditionEntity> result = new ArrayList<>();
        // 获取conditionMap中所有的key
        Set<String> keys = conditionMap.keySet();
        // 创建ConditionEntity对象
        for (String key : keys) {
            // 分割key
            String[] split = key.split("\\$");
            // index:0为字段名
            String field = split[0];
            // index:1为操作符编码
            String operatorCode = split[1];
            // 创建ConditionEntity对象
            ConditionEntity conditionEntity = new ConditionEntity();
            // 设置属性
            conditionEntity.setField(field);
            conditionEntity.setOperatorCode(operatorCode);
            conditionEntity.setValue(conditionMap.get(key));
            // 添加到结果集
            result.add(conditionEntity);
        }
        return result;
    }

    /**
     * 获取And
     */
    public static ConditionGlobeEntity getAnd(Map<String, Object> conditionMap) {
        ConditionGlobeEntity conditionGlobeEntity = new ConditionGlobeEntity();
        conditionGlobeEntity.setAnd(getConditionEntities(conditionMap));
        return conditionGlobeEntity;
    }

}
