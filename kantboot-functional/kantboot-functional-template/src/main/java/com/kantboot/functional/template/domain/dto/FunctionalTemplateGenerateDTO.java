package com.kantboot.functional.template.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FunctionalTemplateGenerateDTO implements Serializable {

    /**
     * 编码
     * code
     */
    private String code;

    /**
     * 参数
     * params
     */
    private Object params;
}
