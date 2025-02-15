package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public interface IBusAiChatService {

    /**
     * 发送消息
     */
    ResponseEntity<StreamingResponseBody> sendMessage(BusAiChatDTO dto);

}
