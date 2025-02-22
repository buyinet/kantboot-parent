package com.kantboot.business.ai.slot.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineBase;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineMessage;
import com.kantboot.api.volcengine.ai.chat.method.ApiVolcengineMethod;
import com.kantboot.api.volcengine.ai.chat.service.IApiVolcengineService;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.vo.ChatMessageAllVO;
import com.kantboot.business.ai.method.BusAIChatMethod;
import com.kantboot.business.ai.slot.ISendMessageSlot;
import com.kantboot.business.ai.util.BusAiChatUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMessageSlotImplOfVolcengine implements ISendMessageSlot {

    @Resource
    private IApiVolcengineService volcengineService;

    @Resource
    private BusAiChatUtil busAiChatUtil;

    /**
     * 发送消息（含流式）
     */
    @Override
    public void sendMessageHasStream(BusAiChatDTO dto, BusAIChatMethod method){
        // 根据会话ID获取会话
        List<ChatMessageAllVO> messages = busAiChatUtil.getMessagesAll(dto.getDialogId());
        messages.add(new ChatMessageAllVO().setRole("user").setContent(dto.getContent()));
        List<ApiVolcengineMessage> ollamaMessages = BeanUtil.copyToList(messages, ApiVolcengineMessage.class);
        ApiVolcengineBase base = new ApiVolcengineBase()
                .setMessages(ollamaMessages)
                .setStream(dto.getStream());

        volcengineService.chatHasStream(base, new ApiVolcengineMethod() {

            @Override
            public void run(String responseStr, String content, Boolean done) {
                System.out.print(responseStr);
                method.run(responseStr, content, done);
            }

            @Override
            public void finish(String content) {
                method.finish(content);
            }

        });
    }

}
