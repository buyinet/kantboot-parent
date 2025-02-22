package com.kantboot.business.ai.i18n.service;

import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelI18n;

import java.util.List;

public interface IBusAiChatModelI18nService {

    /**
     * 根据bodyIdList获取
     */
    List<BusAiChatModelI18n> getByBodyIds(List<Long> bodyIds, String languageCode);

    /**
     * 查询所有
     */
    List<BusAiChatModelI18n> getAll(String languageCode);

}
