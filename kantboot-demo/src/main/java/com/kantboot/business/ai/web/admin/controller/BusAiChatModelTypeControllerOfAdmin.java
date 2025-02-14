package com.kantboot.business.ai.web.admin.controller;

import com.kantboot.business.ai.domain.entity.BusAiChatModelType;
import com.kantboot.business.ai.repository.BusAiChatModelTypeRepository;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.event.EventEmit;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.exception.BaseException;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-ai-web/admin/chatModelType")
public class BusAiChatModelTypeControllerOfAdmin extends BaseAdminController<BusAiChatModelType, Long> {

    @Resource
    private EventEmit eventEmit;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private BusAiChatModelTypeRepository repository;


    @RequestMapping("/save")
    @Override
    public RestResult<Object> save(BusAiChatModelType busAiChatModelType) {

        if(busAiChatModelType.getId() != null
                &&cacheUtil.hasKey("busAiChatModelType:save:"+busAiChatModelType.getId())){
            // 提示正在操作中
            throw BaseException.of("typeInOperation","The operation is being processed");
        }
        BusAiChatModelType save = repository.save(busAiChatModelType);
        eventEmit.to("busAiChatModelType:save",save.getId());
        return RestResult.success(save, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

}
