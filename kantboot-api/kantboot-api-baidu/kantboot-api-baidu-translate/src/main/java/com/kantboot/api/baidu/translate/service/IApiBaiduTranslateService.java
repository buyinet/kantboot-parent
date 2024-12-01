package com.kantboot.api.baidu.translate.service;

public interface IApiBaiduTranslateService {

    /**
     * 翻译文本
     * Translate text
     * @param sourceLanguageCode 源语言编码
     *                           Source language code
     * @param targetLanguageCode 目标语言
     *                       Target language code
     * @param text 文本
     *             Text
     */
    String translateText(String sourceLanguageCode, String targetLanguageCode, String text);

}