package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.service.IBusAiChatModelService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/chatModel")
public class BusAiChatModelController {

    @Resource
    private IBusAiChatModelService IBusAiChatModelService;

    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(
                IBusAiChatModelService.getById(id),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS
        );
    }

    @RequestMapping("/getByTypeId")
    public RestResult<?> getByTypeId(@RequestParam("typeId") Long typeId) {
        return RestResult.success(
                IBusAiChatModelService.getByTypeId(typeId),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getAll")
    public RestResult<?> getAll() {
        return RestResult.success(
                IBusAiChatModelService.getAll(),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }


}
