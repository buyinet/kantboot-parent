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
     * 根据邮箱和密码登录
     * @param email 邮箱
     *              Email
     * @param password 密码
     *                 Password
     */
    LoginVO loginByEmailAndPassword(String email, String password);

    /**
     * 发送手机验证码
     * @param phoneAreaCode 手机区号
     * @param phone 手机号
     */
    void sendLoginVerificationCodeByPhone(String phoneAreaCode, String phone);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 应用内授权登录
     * @param userAccountId 用户账号ID
     *                      User account ID
     */
    LoginVO loginByUserAccountId(Long userAccountId);

}