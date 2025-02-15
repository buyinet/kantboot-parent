package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusAiChatMessageRepository extends JpaRepository<BusAiChatMessage,Long> {

    /**
     * 根据chatId获取聊天记录
     */
    @Query("SELECT m FROM BusAiChatMessage m WHERE m.chatId =?1 ORDER BY m.gmtCreate ASC")
    List<BusAiChatMessage> getByChatId(Long chatId);

}
