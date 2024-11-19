package com.kantboot.system.dict.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ToGobleBatchTextDTO implements Serializable {

    List<String> texts;

    /**
     * 源语言编码
     */
    private String sourceLanguageCode;

    private String dictGroupCode;

}
