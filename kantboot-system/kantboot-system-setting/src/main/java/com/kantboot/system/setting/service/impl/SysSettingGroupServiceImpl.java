package com.kantboot.system.setting.service.impl;

import com.kantboot.system.setting.domain.entity.SysSettingGroup;
import com.kantboot.system.setting.exception.SystemSettingException;
import com.kantboot.system.setting.repository.SysSettingGroupRepository;
import com.kantboot.system.setting.service.ISysSettingGroupService;
import com.kantboot.util.sc.service.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 系统设置分组服务层实现类
 *
 * @author 方某方
 */
@Service
public class SysSettingGroupServiceImpl
        extends BaseServiceImpl<SysSettingGroup, Long>
        implements ISysSettingGroupService {

    @Resource
    private SysSettingGroupRepository repository;

    @Override
    public SysSettingGroup save(SysSettingGroup entity) {
        // 查询是否有相同的分组编码
        if (entity.getId() == null) {
            SysSettingGroup byCode = repository.findByCode(entity.getCode());
            if (byCode != null) {
                // 提示分组编码已存在
                throw SystemSettingException.GROUP_CODE_EXIST;
            }
            return repository.save(entity);
        }

        // 根据id查询
        SysSettingGroup byId = repository.findById(entity.getId()).orElse(null);
        if (byId == null) {
            // 提示分组不存在
            throw SystemSettingException.GROUP_NOT_EXIST;
        }

        // 如果code不为空,且传来的code和数据库中的code不一样
        if (entity.getCode() != null && !entity.getCode().equals(byId.getCode())) {
            // 查询是否有相同的分组编码
            SysSettingGroup byCode = repository.findByCode(entity.getCode());
            if (byCode != null) {
                // 提示分组编码已存在
                throw SystemSettingException.GROUP_CODE_EXIST;
            }
        }

        return repository.save(entity);
    }
}
