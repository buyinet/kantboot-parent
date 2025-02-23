package com.kantboot.business.philosophy.ai.language.service.impl;

import com.kantboot.business.philosophy.ai.language.domain.entity.BusPhilosophyAiLanguagePreset;
import com.kantboot.business.philosophy.ai.language.repository.BusPhilosophyAiLanguagePresetRepository;
import com.kantboot.business.philosophy.ai.language.service.IBusPhilosophyAiLanguagePresetService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusPhilosophyAiLanguagePresetServiceImpl implements IBusPhilosophyAiLanguagePresetService {

    @Resource
    private BusPhilosophyAiLanguagePresetRepository repository;

    @Override
    public List<BusPhilosophyAiLanguagePreset> getAll() {
        return repository.findAll();
    }

    @Override
    public List<BusPhilosophyAiLanguagePreset> getByLanguageCode(String languageCode) {
        return repository.findByLanguageCode(languageCode);
    }

}
