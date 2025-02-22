package com.kantboot.business.ai.dao.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModelSetting;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BusAiChatModelSettingRepository
        extends JpaRepository<BusAiChatModelSetting,Long> {

    /**
     * 根据模型ID删除
     */
    @Transactional
    @Modifying
    void deleteByModelId(Long modelId);

}
