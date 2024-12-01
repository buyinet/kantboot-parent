package com.kantboot.api.baidu.translate.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ApiBaiduTranslateSetting {

    @Resource
    private ISysSettingService settingService;

    /**
     * 获取百度翻译的appid
     * Get the appid of Baidu translation
     *
     * @return appid
     */
    public String getAppid() {
        return settingService.getValue("baiduTranslateAppid");
    }

    /**
     * 获取百度翻译的secret
     * Get the secret of Baidu translation
     *
     * @return secret
     */
    public String getSecret() {
        return settingService.getValue("baiduTranslateSecret");
    }

}