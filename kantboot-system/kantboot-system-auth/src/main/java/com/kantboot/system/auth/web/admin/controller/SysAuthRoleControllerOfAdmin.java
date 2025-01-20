package com.kantboot.system.auth.web.admin.controller;

import com.kantboot.system.auth.domain.entity.SysAuthRole;
import com.kantboot.system.auth.service.ISysAuthRoleService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system-auth/admin/role")
public class SysAuthRoleControllerOfAdmin extends BaseAdminController<SysAuthRole,Long> {

    @Resource
    private ISysAuthRoleService service;

    /**
     * 保存角色权限
     * Save role permission
     *
     * @param roleId       角色id
     *                     role id
     * @param permissionIds 权限id
     *                     permission id
     * @return 保存结果
     * Save result
     */
    @RequestMapping("/newSave")
    public RestResult<Void> newSave(
            @RequestParam("roleId") Long roleId,
            @RequestParam("permissionIds") List<Long> permissionIds) {
        service.newSave(roleId, permissionIds);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }


}
