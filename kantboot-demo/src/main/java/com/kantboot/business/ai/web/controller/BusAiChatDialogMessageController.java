package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.domain.dto.DialogMessageSearchDTO;
import com.kantboot.business.ai.service.IBusAiChatDialogMessageService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus-ai-web/chatDialogMessage")
public class BusAiChatDialogMessageController {

    @Resource
    private IBusAiChatDialogMessageService service;

    @RequestMapping("/getList")
    public RestResult<?> getList(@RequestBody DialogMessageSearchDTO param) {
        return RestResult.success(service.getList(param), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
