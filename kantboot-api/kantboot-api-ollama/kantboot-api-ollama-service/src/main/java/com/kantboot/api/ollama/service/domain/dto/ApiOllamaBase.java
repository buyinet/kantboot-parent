package com.kantboot.api.ollama.service.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

// TODO 之后完善
@Data
@Accessors(chain = true)
public class ApiOllamaBase implements Serializable {

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

}
