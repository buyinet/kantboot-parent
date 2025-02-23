package com.kantboot.business.philosophy.ai.language.repository;

import com.kantboot.business.philosophy.ai.language.domain.entity.BusPhilosophyAiLanguagePreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusPhilosophyAiLanguagePresetRepository extends JpaRepository<BusPhilosophyAiLanguagePreset,Long> {

    /**
     * 根据语言编码获取预设
     * @param languageCode 语言编码
     * @return 预设
     */
    @Query("FROM BusPhilosophyAiLanguagePreset WHERE languageCode =?1 ORDER BY priority DESC")
    List<BusPhilosophyAiLanguagePreset> findByLanguageCode(String languageCode);

}
