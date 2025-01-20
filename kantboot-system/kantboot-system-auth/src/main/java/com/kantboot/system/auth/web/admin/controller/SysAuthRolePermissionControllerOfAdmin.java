package com.kantboot.system.auth.web.admin.controller;

import com.kantboot.system.auth.domain.entity.SysAuthRolePermission;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-auth/admin/rolePermission")
public class SysAuthRolePermissionControllerOfAdmin extends BaseAdminController<SysAuthRolePermission,Long> {
}
