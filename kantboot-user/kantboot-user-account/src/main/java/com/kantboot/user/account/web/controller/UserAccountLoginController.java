package com.kantboot.user.account.web.controller;

import com.kantboot.user.account.domain.vo.LoginVO;
import com.kantboot.user.account.service.IUserAccountLoginService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账号登录控制器
 */
@RestController
@RequestMapping("/user-account-web/userAccountLogin")
public class UserAccountLoginController {

    @Resource
    private IUserAccountLoginService service;

    /**
     * 根据用户名和密码登录
     * 通常不开放
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping("/loginByUsernameAndPassword")
    public RestResult<LoginVO> loginByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return RestResult.success(service.loginByUsernameAndPassword(username, password),
                CommonSuccessStateCodeAndMsg.LOGIN_SUCCESS);
    }

    /**
     * 根据邮箱和密码登录
     * @param email 邮箱
     *              Email
     * @param password 密码
     *                 Password
     */
    @RequestMapping("/loginByEmailAndPassword")
    public RestResult<LoginVO> loginByEmailAndPassword(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        return RestResult.success(service.loginByEmailAndPassword(email, password),
                CommonSuccessStateCodeAndMsg.LOGIN_SUCCESS);
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public RestResult<Void> logout() {
        service.logout();
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.LOGOUT_SUCCESS);
    }

}
