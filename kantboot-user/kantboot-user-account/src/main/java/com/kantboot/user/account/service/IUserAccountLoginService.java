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
     * 发送邮箱验证码
     * @param email 邮箱
     */
    void sendLoginVerificationCodeByEmail(String email);

    /**
     * 验证手机验证码是否登录成功
     * @param phoneAreaCode 手机区号
     *                      Phone area code
     * @param phone 手机号
     *              Phone
     *
     * @param verifyCode 验证码
     *                   Verify code
     */
    LoginVO loginByPhoneVerificationCode(String phoneAreaCode, String phone, String verifyCode);

    /**
     * 验证邮箱验证码是否登录成功
     * @param email 邮箱
     *              Email
     *
     * @param verificationCode 验证码
     *                         Verification code
     */
    LoginVO loginByEmailAndVerificationCode(String email, String verificationCode);

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