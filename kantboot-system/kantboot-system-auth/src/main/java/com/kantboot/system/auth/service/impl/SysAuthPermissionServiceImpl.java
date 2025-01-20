package com.kantboot.system.auth.service.impl;

import com.kantboot.system.auth.dao.repository.SysAuthPermissionUriRepository;
import com.kantboot.system.auth.domain.entity.SysAuthPermissionUri;
import com.kantboot.system.auth.service.ISysAuthPermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAuthPermissionServiceImpl implements ISysAuthPermissionService {

    @Resource
    private SysAuthPermissionUriRepository uriRepository;

    @Override
    public List<SysAuthPermissionUri> getUriList(Long permissionId) {
        return uriRepository.findByPermissionId(permissionId);
    }

}
