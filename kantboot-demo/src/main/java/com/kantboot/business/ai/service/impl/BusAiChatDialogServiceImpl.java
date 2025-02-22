package com.kantboot.business.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatModelRepository;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.dto.DialogSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.exception.BusAiException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class BusAiChatDialogServiceImpl implements IBusAiChatDialogService {

    @Resource
    private BusAiChatDialogRepository repository;

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
    public BusAiChatDialog createChat(Long modelId, String languageCode) {
        if (languageCode == null) {
            languageCode = httpRequestHeaderUtil.getLanguageCode();
        }

        BusAiChatModel busAiChatModel = modelRepository.findById(modelId).orElseThrow(() -> {
            throw BusAiException.MODEL_NOT_EXIST;
        });
        // 查询支持由对应的语言编码
        List<BusAiChatModelLanguageSupport> languageSupports = busAiChatModel.getLanguageSupports();
        for (BusAiChatModelLanguageSupport languageSupport : languageSupports) {
            if (languageSupport.getLanguageCode().equals(languageCode)) {
                // 创建一个聊天记录
                BusAiChatDialog busAiChatDialog = new BusAiChatDialog()
                        .setModelId(modelId)
                        .setUserAccountId(userAccountService.getSelfId())
                        .setLanguageCode(languageCode);
                return repository.save(busAiChatDialog);
            }
        }
        throw BusAiException.MODEL_NOT_EXIST;
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
                    public void run(String responseStr, String str, Boolean done) {
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

    @Transactional
    @Override
    public void sendMessage(BusAiChatDTO dto) {
        dto.setStream(true);
        JSONObject chatJson = busAiChatUtil.getChatJson(dto);
        Long userId = userAccountService.getSelfId();
        BusAiChatDialogMessage assistantMessage = dialogMessageRepository.save(new BusAiChatDialogMessage()
                .setDialogId(dto.getDialogId())
                .setStatusCode("thinking")
                .setRole("assistant"));
        // 修改最后一次聊天时间
        BusAiChatDialog dialog = repository.findById(dto.getDialogId())
                .orElseThrow(
                        BusAiException.CHAT_NOT_EXIST);
        dialog.setGmtModified(new Date());
        repository.save(dialog);

        Long assistantMessageId = assistantMessage.getId();
        ThreadUtil.execute(() -> {
            AIRequestChatUtil ovoAIRequestChat = new AIRequestChatUtil(chatJson.toString()) {
                @Override
                public void run(String responseStr, String str, Boolean done) {
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
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("dialogId", dto.getDialogId());
                    jsonObject.put("role", "assistant");
                    jsonObject.put("userId", userId);
                    jsonObject.put("dialogMessageId", assistantMessageId);
                    jsonObject.put("content", str);
                    jsonObject.put("done", true);
                    messageService.sendMessage(new MessageDTO()
                            .setUserAccountId(userId)
                            .setToolCode("websocket")
                            .setEmit("aiAssistantChat")
                            .setData(jsonObject.toJSONString())
                    );
                    busAiChatUtil.assistantChatFinish(assistantMessage
                            .setStatusCode("finish")
                            .setContent(str));
                }
            };
        });
    }

    @Override
    public List<BusAiChatDialog> getBodyData(DialogSearchDTO param) {
        Long bodyDataCount = repository.getBodyDataCount(param);
        // 每页10条数据
        int pageSize = 10;
        // 计算总页数
        int totalPages = (int) Math.ceil((double) bodyDataCount / pageSize);
        if (totalPages < 1) {
            return List.of();
        }
        // 获取最后一页
        Pageable pageable = PageRequest.of(totalPages - 1, pageSize);
        Page<BusAiChatDialog> bodyData = repository.getBodyData(param, pageable);
        List<BusAiChatDialog> list = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialog.class);
        // 查看有几条数据，如果小于10条，就再加上上一页
        if (list.size() < pageSize && totalPages > 1
                && param.getMaxId() == null
                && param.getMinId() == null
        ) {
            pageable = PageRequest.of(totalPages - 2, pageSize);
            bodyData = repository.getBodyData(param, pageable);
            List<BusAiChatDialog> list2 = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialog.class);
            list2.addAll(list);
            return list2;
        }
        return list;
    }

    @Override
    public List<BusAiChatDialog> getBySelf(DialogSearchDTO param) {
        if (param == null) {
            param = new DialogSearchDTO();
        }

        // 设置用户ID
        param.setUserAccountId(userAccountService.getSelfId());

        return getBodyData(param);
    }

    @Override
    public BusAiChatDialog getById(Long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw BusAiException.CHAT_NOT_EXIST;
        });
    }

    @Override
    public BusAiChatModel getModelById(Long id) {
        BusAiChatDialog byId = getById(id);
        return modelRepository.findById(byId.getModelId()).orElseThrow(() -> {
            throw BusAiException.MODEL_NOT_EXIST;
        });
    }
}
