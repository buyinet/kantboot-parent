package com.kantboot.system.auth.web.admin.controller;

import com.kantboot.system.auth.domain.entity.SysAuthPermission;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-auth/admin/permission")
public class SysAuthPermissionControllerOfAdmin extends BaseAdminController<SysAuthPermission,Long> {
}
