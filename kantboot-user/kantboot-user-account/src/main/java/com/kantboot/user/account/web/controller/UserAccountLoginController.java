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


    /**
     * 根据手机号和验证码登录
     * @param phoneAreaCode 手机号前缀
     *                      Phone number prefix
     * @param phone 手机号
     *              Phone number
     * @param verifyCode 验证码
     *                   Verification code
     */
    @RequestMapping("/loginByPhoneVerifyCode")
    public RestResult<LoginVO> loginByPhoneVerifyCode(
            @RequestParam("phoneAreaCode") String phoneAreaCode,
            @RequestParam("phone") String phone,
            @RequestParam("verifyCode") String verifyCode) {
        return RestResult.success(service.loginByPhoneVerifyCode(phoneAreaCode, phone, verifyCode),
                CommonSuccessStateCodeAndMsg.LOGIN_SUCCESS);
    }

    /**
     * 根据邮箱和验证码登录
     * @param email 邮箱
     *              Email
     * @param verifyCode 验证码
     *                   Verification code
     */
    @RequestMapping("/loginByEmailVerifyCode")
    public RestResult<LoginVO> loginByEmailVerifyCode(
            @RequestParam("email") String email,
            @RequestParam("verifyCode") String verifyCode) {
        return RestResult.success(service.loginByEmailVerifyCode(email, verifyCode),
                CommonSuccessStateCodeAndMsg.LOGIN_SUCCESS);
    }

    /**
     * 发送登录手机登录验证码
     * @param phoneAreaCode 手机号前缀
     *                      Phone number prefix
     * @param phone 手机号
     *              Phone number
     */
    @RequestMapping("/sendLoginVerifyCodeByPhone")
    public RestResult<Void> sendLoginVerifyCodeByPhone(
            @RequestParam("phoneAreaCode") String phoneAreaCode,
            @RequestParam("phone") String phone) {
        service.sendLoginVerifyCodeByPhone(phoneAreaCode, phone);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }

    /**
     * 发送登录邮箱登录验证码
     * @param email 邮箱
     *              Email
     */
    @RequestMapping("/sendLoginVerifyCodeByEmail")
    public RestResult<Void> sendLoginVerifyCodeByEmail(
            @RequestParam("email") String email) {
        service.sendLoginVerifyCodeByEmail(email);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }


}
