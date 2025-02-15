package com.kantboot.system.dict.service.impl;

import com.kantboot.system.dict.dao.repository.SysLanguageLocalizedRepository;
import com.kantboot.system.dict.dao.repository.SysLanguageRepository;
import com.kantboot.system.dict.domain.entity.SysLanguage;
import com.kantboot.system.dict.domain.entity.SysLanguageLocalized;
import com.kantboot.system.dict.service.ISysLanguageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLanguageServiceImpl implements ISysLanguageService {

    @Resource
    private SysLanguageRepository repository;

    @Resource
    private SysLanguageLocalizedRepository localizedRepository;

    @Override
    public List<SysLanguage> getBySupport() {
        return repository.findBySupportTrue();
    }

    @Override
    public List<SysLanguageLocalized> getLocalizedList() {
        return localizedRepository.findAll();
    }

    @Override
    public List<SysLanguage> getAll() {
        return repository.findAll();
    }

}
