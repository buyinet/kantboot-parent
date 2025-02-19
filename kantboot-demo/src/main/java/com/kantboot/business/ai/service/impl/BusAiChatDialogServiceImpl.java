package com.kantboot.business.ai.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.business.ai.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.repository.BusAiChatModelRepository;
import com.kantboot.business.ai.repository.BusAiChatRepository;
import com.kantboot.business.ai.service.IBusAiChatDialogService;
import com.kantboot.business.ai.util.AIRequestChatUtil;
import com.kantboot.business.ai.util.BusAiChatUtil;
import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.functional.message.service.IFunctionalMessageService;
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
public class BusAiChatDialogServiceImpl implements IBusAiChatDialogService {

    @Resource
    private BusAiChatRepository repository;

    @Resource
    private BusAiChatDialogMessageRepository dialogMessageRepository;

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

    @Resource
    private IFunctionalMessageService messageService;

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
                BusAiChatDialog busAiChatDialog = new BusAiChatDialog()
                        .setModelId(modelId)
                        .setUserAccountId(userAccountService.getSelfId())
                        .setLanguageCode(languageCode);
                repository.save(busAiChatDialog);
                // 创建一个消息记录
                dialogMessageRepository.save(new BusAiChatDialogMessage()
                        .setDialogId(busAiChatDialog.getId()));
                hasLanguageSupports = true;
            }
        }
        if (!hasLanguageSupports) {
            throw BusAiException.MODEL_NOT_EXIST;
        }
    }

    @Transactional
    @Override
    public ResponseEntity<StreamingResponseBody> sendMessageOfStreamingResponse(BusAiChatDTO dto) {
        StreamingResponseBody stream = outputStream -> {

            JSONObject chatJson = busAiChatUtil.getChatJson(dto);

            final Boolean[] flag = {true};

            ThreadUtil.execute(() -> {
                AIRequestChatUtil ovoAIRequestChat = new AIRequestChatUtil(chatJson.toString()) {
                    @SneakyThrows
                    @Override
                    public void run(String responseStr, String str,Boolean done) {
                        try {
                            outputStream.write(responseStr.getBytes());
                            outputStream.flush();
                        } catch (IOException e) {
                        }
                    }

                    @SneakyThrows
                    @Override
                    public void finish(String str) {
                        final BusAiChatDialogMessageRepository messageRepositoryIn = dialogMessageRepository;
                        flag[0] = false;
                        try {
                            outputStream.close();
                        } catch (Exception e) {
                        }

                        busAiChatUtil.assistantChatFinish(dto.getDialogId(), str);
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

    @Override
    public BusAiChatDialogMessage sendMessage(BusAiChatDTO dto) {
        JSONObject chatJson = busAiChatUtil.getChatJson(dto);
        Long userId = userAccountService.getSelfId();
        BusAiChatDialogMessage userMessage = new BusAiChatDialogMessage()
                .setDialogId(dto.getDialogId())
                .setUserAccountId(userId)
                .setContent(dto.getContent())
                .setRole("user");
        BusAiChatDialogMessage save = dialogMessageRepository.save(userMessage);
        BusAiChatDialogMessage assistantMessage = dialogMessageRepository.save(new BusAiChatDialogMessage()
                .setDialogId(dto.getDialogId())
                .setRole("assistant"));
        Long assistantMessageId = assistantMessage.getId();
        ThreadUtil.execute(() -> {
        AIRequestChatUtil ovoAIRequestChat = new AIRequestChatUtil(chatJson.toString()) {
            @Override
            public void run(String responseStr, String str,Boolean done) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("text", responseStr);
                jsonObject.put("dialogId", dto.getDialogId());
                jsonObject.put("role", "assistant");
                jsonObject.put("userId", userId);
                jsonObject.put("dialogMessageId", assistantMessageId);
                jsonObject.put("content", str);
                jsonObject.put("done", done);
                messageService.sendMessage(new MessageDTO()
                        .setUserAccountId(userId)
                        .setToolCode("websocket")
                        .setEmit("aiAssistantChat")
                        .setData(jsonObject.toJSONString())
                );
            }

            @Override
            public void finish(String str) {
               busAiChatUtil.assistantChatFinish(assistantMessage.setContent(str));
            }
        };
        });
        return save;
    }

}
