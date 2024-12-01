package com.kantboot.api.google.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 谷歌设置
 * Google setting
 * @author 方某方
 */
@Service
public class ApiGoogleSetting {

    @Resource
    private ISysSettingService settingsService;

    /**
     * 获取谷歌的项目ID
     * Get the Google project ID
     */
    public String getGoogleProjectId() {
        return settingsService.getValue("googleProjectId");
    }

    /**
     * 获取谷歌的凭证json
     * Get the Google credential JSON
     */
    public String getGoogleCredJson(){
        return settingsService.getValue("googleCredJson");
    }

    /**
     * 谷歌的密钥
     * Google secret key
     */
    public String getGoogleSecretKey() {
        return settingsService.getValue("googleSecretKey");
    }

}
