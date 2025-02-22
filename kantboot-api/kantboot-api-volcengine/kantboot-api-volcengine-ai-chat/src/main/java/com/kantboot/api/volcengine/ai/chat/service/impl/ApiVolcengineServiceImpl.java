package com.kantboot.api.volcengine.ai.chat.service.impl;

import com.kantboot.api.ollama.setting.ApiVolcengineSetting;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineBase;
import com.kantboot.api.volcengine.ai.chat.method.ApiVolcengineMethod;
import com.kantboot.api.volcengine.ai.chat.service.IApiVolcengineService;
import com.kantboot.api.volcengine.ai.chat.util.ApiVolcengineRequestUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ApiVolcengineServiceImpl implements IApiVolcengineService {

    @Resource
    private ApiVolcengineSetting setting;

    @Override
    public void chatHasStream(ApiVolcengineBase dto, ApiVolcengineMethod method) {
        dto.setStream(true);
        if(dto.getModel()==null){
            dto.setModel(setting.getDefaultModel());
        }
        String url = setting.getBaseUrl()+"/api/v3/chat/completions";
        ApiVolcengineRequestUtil.requestChatHasStream(dto, url, method,setting.getAppKey());
    }

}
