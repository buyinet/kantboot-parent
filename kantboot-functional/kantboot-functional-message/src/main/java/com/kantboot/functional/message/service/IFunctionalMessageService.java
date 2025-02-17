package com.kantboot.functional.message.service;

import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.functional.message.domain.entity.FunctionalMessage;

public interface IFunctionalMessageService {

    /**
     * 发送消息
     */
    FunctionalMessage sendMessage(MessageDTO dto);

}
