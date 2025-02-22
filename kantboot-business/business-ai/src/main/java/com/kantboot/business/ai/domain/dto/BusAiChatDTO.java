package com.kantboot.business.ai.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BusAiChatDTO implements Serializable {

    /**
     * 会话ID
     * Chat ID
     */
    private Long dialogId;

    /**
     * 聊天内容
     * Chat content
     */
    private String content;

    /**
     * 流
     */
    private Boolean stream = true;

}
