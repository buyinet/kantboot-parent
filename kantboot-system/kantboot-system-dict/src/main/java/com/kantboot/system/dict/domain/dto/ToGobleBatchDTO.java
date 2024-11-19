package com.kantboot.system.dict.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ToGobleBatchDTO implements Serializable {

    List<ToGobleDTO> params;

    /**
     * 源语言编码
     */
    private String sourceLanguageCode;

    /**
     * 字典分组编码
     */
    private String dictGroupCode;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 目标语言编码
     */
    private String targetLanguageCode;

}
