package com.kantboot.system.dict.web.controller;

import com.kantboot.system.dict.domain.entity.SysDictI18n;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
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
        List<SysDictI18n> dict = service.getDict(languageCode, dictGroupCode);
        return RestResult.success(dict, CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
