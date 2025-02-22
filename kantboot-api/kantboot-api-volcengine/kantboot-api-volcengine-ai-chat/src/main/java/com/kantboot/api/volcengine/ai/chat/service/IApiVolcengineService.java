package com.kantboot.api.volcengine.ai.chat.service;

import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineBase;
import com.kantboot.api.volcengine.ai.chat.method.ApiVolcengineMethod;

public interface IApiVolcengineService {

    void chatHasStream(ApiVolcengineBase dto, ApiVolcengineMethod method);

}
