package com.kantboot.api.ollama.chat.service;

import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaAbstract;
import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaBase;

public interface IApiOllamaService {

    /**
     * 调用chat接口
     */
    void chatHasStream(ApiOllamaBase dto, ApiOllamaAbstract ollamaAbstract);

    /**
     * 调用generate接口
     */
    void generate(ApiOllamaBase dto);

}
