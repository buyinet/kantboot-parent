package com.kantboot.system.auth.service.impl;

import com.kantboot.system.auth.dao.repository.SysAuthRolePermissionRepository;
import com.kantboot.system.auth.domain.entity.SysAuthRolePermission;
import com.kantboot.system.auth.service.ISysAuthRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysAuthRoleServiceImpl implements ISysAuthRoleService {

    @Resource
    private SysAuthRolePermissionRepository rolePermissionRepository;

    @Override
    public void newSave(Long roleId, List<Long> permissionIds) {
        // 删除原有权限
        rolePermissionRepository.deleteByRoleId(roleId);
        // 保存新权限
        List< SysAuthRolePermission > rolePermissionList = new ArrayList<>();
        for (Long pid : permissionIds) {
            SysAuthRolePermission rolePermission = new SysAuthRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pid);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionRepository.saveAll(rolePermissionList);
    }
}