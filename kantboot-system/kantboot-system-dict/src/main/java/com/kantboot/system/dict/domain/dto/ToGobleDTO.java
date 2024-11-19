package com.kantboot.system.dict.domain.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ToGobleDTO implements Serializable {

    private String text;

    private String sourceLanguageCode;

    private String dictGroupCode;

    private String dictCode;

    private String targetLanguageCode;

}
