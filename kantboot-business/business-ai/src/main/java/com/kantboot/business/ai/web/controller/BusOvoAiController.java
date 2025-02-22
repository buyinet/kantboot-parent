package com.kantboot.business.ai.web.controller;

import com.kantboot.api.ollama.chat.service.IApiOllamaService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/ai")
public class BusOvoAiController {

    @Resource
    private IApiOllamaService ollamaService;


}
