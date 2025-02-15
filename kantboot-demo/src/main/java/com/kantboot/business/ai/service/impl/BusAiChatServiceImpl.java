package com.kantboot.business.ai.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.entity.BusAiChat;
import com.kantboot.business.ai.domain.entity.BusAiChatMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.repository.BusAiChatMessageRepository;
import com.kantboot.business.ai.repository.BusAiChatModelPresetsRepository;
import com.kantboot.business.ai.repository.BusAiChatModelRepository;
import com.kantboot.business.ai.repository.BusAiChatRepository;
import com.kantboot.business.ai.service.IBusAiChatService;
import com.kantboot.business.ai.util.OvoAIRequestChatUtil;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BusAiChatServiceImpl implements IBusAiChatService {

    @Resource
    private BusAiChatRepository repository;

    @Resource
    private BusAiChatMessageRepository messageRepository;

    @Resource
    private BusAiChatModelRepository modelRepository;

    @Resource
    private BusAiChatModelPresetsRepository modelPresetsRepository;

    @Resource
    private CacheUtil cacheUtil;


    @Override
    public ResponseEntity<StreamingResponseBody> sendMessage(BusAiChatDTO dto) {

        StreamingResponseBody stream = outputStream -> {
            BusAiChat chat = repository.findById(dto.getChatId())
                    .orElseThrow(() ->
                            BaseException.of("chatRecordNotFound", "The chat record does not exist"));
            // 获取模型
            Long modelId = chat.getModelId();
            // 查询模型是否存在
            modelRepository.findById(modelId)
                    .orElseThrow(() ->
                            BaseException.of("modelNotFound", "The model does not exist"));
            // 获取模型前置内容
            List<BusAiChatModelPresets> presets
                    = modelPresetsRepository.getByModelIdAndLanguageCode(modelId, chat.getDefaultLanguageCode());
            if (presets.isEmpty()) {
                // 如果没有完全创建
                throw BaseException.of("modelNotComplete", "The model is not complete");
            }

            JSONObject json = new JSONObject();
            json.put("model", "llama3.2-vision:latest");
            json.put("stream", true);

            JSONArray messagesOfJsonArray = new JSONArray();
            for (BusAiChatModelPresets preset : presets) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("role", preset.getRole());
                jsonObject.put("content", preset.getContent());
                messagesOfJsonArray.add(jsonObject);
            }
            List<BusAiChatMessage> byChatId = messageRepository.getByChatId(dto.getChatId());
            for (BusAiChatMessage message : byChatId) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("role", message.getRole());
                jsonObject.put("content", message.getContent());
                messagesOfJsonArray.add(jsonObject);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", "user");
            jsonObject.put("content", dto.getContent());
            // 添加到数据库中
            messageRepository.save(new BusAiChatMessage()
                    .setChatId(dto.getChatId())
                    .setRole("user")
                    .setContent(dto.getContent()));
            messagesOfJsonArray.add(jsonObject);

            json.put("messages", messagesOfJsonArray);

            // 缓存锁
            if (!cacheUtil.lock("busAiChatServiceImpl:sendMessage:" + dto.getChatId(), 30, TimeUnit.MINUTES)) {
                // 提示正在回答中
                throw BaseException.of("busAiChatServiceImpl:sendMessage:lock", "The request is being answered");
            }

            final Boolean[] flag = {true};

            ThreadUtil.execute(() -> {

                OvoAIRequestChatUtil ovoAIRequestChat = new OvoAIRequestChatUtil(json.toString()) {
                    @SneakyThrows
                    @Override
                    public void run(String responseStr, String str) {
                        try {
                            outputStream.write(responseStr.getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                        }
                    }

                    @SneakyThrows
                    @Override
                    public void finish(String str) {
                        final BusAiChatMessageRepository messageRepositoryIn = messageRepository;
                        flag[0] = false;
                        try {
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        busAiChatFinish(dto.getChatId(), str);
                    }
                };
            });

            while (flag[0]) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        return ResponseEntity.ok()
                .header("Content-Type", "text/event-stream")
                .body(stream);
    }

    private void busAiChatFinish(Long chatId,String str) {
        messageRepository.save(new BusAiChatMessage()
               .setChatId(chatId)
               .setRole("assistant")
               .setContent(str));
        // 解锁
        cacheUtil.unlock("busAiChatServiceImpl:sendMessage:" + chatId);
    }

}
