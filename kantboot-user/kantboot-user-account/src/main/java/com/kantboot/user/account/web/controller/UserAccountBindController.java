package com.kantboot.user.account.web.controller;

import com.kantboot.user.account.service.IUserAccountBindService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账号绑定控制器
 */
@RestController
@RequestMapping("/user-account-web/userAccountBind")
public class UserAccountBindController {

    @Resource
    private IUserAccountBindService service;

    /**
     * 跳过绑定
     */
    @RequestMapping("/skipBind")
    public RestResult<Void> skipBind() {
        service.skipBind();
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SKIP_BIND_SUCCESS);
    }

}
