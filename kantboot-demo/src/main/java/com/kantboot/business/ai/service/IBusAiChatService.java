package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface IBusAiChatService {

    /**
     * 创建对话
     */
    void createChat(Long modelId, String languageCode);

    /**
     * 发送消息
     */
    ResponseEntity<StreamingResponseBody> sendMessage(BusAiChatDTO dto);

}
