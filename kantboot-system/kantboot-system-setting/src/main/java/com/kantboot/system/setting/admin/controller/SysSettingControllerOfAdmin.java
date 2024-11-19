package com.kantboot.system.setting.admin.controller;

import com.kantboot.system.setting.domain.entity.SysSetting;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system-setting/admin/setting")
public class SysSettingControllerOfAdmin extends BaseAdminController<SysSetting,Long> {

}