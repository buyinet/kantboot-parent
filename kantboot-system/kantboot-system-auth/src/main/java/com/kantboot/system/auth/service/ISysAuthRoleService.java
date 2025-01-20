package com.kantboot.system.auth.service;


import java.util.List;

public interface ISysAuthRoleService {

    /**
     * newSave
     */
    void newSave(Long roleId, List<Long> permissionIds);

}
