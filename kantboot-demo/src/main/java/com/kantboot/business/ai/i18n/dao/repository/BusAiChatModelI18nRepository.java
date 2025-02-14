package com.kantboot.business.ai.i18n.dao.repository;

import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelI18n;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BusAiChatModelI18nRepository extends JpaRepository<BusAiChatModelI18n,Long> {

    /**
     * 根据主体ID删除
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM BusAiChatModelI18n WHERE bodyId = ?1")
    void deleteByBodyId(Long bodyId);


}
