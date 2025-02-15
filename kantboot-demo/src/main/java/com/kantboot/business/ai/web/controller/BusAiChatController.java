package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.service.IBusAiChatService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
