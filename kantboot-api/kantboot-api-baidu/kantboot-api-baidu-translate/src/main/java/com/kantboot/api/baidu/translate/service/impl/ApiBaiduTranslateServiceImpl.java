package com.kantboot.api.baidu.translate.service.impl;

import com.kantboot.api.baidu.translate.service.IApiBaiduTranslateService;
import com.kantboot.api.baidu.translate.setting.ApiBaiduTranslateSetting;
import com.kantboot.api.baidu.translate.util.BaiduTranslateUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiBaiduTranslateServiceImpl implements IApiBaiduTranslateService {

    @Resource
    private ApiBaiduTranslateSetting setting;

    @Override
    public String translateText(String sourceLanguageCode, String targetLanguageCode, String text) {
        log.info("Baidu translateText sourceLanguageCode:{} targetLanguageCode:{} text:{}", sourceLanguageCode, targetLanguageCode, text);
        return BaiduTranslateUtil.translate(text,
                BaiduTranslateUtil.toBaiduCode(sourceLanguageCode),
                BaiduTranslateUtil.toBaiduCode(targetLanguageCode),
                setting.getAppid(),
                setting.getSecret());
    }

}
