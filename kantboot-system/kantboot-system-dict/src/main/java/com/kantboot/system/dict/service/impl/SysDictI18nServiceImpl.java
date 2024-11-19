package com.kantboot.system.dict.service.impl;

import com.kantboot.system.dict.dao.repository.SysDictI18nRepository;
import com.kantboot.system.dict.dao.repository.SysDictRepository;
import com.kantboot.system.dict.domain.entity.SysDictI18n;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.system.dict.service.ISysLanguageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDictI18nServiceImpl implements ISysDictI18nService {

    @Resource
    private ISysLanguageService languageService;


    @Resource
    private SysDictI18nRepository dictI18nRepository;

    @Resource
    private SysDictRepository dictRepository;

    @Override
    public List<SysDictI18n> getDict(String languageCode, String dictGroupCode) {
        // 从数据库中获取
        return dictI18nRepository.findByDictGroupCodeAndLanguageCode(dictGroupCode, languageCode);
    }

}
