package com.kantboot.util.data.change.service.impl;

import cn.hutool.core.util.IdUtil;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.data.change.service.IDataChangeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DataChangeServiceImpl implements IDataChangeService {

    @Resource
    private CacheUtil cacheUtil;


    @Override
    public void dataChange(String key) {
        if (!"".equals(key)) {
            String uuid = IdUtil.simpleUUID();
            cacheUtil.set("ChangeData:" + key, uuid);
        }
    }

    @Override
    public void dataChanges(String[] keys) {
        String uuid = IdUtil.simpleUUID();
        for (String key : keys) {
            cacheUtil.set("ChangeData:" + key, uuid);
        }
    }

    @Override
    public String getUuidByKey(String key) {
        String uuid = cacheUtil.get("ChangeData:" + key);
        if(uuid==null){
            uuid = IdUtil.simpleUUID();
            cacheUtil.set("ChangeData:" + key, uuid);
        }
        return uuid;
    }
}
