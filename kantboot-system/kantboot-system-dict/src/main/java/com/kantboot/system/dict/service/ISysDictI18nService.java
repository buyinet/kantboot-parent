package com.kantboot.system.dict.service;

import com.kantboot.system.dict.domain.dto.ToGobleBatchDTO;
import com.kantboot.system.dict.domain.dto.ToGobleBatchTextDTO;
import com.kantboot.system.dict.domain.dto.ToGobleDTO;
import com.kantboot.system.dict.domain.entity.SysDictI18n;

import java.util.List;

/**
 * 字典国际化服务接口
 */
public interface ISysDictI18nService {

    /**
     * 根据语言和分组编码查询国际化字典
     */
    List<SysDictI18n> getDictList(String languageCode, String dictGroupCode);

    /**
     * 根据语言编码、字典组编码、字典编码查询国际化字典
     */
    String getDictText(String languageCode, String dictGroupCode, String dictCode);

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

    /**
     * 根据 语言code、分组code、编码code翻译，并放入国际化字典
     * Translate according to language code, group code, and code, and put it into the internationalization dictionary
     * @param text 文本
     *             Text
     * @param sourceLanguageCode 源语言编码
     *                           Source language code
     * @param dictGroupCode 字典组编码
     *                      Dictionary group code
     * @param dictCode 字典编码
     *                 Dictionary code
     */
    List<SysDictI18n> toGoble(String text, String sourceLanguageCode, String dictGroupCode, String dictCode);

    /**
     * 批量翻译
     * @param toGobleBatchDTO 批量翻译的DTO
     *                        DTO of batch translation
     *
     */
    void toGobleBatch(ToGobleBatchDTO toGobleBatchDTO);


    /**
     * 批量翻译
     * @param toGobleBatchTextDTO 批量翻译的DTO
     *                            DTO of batch translation
     */
    void toGlobalBatchText(ToGobleBatchTextDTO toGobleBatchTextDTO);

    String getTargetLanguageText(ToGobleDTO dto);

}
