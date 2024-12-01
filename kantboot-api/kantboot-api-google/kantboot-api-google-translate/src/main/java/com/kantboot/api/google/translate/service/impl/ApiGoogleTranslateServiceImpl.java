package com.kantboot.api.google.translate.service.impl;

import com.kantboot.api.google.setting.ApiGoogleSetting;
import com.kantboot.api.google.translate.dao.repository.ApiGoogleTranslateTextRepository;
import com.kantboot.api.google.translate.domain.entity.ApiGoogleTranslateText;
import com.kantboot.api.google.translate.service.IApiGoogleTranslateService;
import com.kantboot.api.google.translate.util.GoogleTranslateUtil;
import com.kantboot.system.dict.dao.repository.SysDictI18nRepository;
import com.kantboot.system.dict.dao.repository.SysDictRepository;
import com.kantboot.system.dict.service.ISysLanguageService;
import com.kantboot.util.cache.CacheUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ApiGoogleTranslateServiceImpl implements IApiGoogleTranslateService {

    @Resource
    private ApiGoogleSetting apiGoogleSetting;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private ApiGoogleTranslateTextRepository repository;

    @Resource
    private SysDictI18nRepository dictI18nRepository;

    @Resource
    private SysDictRepository dictRepository;

    @Resource
    private ISysLanguageService languageService;

    @Override
    public String translateText(String sourceLanguageCode, String targetLanguageCode, String text) {
        log.info("Google translate sourceLanguageCode:{},targetLanguageCode:{},text:{}",sourceLanguageCode,targetLanguageCode,text);
        if(sourceLanguageCode.equals(targetLanguageCode)){
            return text;
        }
        if(sourceLanguageCode.equals("zh_MO")){
            sourceLanguageCode = "zh-HK";
        }
        if(targetLanguageCode.equals("zh_MO")){
            targetLanguageCode = "zh-HK";
        }

        ApiGoogleTranslateText bySourceLanguageCodeAndTargetLanguageCodeAndSourceText =
                repository.findBySourceLanguageCodeAndTargetLanguageCodeAndSourceText(sourceLanguageCode, targetLanguageCode, text);
        if(bySourceLanguageCodeAndTargetLanguageCodeAndSourceText != null){
            return bySourceLanguageCodeAndTargetLanguageCodeAndSourceText.getTargetText();
        }

        try {
            String targetText = GoogleTranslateUtil.translateText(apiGoogleSetting.getGoogleProjectId(),
                    apiGoogleSetting.getGoogleCredJson(),
                    sourceLanguageCode,
                    targetLanguageCode,
                    text);
            ApiGoogleTranslateText apiGoogleTranslateText = new ApiGoogleTranslateText();
            apiGoogleTranslateText.setSourceLanguageCode(sourceLanguageCode);
            apiGoogleTranslateText.setTargetLanguageCode(targetLanguageCode);
            apiGoogleTranslateText.setSourceText(text);
            apiGoogleTranslateText.setTargetText(targetText);
            repository.save(apiGoogleTranslateText);
            return targetText;
        }catch (Exception e){
            // redis记录住异常次数
            // Record the number of exceptions in redis
            String key = "googleTranslateExceptionCount:"+sourceLanguageCode+":"+targetLanguageCode;
            String count = cacheUtil.get(key);
            if(count == null){
                count = "0";
            }
            int countInt = Integer.parseInt(count);
            // 如果次数大于5次，就不再调用google翻译
            // If the number of times is greater than 5, Google translation will not be called again
            if(countInt > 300){
                return "ERROR#TRANSLATE";
            }
            countInt++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            cacheUtil.setEx(key, countInt+"", 60*60*24);
            return translateText(sourceLanguageCode,targetLanguageCode,text);
        }
    }


}
