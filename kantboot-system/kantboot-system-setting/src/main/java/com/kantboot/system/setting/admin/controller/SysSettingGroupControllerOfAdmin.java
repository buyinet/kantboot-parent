package com.kantboot.system.setting.admin.controller;

import com.kantboot.system.setting.domain.entity.SysSettingGroup;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-setting/admin/settingGroup")
public class SysSettingGroupControllerOfAdmin extends BaseAdminController<SysSettingGroup,Long> {

}
