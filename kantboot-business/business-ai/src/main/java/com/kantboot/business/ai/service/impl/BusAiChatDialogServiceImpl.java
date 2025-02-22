package com.kantboot.business.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.kantboot.business.ai.constants.DialogMessageStatusCodeConstants;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatModelRepository;
import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.dto.DialogSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.domain.vo.ChatTransmissionVO;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.business.ai.method.BusAIChatMethod;
import com.kantboot.business.ai.service.IBusAiChatDialogService;
import com.kantboot.business.ai.slot.SendMessageSlot;
import com.kantboot.business.ai.util.BusAiChatUtil;
import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.functional.message.service.IFunctionalMessageService;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

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
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Resource
    private SendMessageSlot sendMessageSlot;

    @Resource
    private BusAiChatUtil busAiChatUtil;

    @Resource
    private IFunctionalMessageService functionalMessageService;

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
        return null;
    }

    @Transactional
    @Override
    public void sendMessage(BusAiChatDTO dto) {
        // 加锁
        busAiChatUtil.lockOfSendMessage(dto.getDialogId());

        // 获取用户账号ID
        Long userAccountId = userAccountService.getSelfId();

        // 将用户的发送的消息保存到数据库中
        BusAiChatDialogMessage userMessage = new BusAiChatDialogMessage();
        userMessage.setDialogId(dto.getDialogId())
                .setUserAccountId(userAccountId)
                .setRole("user")
                .setContent(dto.getContent());
        dialogMessageRepository.save(userMessage);

        BusAiChatDialogMessage aiMessage = new BusAiChatDialogMessage();
        aiMessage.setDialogId(dto.getDialogId())
                .setRole("assistant")
                .setContent("")
                .setStatusCode(DialogMessageStatusCodeConstants.THINKING);
        dialogMessageRepository.save(aiMessage);

        BusAiChatDialog dialog = repository.findById(dto.getDialogId())
                .orElseThrow(
                        BusAiException.CHAT_NOT_EXIST);
        dialog.setGmtModified(new Date());
        repository.save(dialog);

        ThreadUtil.execute(() -> {
            sendMessageSlot.sendMessageHasStream(dto, new BusAIChatMethod() {

                @Override
                public void run(String text, String content, Boolean done) {
                    ChatTransmissionVO chatTransmissionVO = new ChatTransmissionVO()
                            .setText(text)
                            .setContent(content)
                            .setDone(done)
                            .setDialogId(aiMessage.getDialogId())
                            .setDialogMessageId(aiMessage.getId())
                            .setRole(aiMessage.getRole());
                    functionalMessageService.sendMessage(new MessageDTO()
                            .setUserAccountId(userAccountId)
                            .setToolCode("websocket")
                            .setEmit("aiAssistantChat")
                            .setData(JSON.toJSONString(chatTransmissionVO))
                    );
                }

                @Override
                public void finish(String content) {
                    aiMessage.setContent(content);
                    aiMessage.setStatusCode(DialogMessageStatusCodeConstants.FINISH);
                    dialogMessageRepository.save(aiMessage);
                    // 解锁
                    busAiChatUtil.unlockOfSendMessage(dto.getDialogId());
                }
            });
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
