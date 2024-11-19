package com.kantboot.functional.template.web.controller;

import com.kantboot.functional.template.domain.vo.FunctionalTemplateGenerateVO;
import com.kantboot.functional.template.service.IFunctionalTemplateService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-template-web/template")
public class FunctionalTemplateController {

    @Resource
    private IFunctionalTemplateService service;

    @RequestMapping("/getTemplate")
    public RestResult<FunctionalTemplateGenerateVO> getTemplate(
            @RequestParam("code") String code,
            @RequestParam("params") Object params
    ) {
        return RestResult.success(service.getTemplate(code,params), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
