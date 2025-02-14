package com.kantboot.business.ai.i18n.service;

import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelTypeI18n;

import java.util.List;

public interface IBusAiChatModelTypeI18nService {

    /**
     * 获取所有类型的国际化信息
     */
    List<BusAiChatModelTypeI18n> getAll(String languageCode);

}
