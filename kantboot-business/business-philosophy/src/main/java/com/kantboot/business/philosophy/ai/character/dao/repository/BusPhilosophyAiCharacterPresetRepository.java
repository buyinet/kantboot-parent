package com.kantboot.business.philosophy.ai.character.dao.repository;

import com.kantboot.business.philosophy.ai.character.domain.entity.BusPhilosophyAiCharacterPreset;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusPhilosophyAiCharacterPresetRepository extends JpaRepository<BusPhilosophyAiCharacterPreset,Long> {

    /**
     * 根据模型ID删除
     */
    @Transactional
    @Modifying
    void deleteByCharacterId(Long characterId);

    /**
     * 根据模型ID和
     */
    @Query("SELECT p FROM BusPhilosophyAiCharacterPreset p WHERE p.characterId = ?1 AND p.languageCode = ?2 ORDER BY p.priority DESC")
    List<BusPhilosophyAiCharacterPreset> getByCharacterIdAndLanguageCode(Long characterId, String languageCode);

}
