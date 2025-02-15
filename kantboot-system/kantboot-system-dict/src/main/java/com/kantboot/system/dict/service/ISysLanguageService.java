package com.kantboot.system.dict.service;

import com.kantboot.system.dict.domain.entity.SysLanguage;
import com.kantboot.system.dict.domain.entity.SysLanguageLocalized;

import java.util.List;

public interface ISysLanguageService {

    List<SysLanguage> getBySupport();

    List<SysLanguageLocalized> getLocalizedList();

    /**
     * 获取所有语言
     */
    List<SysLanguage> getAll();

}
