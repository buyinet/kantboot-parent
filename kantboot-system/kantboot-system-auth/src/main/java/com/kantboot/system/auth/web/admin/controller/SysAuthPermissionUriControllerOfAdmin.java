package com.kantboot.system.auth.web.admin.controller;

import com.kantboot.system.auth.domain.entity.SysAuthPermissionUri;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-auth/admin/permissionUri")
public class SysAuthPermissionUriControllerOfAdmin extends BaseAdminController<SysAuthPermissionUri,Long> {

}
