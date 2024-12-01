package com.kantboot.api.google.translate.web.controller;

import com.kantboot.api.google.translate.service.IApiGoogleTranslateService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-google-translate-web/translate")
public class GoogleTranslateController{

    @Resource
    private IApiGoogleTranslateService service;

    /**
     * 翻译文本
     * Translate text
     *
     * @param sourceLanguageCode 源语言编码
     *                           Source language code
     * @param targetLanguage 目标语言
     *                       Target language
     * @param text 文本
     *             Text
     *
     */
    @RequestMapping(value = "/translateText")
    public RestResult<String> translateText(
            @RequestParam("sourceLanguageCode") String sourceLanguageCode,
            @RequestParam("targetLanguage") String targetLanguage,
            @RequestParam("text") String text){
        return RestResult.success(service.translateText(sourceLanguageCode, targetLanguage, text),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
