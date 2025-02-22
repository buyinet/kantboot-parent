package com.kantboot.api.segmind.ai.chat.service;

import com.kantboot.api.segmind.ai.chat.domain.dto.ApiSegmindBase;
import com.kantboot.api.segmind.ai.chat.method.ApiSegmindMethod;

public interface IApiSegmindService {

    void chatHasStream(ApiSegmindBase dto, ApiSegmindMethod method);

}
