package com.kantboot.business.ai.i18n.dao.repository;

import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelLabelI18n;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BusAiChatModelLabelI18nRepository extends JpaRepository<BusAiChatModelLabelI18n,Long> {

    /**
     * 根据模型ID删除
     */
    @Transactional
    @Modifying
    void deleteByModelId(Long modelId);

}
