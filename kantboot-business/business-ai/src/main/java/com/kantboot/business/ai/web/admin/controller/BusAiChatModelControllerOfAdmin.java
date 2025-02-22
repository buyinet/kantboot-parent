package com.kantboot.business.ai.web.admin.controller;

import com.kantboot.business.ai.domain.dto.BusAiChatModelDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.service.IBusAiChatModelService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/admin/chatModel")
public class BusAiChatModelControllerOfAdmin extends BaseAdminController<BusAiChatModel, Long> {

    @Resource
    private IBusAiChatModelService service;

    @RequestMapping("/newSave")
    public RestResult<Object> newSave(@RequestBody BusAiChatModelDTO entity) {
        service.newSave(entity);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

}
