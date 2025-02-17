package com.kantboot.business.ai.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.entity.BusAiChat;
import com.kantboot.business.ai.domain.entity.BusAiChatMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.business.ai.repository.BusAiChatMessageRepository;
import com.kantboot.business.ai.repository.BusAiChatModelRepository;
import com.kantboot.business.ai.repository.BusAiChatRepository;
import com.kantboot.business.ai.service.IBusAiChatService;
import com.kantboot.business.ai.util.AIRequestChatUtil;
import com.kantboot.business.ai.util.BusAiChatUtil;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;

@Service
public class BusAiChatServiceImpl implements IBusAiChatService {

    @Resource
    private BusAiChatRepository repository;

    @Resource
    private BusAiChatMessageRepository messageRepository;

    @Resource
    private BusAiChatModelRepository modelRepository;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    BusAiChatUtil busAiChatUtil;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Override
    public void createChat(Long modelId, String languageCode) {
        if (languageCode == null) {
            languageCode = httpRequestHeaderUtil.getLanguageCode();
        }

        BusAiChatModel busAiChatModel = modelRepository.findById(modelId).orElseThrow(() -> {
            throw BusAiException.MODEL_NOT_EXIST;
        });
        // 查询支持由对应的语言编码
        List<BusAiChatModelLanguageSupport> languageSupports = busAiChatModel.getLanguageSupports();
        boolean hasLanguageSupports = false;
        for (BusAiChatModelLanguageSupport languageSupport : languageSupports) {
            if (languageSupport.getLanguageCode().equals(languageCode)) {
                // 创建一个聊天记录
                BusAiChat busAiChat = new BusAiChat()
                        .setModelId(modelId)
                        .setUserAccountId(userAccountService.getSelfId())
                        .setLanguageCode(languageCode);
                repository.save(busAiChat);
                // 创建一个消息记录
                messageRepository.save(new BusAiChatMessage()
                        .setChatId(busAiChat.getId()));
                hasLanguageSupports = true;
            }
        }
        if (!hasLanguageSupports) {
            throw BusAiException.MODEL_NOT_EXIST;
        }
    }

    @Transactional
    @Override
    public ResponseEntity<StreamingResponseBody> sendMessage(BusAiChatDTO dto) {
        StreamingResponseBody stream = outputStream -> {

            JSONObject chatJson = busAiChatUtil.getChatJson(dto);

            final Boolean[] flag = {true};

            ThreadUtil.execute(() -> {
                AIRequestChatUtil ovoAIRequestChat = new AIRequestChatUtil(chatJson.toString()) {
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
                        }

                        busAiChatUtil.assistantChatFinish(dto.getChatId(), str);
                    }
                };
            });

            while (flag[0]) {
                try {
                    Thread.sleep(100);
                    System.err.print("·");
                } catch (InterruptedException e) {
                }
            }
        };

        return ResponseEntity.ok()
                .header("Content-Type", "text/event-stream")
                .body(stream);
    }


}
