package com.kantboot.business.ai.util;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogRepository;
import com.kantboot.business.ai.dao.repository.BusAiChatModelPresetsRepository;
import com.kantboot.business.ai.domain.dto.DialogMessageSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.domain.vo.ChatMessageAllVO;
import com.kantboot.business.ai.exception.BusAiException;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BusAiChatUtil {

    @Resource
    private BusAiChatDialogRepository dialogRepository;

    @Resource
    private BusAiChatModelPresetsRepository presetsRepository;

    @Resource
    private BusAiChatDialogMessageRepository messageRepository;

    @Resource
    private CacheUtil cacheUtil;

    public List<ChatMessageAllVO> getMessagesAll(Long dialogId){
        // 获取到对应的对话
        BusAiChatDialog dialog = dialogRepository.findById(dialogId)
                .orElseThrow(() -> BusAiException.CHAT_NOT_EXIST);

        // 获取预设
        List<BusAiChatModelPresets> presets
                = presetsRepository.getByModelIdAndLanguageCode(dialog.getModelId(), dialog.getLanguageCode());

        List<BusAiChatDialogMessage> messages = getMessageList(new DialogMessageSearchDTO().setDialogId(dialogId));

        List<ChatMessageAllVO> result = new ArrayList<>();

        for (BusAiChatModelPresets preset : presets) {
            result.add(new ChatMessageAllVO()
                    .setRole(preset.getRole())
                    .setContent(preset.getContent())
            );
        }

        for (BusAiChatDialogMessage message : messages) {
            result.add(new ChatMessageAllVO()
                    .setRole(message.getRole())
                    .setContent(message.getContent())
            );
        }

        return result;
    }

    public List<BusAiChatDialogMessage> getMessageList(DialogMessageSearchDTO param) {
        Long bodyDataCount = messageRepository.getBodyDataCount(param);
        // 每页10条数据
        int pageSize = 50;
        // 计算总页数
        int totalPages = (int) Math.ceil((double) bodyDataCount / pageSize);
        if (totalPages < 1) {
            return List.of();
        }
        // 获取最后一页
        Pageable pageable = PageRequest.of(totalPages-1, pageSize);
        Page<BusAiChatDialogMessage> bodyData = messageRepository.getBodyData(param, pageable);
        List<BusAiChatDialogMessage> list = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialogMessage.class);
        // 查看有几条数据，如果小于10条，就再加上上一页
        if (list.size() < 10 && totalPages > 1
                && param.getMaxId() == null
                && param.getMinId() == null
        ) {
            pageable = PageRequest.of(totalPages-2, pageSize);
            bodyData = messageRepository.getBodyData(param, pageable);
            List<BusAiChatDialogMessage> list2 = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialogMessage.class);
            list2.addAll(list);
            return list2;
        }
        return list;
    }

    /**
     * 锁定
     */
    public void lockOfSendMessage(Long chatId){
        // 缓存锁
        if (!cacheUtil.lock("busAiChatDialogService:sendMessage:" + chatId, 10, TimeUnit.MINUTES)) {
            // 提示正在回答中
            throw BaseException.of("busAiChatDialogService:sendMessage:lock", "The request is being answered");
        }
    }

    /**
     * 解锁
     */
    public void unlockOfSendMessage(Long chatId){
        cacheUtil.unlock("busAiChatDialogService:sendMessage:" + chatId);
    }



}
