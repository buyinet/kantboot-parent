package com.kantboot.api.ollama.chat.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ApiOllamaMessage implements Serializable {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

}
