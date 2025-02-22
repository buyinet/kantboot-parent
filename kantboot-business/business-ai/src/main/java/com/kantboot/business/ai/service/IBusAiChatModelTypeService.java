package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.entity.BusAiChatModelType;

import java.util.List;

public interface IBusAiChatModelTypeService {

    /**
     * 获取所有类型
     */
    List<BusAiChatModelType> getAll();

    /**
     * 根据ID获取类型
     */
    BusAiChatModelType getById(Long id);

}
