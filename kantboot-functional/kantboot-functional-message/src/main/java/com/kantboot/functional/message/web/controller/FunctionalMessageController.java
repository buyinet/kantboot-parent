package com.kantboot.functional.message.web.controller;

import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.functional.message.service.IFunctionalMessageService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-message-web/message")
public class FunctionalMessageController {

    @Resource
    private IFunctionalMessageService service;

    @RequestMapping("/sendMessage")
    public RestResult<?> sendMessage(@RequestBody MessageDTO dto) {
        service.sendMessage(dto);
        return RestResult.success(null,CommonSuccessStateCodeAndMsg.SEND_SUCCESS);
    }

}
