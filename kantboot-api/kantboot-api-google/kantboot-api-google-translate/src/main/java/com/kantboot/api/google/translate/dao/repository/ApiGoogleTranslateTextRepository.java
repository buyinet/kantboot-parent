package com.kantboot.api.google.translate.dao.repository;

import com.kantboot.api.google.translate.domain.entity.ApiGoogleTranslateText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiGoogleTranslateTextRepository
    extends JpaRepository<ApiGoogleTranslateText, Long> {

    /**
     * 根据源语言编码、目标语言编码、原文本查询翻译文本
     */
    ApiGoogleTranslateText findBySourceLanguageCodeAndTargetLanguageCodeAndSourceText(
        String sourceLanguageCode, String targetLanguageCode, String sourceText);

}
