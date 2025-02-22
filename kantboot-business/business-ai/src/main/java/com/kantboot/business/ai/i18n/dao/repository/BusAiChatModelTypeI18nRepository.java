package com.kantboot.business.ai.i18n.dao.repository;

import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelTypeI18n;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusAiChatModelTypeI18nRepository extends JpaRepository<BusAiChatModelTypeI18n,Long> {

    /**
     * 根据语言编码获取所有类型的国际化信息
     */
    List<BusAiChatModelTypeI18n> getByLanguageCode(String languageCode);

    /**
     * 根据bodyId删除
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM BusAiChatModelTypeI18n WHERE bodyId = ?1")
    void deleteByBodyId(Long bodyId);

}
