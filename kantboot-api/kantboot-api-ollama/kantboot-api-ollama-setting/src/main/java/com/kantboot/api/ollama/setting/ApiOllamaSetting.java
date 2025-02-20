package com.kantboot.api.ollama.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApiOllamaSetting {

    @Resource
    private ISysSettingService settingService;

    /**
     * ollama的api的baseUrl
     */
    public String getBaseUrl() {
        String value = settingService.getValue("apiOllamaBaseUrl");
        if(value==null){
            throw BaseException.of("ollamaBaseUrlNotConfig","Ollama's api baseUrl not config");
        }
        // 如果末尾不带/，则加上/
        if(!value.endsWith("/")){
            value+="/";
        }
        return value;
    }

    /**
     * ollama的api的默认模型
     */
    public String getDefaultModel() {
        String value = settingService.getValue("apiOllamaDefaultModel");
        if(value==null){
            throw BaseException.of("ollamaDefaultModelNotConfig","Ollama's api default model not config");
        }
        return value;
    }

}
