package com.kantboot.functional.file.service.impl;


import com.kantboot.functional.file.dao.repository.FunctionalFileGroupRepository;
import com.kantboot.functional.file.domain.entity.FunctionalFileGroup;
import com.kantboot.functional.file.service.IFunctionalFileGroupService;
import com.kantboot.util.cache.CacheUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 文件组管理的Service接口实现类
 * @author 方某方
 */
@Service
public class FunctionalFileGroupServiceImpl implements IFunctionalFileGroupService {

    @Resource
    FunctionalFileGroupRepository repository;

    @Resource
    CacheUtil cacheUtil;

    @Override
    public FunctionalFileGroup getByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public String getPathByCode(String code) {
        // 先从缓存中获取
        // First get from the cache
        String redisKey = "fileGroupId:" + code + ":path";
        String s = cacheUtil.get(redisKey);
        if (s != null) {
            return s;
        }
        // 从数据库中获取
        // Get from the database
        FunctionalFileGroup byCode = repository.findByCode(code);
        if (byCode != null) {
            // 存入缓存
            // Save to cache
            cacheUtil.set(redisKey, byCode.getPath());
            return byCode.getPath();
        }
        return null;
    }

    @Override
    public Boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}
