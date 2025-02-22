package com.kantboot.api.segmind.ai.chat.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ApiSegmindBase implements Serializable {

    /**
     * 模型名称
     */
    private String model;

    /**
     * 提示词
     */
    private String prompt;

    /**
     * 是否开启流式
     */
    private Boolean stream;

    /**
     * 消息列表
     */
    private List<ApiSegmindMessage> messages;

    /**
     * 是否重连
     */
    private Boolean isConnect;

}
