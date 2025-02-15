package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusAiChatModelPresetsRepository extends JpaRepository<BusAiChatModelPresets,Long> {

    /**
     * 根据模型ID删除
     */
    @Transactional
    @Modifying
    void deleteByModelId(Long modelId);

    /**
     * 根据模型ID和
     */
    @Query("SELECT p FROM BusAiChatModelPresets p WHERE p.modelId = ?1 AND p.languageCode = ?2 ORDER BY p.priority DESC")
    List<BusAiChatModelPresets> getByModelIdAndLanguageCode(Long modelId, String languageCode);

}
