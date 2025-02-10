package com.kantboot.user.online.web.controller;

import com.kantboot.user.online.service.IUserAccountOnlineService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-online-web/userAccountOnline")
public class UserAccountOnlineController {

    @Resource
    private IUserAccountOnlineService service;

    /**
     * 心跳自己
     * @return RestResult<Void>
     */
    @RequestMapping("/heartbeatSelf")
    public RestResult<Void> heartbeatSelf() {
        service.heartbeatSelf();
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    /**
     * 对自己进行隐身
     * @param isInvisible 是否隐身
     * @return RestResult<Void>
     */
    @RequestMapping("/invisibleSelf")
    public RestResult<Void> invisibleSelf(@RequestParam Boolean isInvisible) {
        service.invisibleSelf(isInvisible);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    /**
     * 对自己进行上线
     */
    @RequestMapping("/onlineSelf")
    public RestResult<Void> onlineSelf() {
        service.onlineSelf();
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    /**
     * 对自己进行离线
     */
    @RequestMapping("/offlineSelf")
    public RestResult<Void> offlineSelf() {
        service.offlineSelf();
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

}
