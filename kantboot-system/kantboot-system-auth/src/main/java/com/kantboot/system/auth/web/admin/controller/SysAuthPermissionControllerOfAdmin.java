package com.kantboot.system.auth.web.admin.controller;

import com.kantboot.system.auth.domain.entity.SysAuthPermission;
import com.kantboot.system.auth.domain.entity.SysAuthPermissionUri;
import com.kantboot.system.auth.service.ISysAuthPermissionService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system-auth/admin/permission")
public class SysAuthPermissionControllerOfAdmin extends BaseAdminController<SysAuthPermission,Long> {

    @Resource
    private ISysAuthPermissionService service;

    @RequestMapping("/getUriList")
    public RestResult<List<SysAuthPermissionUri>> getUriList(Long permissionId){
        return RestResult.success(service.getUriList(permissionId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
