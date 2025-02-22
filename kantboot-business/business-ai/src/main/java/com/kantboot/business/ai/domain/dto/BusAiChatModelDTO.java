package com.kantboot.business.ai.domain.dto;

import com.kantboot.business.ai.domain.entity.BusAiChatModelLabel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.domain.entity.BusAiChatModelSetting;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BusAiChatModelDTO implements Serializable {

    /**
     * 模型ID
     * Model ID
     */
    private Long id;

    /**
     * 模型名称
     * Model name
     */
    private String name;

    /**
     * 模型分类ID
     * Model type ID
     */
    private Long typeId;

    /**
     * 模型描述
     * Model description
     */
    private String description;

    /**
     * 介绍
     */
    private String introduction;

    /**
     * 生日
     */
    private String gmtBirthday;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 源语言编码
     */
    private String sourceLanguageCode;

    List<BusAiChatModelLabel> labels;

    BusAiChatModelSetting setting;

    List<BusAiChatModelPresets> presets;

    List<BusAiChatModelLanguageSupport> languageSupports;


}
