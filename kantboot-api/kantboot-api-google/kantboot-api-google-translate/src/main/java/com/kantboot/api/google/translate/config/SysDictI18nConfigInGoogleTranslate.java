package com.kantboot.api.google.translate.config;

import com.kantboot.api.google.translate.service.IApiGoogleTranslateService;
import com.kantboot.system.dict.slot.SysDictI18nSlot;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysDictI18nConfigInGoogleTranslate {

    @Resource
    private IApiGoogleTranslateService service;

    @Bean
    public SysDictI18nSlot sysDictI18nSlot(){
        return new SysDictI18nSlot(){
            @Override
            public String translateText(String sourceLanguageCode, String targetLanguage, String text) {
                return service.translateText(sourceLanguageCode, targetLanguage, text);
            }
        };
    }

}
