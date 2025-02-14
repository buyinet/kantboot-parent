package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BusAiChatModelPresetsRepository extends JpaRepository<BusAiChatModelPresets,Long> {

    /**
     * 根据模型ID删除
     */
    @Transactional
    @Modifying
    void deleteByModelId(Long modelId);

}
