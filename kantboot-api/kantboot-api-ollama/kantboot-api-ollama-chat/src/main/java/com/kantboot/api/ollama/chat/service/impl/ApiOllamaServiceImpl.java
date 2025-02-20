package com.kantboot.api.ollama.chat.service.impl;

import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaAbstract;
import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaBase;
import com.kantboot.api.ollama.chat.service.IApiOllamaService;
import com.kantboot.api.ollama.chat.util.ApiOllamaRequestUtil;
import com.kantboot.api.ollama.setting.ApiOllamaSetting;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ApiOllamaServiceImpl implements IApiOllamaService {

    @Resource
    ApiOllamaSetting setting;

    @Override
    public void chatHasStream(ApiOllamaBase dto, ApiOllamaAbstract ollamaAbstract) {
        ApiOllamaRequestUtil.requestChatHasStream(dto, setting.getBaseUrl()+"api/chat", ollamaAbstract);
    }

    @Override
    public void generate(ApiOllamaBase dto) {
    }

}
