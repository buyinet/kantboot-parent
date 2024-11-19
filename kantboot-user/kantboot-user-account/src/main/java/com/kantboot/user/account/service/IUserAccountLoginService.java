package com.kantboot.user.account.service;

import com.kantboot.user.account.domain.vo.LoginVO;

public interface IUserAccountLoginService {

    /**
     * 根据用户名和密码登录
     * @param username 用户名
     * @param password 密码
     * @return 登录信息
     */
    LoginVO loginByUsernameAndPassword(String username, String password);

    /**
     * 发送手机验证码
     * @param phoneAreaCode 手机区号
     * @param phone 手机号
     */
    void sendPhoneVerificationCode(String phoneAreaCode, String phone);

    /**
     * 退出登录
     */
    void logout();

}