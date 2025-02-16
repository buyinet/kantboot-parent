package com.kantboot.business.ai.i18n.web.controller;

import com.kantboot.business.ai.i18n.service.IBusAiChatModelTypeI18nService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/chatModelTypeI18n")
public class BusAiChatModelTypeI18nController {

    @Resource
    private IBusAiChatModelTypeI18nService service;

    @RequestMapping("/getAll")
    public RestResult<?> getAll() {
        return RestResult.success(service.getAll(null), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
