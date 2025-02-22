package com.kantboot.api.segmind.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ApiSegmindSetting {

    @Resource
    private ISysSettingService settingService;

    /**
     * api的baseUrl
     */
    public String getBaseUrl() {
        String value = settingService.getValue("apiSegmindBaseUrl");
        if(value==null){
            throw BaseException.of("ollamaBaseUrlNotConfig","Segmind's api baseUrl not config");
        }
        return value;
    }

    /**
     * 默认模型
     */
    public String getDefaultModel() {
        String value = settingService.getValue("apiSegmindDefaultModel");
        if(value==null){
            throw BaseException.of("ollamaDefaultModelNotConfig","Segmind's api default model not config");
        }
        return value;
    }

    /**
     * app的Key
     */
    public String getAppKey() {
        String value = settingService.getValue("apiSegmindAppKey");
        if(value==null){
            throw BaseException.of("ollamaAppKeyNotConfig","Segmind's api app key not config");
        }
        return value;
    }

}
