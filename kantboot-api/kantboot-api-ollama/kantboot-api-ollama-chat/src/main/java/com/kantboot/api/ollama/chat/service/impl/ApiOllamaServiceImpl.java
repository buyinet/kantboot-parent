package com.kantboot.api.ollama.chat.service.impl;

import com.kantboot.api.ollama.chat.method.ApiOllamaMethod;
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
    public void chatHasStream(ApiOllamaBase dto, ApiOllamaMethod ollamaAbstract) {
        if(dto.getModel()==null){
            // 设置默认模型
            dto.setModel(setting.getDefaultModel());
        }
        if(dto.getStream()==null){
            // 设置默认流式
            dto.setStream(true);
        }
        String url = setting.getBaseUrl()+"/api/chat";
        ApiOllamaRequestUtil.requestChatHasStream(dto, url, ollamaAbstract);
    }

    @Override
    public void generate(ApiOllamaBase dto) {
    }

}
