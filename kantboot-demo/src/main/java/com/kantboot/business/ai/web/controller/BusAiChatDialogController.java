package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
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
        return RestResult.success(service.sendMessage(dto), CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    @RequestMapping("/createDialog")
    public RestResult<?> createChat(@RequestParam("modelId") Long modelId,
            @RequestParam("languageCode") String languageCode) {
        service.createChat(modelId, languageCode);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

}
