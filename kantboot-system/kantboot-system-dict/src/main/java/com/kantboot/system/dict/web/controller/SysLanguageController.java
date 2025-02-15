package com.kantboot.system.dict.web.controller;

import com.kantboot.system.dict.domain.entity.SysLanguage;
import com.kantboot.system.dict.domain.entity.SysLanguageLocalized;
import com.kantboot.system.dict.service.ISysLanguageService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system-dict-web/language")
public class SysLanguageController {

    @Resource
    private ISysLanguageService service;

    @RequestMapping("/getBySupport")
    public RestResult<List<SysLanguage>> getBySupport() {
        return RestResult.success(service.getBySupport(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getAll")
    public RestResult<List<SysLanguage>> getAll() {
        return RestResult.success(service.getAll(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 获取所有的语言本地化信息
     * @return 语言本地化信息列表
     */
    @RequestMapping("/getLocalizedList")
    public RestResult<List<SysLanguageLocalized>> getLocalizes() {
        return RestResult.success(service.getLocalizedList(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
