package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface IBusAiChatDialogService {

    /**
     * 创建对话
     */
    void createChat(Long modelId, String languageCode);

    /**
     * 发送消息
     */
    ResponseEntity<StreamingResponseBody> sendMessageOfStreamingResponse(BusAiChatDTO dto);

    /**
     * 发送消息
     */
    BusAiChatDialogMessage sendMessage(BusAiChatDTO dto);

}
