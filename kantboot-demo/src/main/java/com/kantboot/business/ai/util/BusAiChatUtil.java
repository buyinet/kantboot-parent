package com.kantboot.business.ai.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.kantboot.business.ai.domain.entity.BusAiChatMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.business.ai.repository.BusAiChatMessageRepository;
import com.kantboot.business.ai.repository.BusAiChatModelPresetsRepository;
import com.kantboot.business.ai.repository.BusAiChatRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusAiChatUtil {

    @Resource
    private BusAiChatRepository chatRepository;

    @Resource
    private BusAiChatMessageRepository messageRepository;

    @Resource
    private BusAiChatModelPresetsRepository modelPresetsRepository;

    /**
     * 获取最上级JSON
     */
    public JSONObject getRootJson(Boolean stream) {
        if(stream==null){
            stream=true;
        }
        JSONObject json = new JSONObject();
        json.put("model", "llama3.2-vision:latest");
        json.put("stream", stream);
        return json;
    }


    /**
     * 获取模型预设
     */
    public JSONArray getPresets(Long chatModelId, String languageCode) {
        List<BusAiChatModelPresets> byModelIdAndLanguageCode
                = modelPresetsRepository.getByModelIdAndLanguageCode(chatModelId, languageCode);
        if (byModelIdAndLanguageCode.isEmpty()) {
            // 如果没创建完成
            throw BusAiException.MODEL_NOT_COMPLETE;
        }
        JSONArray jsonArray = new JSONArray();
        for (BusAiChatModelPresets preset : byModelIdAndLanguageCode) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", preset.getRole());
            jsonObject.put("content", preset.getContent());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 获取该聊天的消息
     */
    public JSONArray getMessages(Long chatId) {
        List<BusAiChatMessage> byChatId = messageRepository.getByChatId(chatId);
        JSONArray messagesOfJsonArray = new JSONArray();
        for (BusAiChatMessage message : byChatId) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", message.getRole());
            jsonObject.put("content", message.getContent());
            messagesOfJsonArray.add(jsonObject);
        }
        return messagesOfJsonArray;
    }

}
