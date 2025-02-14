package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BusAiChatModelLanguageSupportRepository extends JpaRepository<BusAiChatModelLanguageSupport,Long> {

    /**
     * 根据模型id删除所有语言支持
     * @param modelId 模型id
     */
    @Transactional
    @Modifying
    void deleteByModelId(Long modelId);

}
