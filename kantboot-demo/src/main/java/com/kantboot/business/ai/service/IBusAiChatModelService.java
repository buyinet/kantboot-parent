package com.kantboot.business.ai.service;

import com.kantboot.business.ai.domain.dto.BusAiChatModelDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;

import java.util.List;

public interface IBusAiChatModelService {

    /**
     * 根据类型ID获取
     */
    List<BusAiChatModel> getByTypeId(Long typeId);

    /**
     * 根据ID获取
     */
    BusAiChatModel getById(Long id);


    void newSave(BusAiChatModelDTO dto);

}
