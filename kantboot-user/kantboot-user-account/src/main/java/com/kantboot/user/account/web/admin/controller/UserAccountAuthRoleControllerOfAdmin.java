package com.kantboot.user.account.web.admin.controller;

import com.kantboot.user.account.domain.entity.UserAccountAuthRole;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-account-web/admin/userAccountAuthRole")
public class UserAccountAuthRoleControllerOfAdmin extends BaseAdminController<UserAccountAuthRole,Long> {

    // TODO: 终端，差一个newSave方法

}
