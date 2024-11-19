package com.kantboot.api.qinucloud.setting;

import com.kantboot.system.setting.service.ISysSettingService;
import com.qiniu.util.Auth;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 七牛云配置
 * Qiniu Cloud Setting
 */
@Component
public class ApiQiniuCloudSetting {

    @Resource
    private ISysSettingService settingService;

    /**
     * 获取七牛云的Auth
     * Get the Auth of Qiniu Cloud
     */
    public Auth getQiniuAuth(){
        String accessKey = getAccessKey();
        String secretKey = getSecretKey();
        return Auth.create(accessKey, secretKey);
    }


    /**
     * 获取七牛云的accessKey
     * Get the accessKey of Qiniu Cloud
     */
    public String getAccessKey(){
        return settingService.getValue("apiQiniuCloudAccessKey");
    }

    /**
     * 获取七牛云的secretKey
     * Get the secretKey of Qiniu Cloud
     */
    public String getSecretKey(){
        return settingService.getValue("apiQiniuCloudSecretKey");
    }


}
