//package com.kantboot.business.ai.slot;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaBase;
//import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaMessage;
//import com.kantboot.api.ollama.chat.method.ApiOllamaMethod;
//import com.kantboot.api.ollama.chat.service.IApiOllamaService;
//import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
//import com.kantboot.business.ai.domain.vo.ChatMessageAllVO;
//import com.kantboot.business.ai.method.BusAIChatMethod;
//import com.kantboot.business.ai.util.BusAiChatUtil;
//import jakarta.annotation.Resource;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class SendMessageSlotOld {
//
//    @Resource
//    private IApiOllamaService ollamaService;
//
//    @Resource
//    private BusAiChatUtil busAiChatUtil;
//
//    /**
//     * 发送消息（含流式）
//     */
//    public void sendMessageHasStream(BusAiChatDTO dto, BusAIChatMethod method){
//        // 根据会话ID获取会话
//        List<ChatMessageAllVO> messages = busAiChatUtil.getMessagesAll(dto.getDialogId());
//        List<ApiOllamaMessage> ollamaMessages = BeanUtil.copyToList(messages, ApiOllamaMessage.class);
//        ApiOllamaBase base = new ApiOllamaBase()
//                .setMessages(ollamaMessages)
//                .setStream(dto.getStream());
//
//        ollamaService.chatHasStream(base, new ApiOllamaMethod() {
//
//            @Override
//            public void run(String responseStr, String content, Boolean done) {
//                System.out.print(responseStr);
//                method.run(responseStr, content, done);
//            }
//
//            @Override
//            public void finish(String content) {
//                method.finish(content);
//            }
//
//        });
//    }
//
//}
