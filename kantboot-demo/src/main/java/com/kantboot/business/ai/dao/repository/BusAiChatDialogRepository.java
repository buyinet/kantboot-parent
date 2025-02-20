package com.kantboot.business.ai.dao.repository;

import com.kantboot.business.ai.domain.dto.DialogSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BusAiChatDialogRepository extends JpaRepository<BusAiChatDialog,Long> {

    /**
     * 根据用户ID获取
     */
    @Query("""
            FROM BusAiChatDialog d
            WHERE (:#{#param.userAccountId} IS NULL OR d.userAccountId = :#{#param.userAccountId})
            AND (:#{#param.maxId} IS NULL OR d.id < :#{#param.maxId})
            AND (:#{#param.minId} IS NULL OR d.id > :#{#param.minId})
            AND (:#{#param.dialogId} IS NULL OR d.id = :#{#param.dialogId})
            AND (:#{#param.modelId} IS NULL OR d.modelId = :#{#param.modelId})
            ORDER BY d.gmtModified DESC
            """)
    Page<BusAiChatDialog> getBodyData(@Param("param") DialogSearchDTO param, Pageable pageable);

    /**
     * 查询总数
     */
    @Query("""
            SELECT COUNT(d)
            FROM BusAiChatDialog d
            WHERE (:#{#param.userAccountId} IS NULL OR d.userAccountId = :#{#param.userAccountId})
            AND (:#{#param.maxId} IS NULL OR d.id < :#{#param.maxId})
            AND (:#{#param.minId} IS NULL OR d.id > :#{#param.minId})
            AND (:#{#param.dialogId} IS NULL OR d.id = :#{#param.dialogId})
            AND (:#{#param.modelId} IS NULL OR d.modelId = :#{#param.modelId})
            """)
    Long getBodyDataCount(@Param("param") DialogSearchDTO param);

}
