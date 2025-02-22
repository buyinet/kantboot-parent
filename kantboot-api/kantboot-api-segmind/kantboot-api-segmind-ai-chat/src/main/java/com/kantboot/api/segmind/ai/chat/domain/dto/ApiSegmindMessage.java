package com.kantboot.api.segmind.ai.chat.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ApiSegmindMessage implements Serializable {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

}
