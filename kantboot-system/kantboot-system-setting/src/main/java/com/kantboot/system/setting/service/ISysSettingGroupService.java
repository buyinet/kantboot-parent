package com.kantboot.system.setting.service;

import com.kantboot.system.setting.domain.entity.SysSettingGroup;
import com.kantboot.util.sc.service.IBaseService;

/**
 * 系统设置分组服务层接口
 * @author 方某方
 */
public interface ISysSettingGroupService extends IBaseService<SysSettingGroup,Long> {

    SysSettingGroup save(SysSettingGroup entity);

}
