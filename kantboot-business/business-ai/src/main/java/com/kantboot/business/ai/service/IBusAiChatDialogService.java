package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.dto.DialogSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;

public interface IBusAiChatDialogService {

    /**
     * 创建对话
     */
    BusAiChatDialog createChat(Long modelId, String languageCode);

    /**
     * 发送消息
     */
    ResponseEntity<StreamingResponseBody> sendMessageOfStreamingResponse(BusAiChatDTO dto);

    /**
     * 发送消息
     */
    void sendMessage(BusAiChatDTO dto);

    /**
     * 根据用户账号ID获取对话列表
     */
    List<BusAiChatDialog> getBodyData(DialogSearchDTO param);

    /**
     * 获取自身的对话列表
     */
    List<BusAiChatDialog> getBySelf(DialogSearchDTO param);

    BusAiChatDialog getById(Long id);

    /**
     * 根据会话ID获取model
     */
    BusAiChatModel getModelById(Long id);

}
