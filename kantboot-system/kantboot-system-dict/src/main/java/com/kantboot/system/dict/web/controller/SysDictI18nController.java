package com.kantboot.system.dict.web.controller;

import com.kantboot.system.dict.domain.dto.ToGobleBatchDTO;
import com.kantboot.system.dict.domain.dto.ToGobleBatchTextDTO;
import com.kantboot.system.dict.domain.entity.SysDictI18n;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典国际化控制器
 */
@RestController
@RequestMapping("/system-dict-web/dictI18n")
public class SysDictI18nController {

    @Resource
    private ISysDictI18nService service;

    @RequestMapping("/getDict")
    public RestResult<List<SysDictI18n>> getDict(
            @RequestParam("languageCode") String languageCode,
            @RequestParam("dictGroupCode") String dictGroupCode) {
        List<SysDictI18n> dict = service.getDictList(languageCode, dictGroupCode);
        return RestResult.success(dict, CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/translateText")
    public RestResult<String> translateText(
            @RequestParam("sourceLanguageCode") String sourceLanguageCode,
            @RequestParam("targetLanguageCode") String targetLanguageCode,
            @RequestParam("text") String text){
        return RestResult.success(service.translateText(sourceLanguageCode, targetLanguageCode, text),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/toGoble")
    public RestResult<List<SysDictI18n>> toGoble(
            @RequestParam("text") String text,
            @RequestParam("sourceLanguageCode") String sourceLanguageCode,
            @RequestParam("dictGroupCode") String dictGroupCode,
            @RequestParam("dictCode") String dictCode) {
        List<SysDictI18n> goble = service.toGoble(text, sourceLanguageCode, dictGroupCode, dictCode);
        return RestResult.success(goble, CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/toGobleBatch")
    public RestResult<Void> toGobleBatch(@RequestBody ToGobleBatchDTO toGobleBatchDTO) {
        service.toGobleBatch(toGobleBatchDTO);
        return RestResult.success(null,CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/toGlobalBatchText")
    public RestResult<Void> toGlobalBatchText(@RequestBody ToGobleBatchTextDTO toGobleBatchTextDTO) {
        service.toGlobalBatchText(toGobleBatchTextDTO);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
