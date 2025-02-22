package com.kantboot.business.ai.i18n.web.controller;

import com.kantboot.business.ai.i18n.service.IBusAiChatModelI18nService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business-ai-web/chatModelI18n")
public class BusAiChatModelI18nController {

    @Resource
    private IBusAiChatModelI18nService service;

    @RequestMapping("/getByBodyIds")
    public RestResult<?> getByBodyIdList(@RequestParam("bodyIds") List<Long> bodyIds) {
        return RestResult.success(service.getByBodyIds(bodyIds,null), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getAll")
    public RestResult<?> getAll(@RequestParam("languageCode") String languageCode) {
        return RestResult.success(service.getAll(null), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }


}
