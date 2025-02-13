package com.kantboot.api.qinucloud.service.impl;

import com.kantboot.api.qinucloud.service.IApiQiniuSmsService;
import com.kantboot.api.qinucloud.setting.ApiQiniuCloudSetting;
import com.kantboot.api.qinucloud.util.ApiQiniuCloudUtil;
import com.kantboot.util.event.EventEmit;
import com.kantboot.util.event.annotation.EventOn;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ApiQiniuSmsServiceImpl implements IApiQiniuSmsService {

    @Resource
    private ApiQiniuCloudSetting setting;

    @Resource
    private EventEmit eventEmit;

    @Override
    public void sendFulltextMessageOne(String phone, String content) {
        eventEmit.to("qiniucloudSendFulltextMessageOne", phone, content);
    }

    @EventOn(value = "qiniucloudSendFulltextMessageOne")
    public void qiniucloudSendFulltextMessageOne(String phone, String content) {
        ApiQiniuCloudUtil.sendFulltextMessageOne(phone, content, setting.getQiniuAuth());
    }

}
