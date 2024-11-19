package com.kantboot.functional.template.web.admin.controller;

import com.kantboot.functional.template.domain.entity.FunctionalTemplateGroup;
import com.kantboot.functional.template.service.IFunctionalTemplateGroupService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/functional-template-web/admin/functionalTemplateGroup")
public class FunctionalTemplateGroupControllerOfAdmin extends BaseAdminController<FunctionalTemplateGroup,Long> {

    @Resource
    private IFunctionalTemplateGroupService service;

    @PostMapping("/getList")
    public RestResult<List<FunctionalTemplateGroup>> getList() {
        return RestResult.success(service.getList(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
