package com.kantboot.util.data.change.controller;

import com.kantboot.util.data.change.service.IDataChangeService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/util-data-change-web/dataChange")
public class DataChangeController {

    @Resource
    private IDataChangeService dataChangeService;

    @PostMapping("/getUuidByKey")
    public RestResult<String> getUuidByKey(@RequestParam("key") String key) {
        return RestResult.success(
                dataChangeService.getUuidByKey(key),
                CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
