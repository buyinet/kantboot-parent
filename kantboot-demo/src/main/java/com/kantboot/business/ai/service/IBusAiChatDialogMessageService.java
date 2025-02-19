package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.DialogMessageSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;

import java.util.List;

public interface IBusAiChatDialogMessageService {

    /**
     * 获取一开始看到的对话
     */
    List<BusAiChatDialogMessage> getList(DialogMessageSearchDTO param);

}
