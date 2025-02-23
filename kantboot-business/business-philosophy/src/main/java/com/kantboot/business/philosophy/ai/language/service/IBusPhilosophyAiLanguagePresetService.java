package com.kantboot.business.philosophy.ai.language.service;

import com.kantboot.business.philosophy.ai.language.domain.entity.BusPhilosophyAiLanguagePreset;

import java.util.List;

public interface IBusPhilosophyAiLanguagePresetService {

    /**
     * 获取所有语言预设
     * @return 所有语言预设
     */
    List<BusPhilosophyAiLanguagePreset> getAll();

    /**
     * 根据语言获取语言编码预设
     * @param languageCode 语言编码
     * @return 语言预设
     */
    List<BusPhilosophyAiLanguagePreset> getByLanguageCode(String languageCode);

}
