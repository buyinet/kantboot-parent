package com.kantboot.business.ai.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 聊天传输
 * Chat transmission
 * 用于将数据流传输
 * Used to transmit data of streaming
 */
@Data
@Accessors(chain = true)
public class ChatTransmissionVO implements Serializable {

    /**
     * 会话ID
     * Dialog ID
     */
    private Long dialogId;

    /**
     * 角色
     * Role
     */
    private String role;

    /**
     * 用户ID
     * User ID
     */
    private Long userId;

    /**
     * 消息ID
     * Message ID
     */
    private Long dialogMessageId;

    /**
     * 内容
     * Content
     */
    private String content;

    /**
     * 文本
     * Text
     */
    private String text;

    /**
     * 是否结束
     * Whether to end
     */
    private Boolean done;

}
