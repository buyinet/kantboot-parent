package com.kantboot.api.ollama.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApiVolcengineSetting {


    @Resource
    private ISysSettingService settingService;

    /**
     * api的baseUrl
     */
    public String getBaseUrl() {
        String value = settingService.getValue("apiVolcengineBaseUrl");
        if(value==null){
            throw BaseException.of("ollamaBaseUrlNotConfig","Volcengine's api baseUrl not config");
        }
        return value;
    }

    /**
     * 默认模型
     */
    public String getDefaultModel() {
        String value = settingService.getValue("apiVolcengineDefaultModel");
        if(value==null){
            throw BaseException.of("ollamaDefaultModelNotConfig","Volcengine's api default model not config");
        }
        return value;
    }

    /**
     * app的Key
     */
    public String getAppKey() {
        String value = settingService.getValue("apiVolcengineAppKey");
        if(value==null){
            throw BaseException.of("ollamaAppKeyNotConfig","Volcengine's api app key not config");
        }
        return value;
    }

    /**
     * accessToken
     */
    public String getAccountKey() {
        String value = settingService.getValue("apiVolcengineAccountKey");
        if (value == null) {
            throw BaseException.of("ollamaAccountKeyNotConfig", "Volcengine's api account key not config");
        }
        return value;
    }

}
