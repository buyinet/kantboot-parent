package com.kantboot.system.auth.service;

import com.kantboot.system.auth.domain.entity.SysAuthPermissionUri;

import java.util.List;

public interface ISysAuthPermissionService  {

    /**
     * 获取URI的List
     */
    List<SysAuthPermissionUri> getUriList(Long permissionId);

}
