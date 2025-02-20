package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.domain.dto.DialogSearchDTO;
import com.kantboot.business.ai.service.IBusAiChatDialogService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/business-ai-web/chatDialog")
public class BusAiChatDialogController {

    @Resource
    private IBusAiChatDialogService service;

    @RequestMapping("/sendMessageOfStreamingResponse")
    public ResponseEntity<StreamingResponseBody> sendMessageOfStreamingResponse(@RequestBody BusAiChatDTO dto) {
        return service.sendMessageOfStreamingResponse(dto);
    }

    @RequestMapping("/sendMessage")
    public RestResult<?> sendMessage(@RequestBody BusAiChatDTO dto) {
        service.sendMessage(dto);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SEND_SUCCESS);
    }

    @RequestMapping("/createDialog")
    public RestResult<?> createChat(@RequestParam("modelId") Long modelId,
            @RequestParam("languageCode") String languageCode) {
        return RestResult.success(service.createChat(modelId, languageCode), CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    @RequestMapping("/getBySelf")
    public RestResult<?> getBySelf(@RequestBody DialogSearchDTO param) {
        return RestResult.success(service.getBySelf(param), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getModelById")
    public RestResult<?> getModelById(@RequestParam("id") Long id) {
        return RestResult.success(service.getModelById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
