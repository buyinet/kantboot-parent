package com.kantboot.business.ai.slot.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.api.segmind.ai.chat.domain.dto.ApiSegmindBase;
import com.kantboot.api.segmind.ai.chat.domain.dto.ApiSegmindMessage;
import com.kantboot.api.segmind.ai.chat.method.ApiSegmindMethod;
import com.kantboot.api.segmind.ai.chat.service.IApiSegmindService;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.vo.ChatMessageAllVO;
import com.kantboot.business.ai.method.BusAIChatMethod;
import com.kantboot.business.ai.slot.ISendMessageSlot;
import com.kantboot.business.ai.util.BusAiChatUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMessageSlotImplOfSegmind implements ISendMessageSlot {

    @Resource
    private IApiSegmindService apiSegmindService;

    @Resource
    private BusAiChatUtil busAiChatUtil;

    @Override
    public void sendMessageHasStream(BusAiChatDTO dto, BusAIChatMethod method) {
        // 根据会话ID获取会话
        List<ChatMessageAllVO> messages = busAiChatUtil.getMessagesAll(dto.getDialogId());
        messages.add(new ChatMessageAllVO().setRole("user").setContent(dto.getContent()));
        List<ApiSegmindMessage> ollamaMessages = BeanUtil.copyToList(messages, ApiSegmindMessage.class);
        ApiSegmindBase base = new ApiSegmindBase()
                .setMessages(ollamaMessages)
                .setStream(dto.getStream());

        apiSegmindService.chatHasStream(base, new ApiSegmindMethod() {

            @Override
            public void run(String responseStr, String content, Boolean done) {
                method.run(responseStr, content, done);
            }

            @Override
            public void finish(String content) {
                method.finish(content);
            }

        });
    }

}
