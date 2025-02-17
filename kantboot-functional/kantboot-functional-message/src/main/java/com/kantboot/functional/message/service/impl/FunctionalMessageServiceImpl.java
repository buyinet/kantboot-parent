package com.kantboot.functional.message.service.impl;

import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.message.dao.repository.FunctionalMessageRepository;
import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.functional.message.domain.entity.FunctionalMessage;
import com.kantboot.functional.message.service.IFunctionalMessageService;
import com.kantboot.util.event.EventEmit;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FunctionalMessageServiceImpl implements IFunctionalMessageService {

    @Resource
    private FunctionalMessageRepository repository;

    @Resource
    private EventEmit eventEmit;

    @Override
    public FunctionalMessage sendMessage(MessageDTO dto) {
        FunctionalMessage message = new FunctionalMessage();
        message.setToolCode(dto.getToolCode());
        message.setUserAccountId(dto.getUserAccountId());
        message.setEmit(dto.getEmit());
        if(dto.getData()!=null){
            message.setDataStr(JSON.toJSONString(dto.getData()));
        }
        FunctionalMessage save = repository.save(message);
        eventEmit.to("functionalMessage:message:"+dto.getToolCode(),save);
        return save;
    }

}
