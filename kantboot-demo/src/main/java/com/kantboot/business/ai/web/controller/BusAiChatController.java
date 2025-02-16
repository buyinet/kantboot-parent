package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.service.IBusAiChatService;
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
@RequestMapping("/business-ai-web/chat")
public class BusAiChatController {

    @Resource
    private IBusAiChatService service;

    @RequestMapping("/sendMessage")
    public ResponseEntity<StreamingResponseBody> sendMessage(@RequestBody BusAiChatDTO dto) {
        return service.sendMessage(dto);
    }

    @RequestMapping("/createChat")
    public RestResult<?> createChat(@RequestParam("modelId") Long modelId,
            @RequestParam("languageCode") String languageCode) {
        service.createChat(modelId, languageCode);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

}
