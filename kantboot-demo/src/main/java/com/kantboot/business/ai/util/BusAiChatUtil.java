package com.kantboot.business.ai.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatModelPresetsRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogRepository;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BusAiChatUtil {

    @Resource
    private BusAiChatDialogRepository chatRepository;

    @Resource
    private BusAiChatDialogMessageRepository messageRepository;

    @Resource
    private BusAiChatModelPresetsRepository modelPresetsRepository;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private IUserAccountService userAccountService;

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
    public JSONArray getMessages(Long dialogId) {
        List<BusAiChatDialogMessage> byChatId = messageRepository.getByDialogId(dialogId);
        JSONArray messagesOfJsonArray = new JSONArray();
        for (BusAiChatDialogMessage message : byChatId) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", message.getRole());
            jsonObject.put("content", message.getContent());
            messagesOfJsonArray.add(jsonObject);
        }
        return messagesOfJsonArray;
    }

    /**
     * 获取初始化JSON
     */
    public JSONObject getChatJson(BusAiChatDTO dto) {
        Long dialogId = dto.getDialogId();
        // 根据chatId获取Chat信息
        BusAiChatDialog dialog = chatRepository.findById(dialogId).orElseThrow(() ->
                BusAiException.CHAT_NOT_EXIST);
        JSONObject rootJson = getRootJson(dto.getStream());
        JSONArray messageArray = new JSONArray();
        // 获取预设
        JSONArray presets = getPresets(dialog.getModelId(), dialog.getLanguageCode());
        for (int i = 0; i < presets.size(); i++) {
            JSONObject preset = presets.getJSONObject(i);
            messageArray.add(preset);
        }
        // 获取所有聊天记录
        JSONArray messages = getMessages(dialogId);
        for (int i = 0; i < messages.size(); i++) {
            JSONObject message = messages.getJSONObject(i);
            messageArray.add(message);
        }

        // 添加新的消息
        JSONObject newMessage = new JSONObject();
        newMessage.put("role", "user");
        newMessage.put("content", dto.getContent());
        messageArray.add(newMessage);

        // 将数据存储到数据库中
        BusAiChatDialogMessage userMessage = new BusAiChatDialogMessage()
                .setDialogId(dto.getDialogId())
                .setUserAccountId(userAccountService.getSelfId())
                .setContent(dto.getContent())
                .setRole("user");
        messageRepository.save(userMessage);


        lock(dialogId);

        rootJson.put("messages", messageArray);
        return rootJson;
    }

    /**
     * 锁定
     */
    public void lock(Long chatId){
        // 缓存锁
        if (!cacheUtil.lock("busAiChatDialogService:sendMessage:" + chatId, 10, TimeUnit.MINUTES)) {
            // 提示正在回答中
            throw BaseException.of("busAiChatDialogService:sendMessage:lock", "The request is being answered");
        }
    }

    /**
     * 解锁
     */
    public void unlock(Long chatId){
        cacheUtil.unlock("busAiChatDialogService:sendMessage:" + chatId);
    }

    /**
     * 回复完成
     */
    public void assistantChatFinish(Long chatId, String content){
        messageRepository.save(new BusAiChatDialogMessage()
                .setDialogId(chatId)
                .setRole("assistant")
                .setContent(content));
        unlock(chatId);
    }

    public void assistantChatFinish(BusAiChatDialogMessage dialogMessage){
        messageRepository.save(dialogMessage);
        unlock(dialogMessage.getDialogId());
    }

}
