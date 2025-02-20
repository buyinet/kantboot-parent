package com.kantboot.business.ai.dao.repository;

import com.kantboot.business.ai.domain.dto.DialogMessageSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusAiChatDialogMessageRepository extends JpaRepository<BusAiChatDialogMessage,Long> {

    /**
     * 根据chatId获取聊天记录
     */
    @Query("FROM BusAiChatDialogMessage m WHERE m.dialogId =?1 ORDER BY m.gmtCreate ASC")
    List<BusAiChatDialogMessage> getByDialogId(Long dialogId);

    @Query("""
            FROM BusAiChatDialogMessage m
            WHERE (:#{#param.dialogId} IS NULL OR m.dialogId =:#{#param.dialogId})
            AND (:#{#param.content} IS NULL OR m.content LIKE %:#{#param.content}%)
            AND (:#{#param.role} IS NULL OR m.role =:#{#param.role})
            AND (:#{#param.maxId} IS NULL OR m.id < :#{#param.maxId})
            AND (:#{#param.minId} IS NULL OR m.id > :#{#param.minId})
            ORDER BY m.id ASC
    """)
    Page<BusAiChatDialogMessage> getBodyData(@Param("param") DialogMessageSearchDTO param, Pageable pageable);

    @Query("""
            SELECT COUNT(m)
            FROM BusAiChatDialogMessage m
            WHERE (:#{#param.dialogId} IS NULL OR m.dialogId =:#{#param.dialogId})
            AND (:#{#param.content} IS NULL OR m.content LIKE %:#{#param.content}%)
            AND (:#{#param.role} IS NULL OR m.role =:#{#param.role})
            AND (:#{#param.maxId} IS NULL OR m.id < :#{#param.maxId})
            AND (:#{#param.minId} IS NULL OR m.id > :#{#param.minId})
    """)
    Long getBodyDataCount(@Param("param") DialogMessageSearchDTO param);

}
