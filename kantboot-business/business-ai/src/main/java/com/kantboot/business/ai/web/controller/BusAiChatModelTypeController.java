package com.kantboot.business.ai.web.controller;

import com.kantboot.business.ai.service.IBusAiChatModelTypeService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/chatModelType")
public class BusAiChatModelTypeController {

    @Resource
    private IBusAiChatModelTypeService service;

    /**
     * 获取所有类型
     * @return 所有类型
     */
    @RequestMapping("/getAll")
    public RestResult<?> getAll(){
        return RestResult.success(service.getAll(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }


}
