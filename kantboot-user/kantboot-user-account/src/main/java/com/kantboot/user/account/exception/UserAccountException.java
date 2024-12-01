package com.kantboot.user.account.exception;

import com.kantboot.util.rest.exception.BaseException;

public class UserAccountException {

    /**
     * 未登录
     */
    public static final BaseException NOT_LOGIN = BaseException.of("notLogin", "Not logged in");

    /**
     * 账号不存在
     */
    public static final BaseException NOT_EXIST = BaseException.of("notExist", "Account does not exist");

    /**
     * 用户名已存在
     */
    public static final BaseException USERNAME_EXIST = BaseException.of("usernameExist", "Username already exists");

    /**
     * 原密码错误
     */
    public static final BaseException OLD_PASSWORD_ERROR = BaseException.of("oldPasswordError", "Old password error");

    /**
     * 用户名或密码错误
     * usernameOrPasswordError
     */
    public static final BaseException USERNAME_OR_PASSWORD_ERROR = BaseException.of("usernameOrPasswordError", "Username or password error");

    /**
     * 该账号未设置密码
     * passwordNotSet
     */
    public static final BaseException PASSWORD_NOT_SET = BaseException.of("passwordNotSet", "The account has not set a password");

    /**
     * 未使用短信验证码插件
     */
    public static final BaseException SMS_PLUGIN_NOT_USED = BaseException.of("smsPluginNotUsed", "The SMS verification code plugin is not used");

    /**
     * 账号不存在
     */
    public static final BaseException ACCOUNT_NOT_EXIST = BaseException.of("accountNotExist", "Account does not exist");

    /**
     * 邮箱或密码错误
     */
    public static final BaseException EMAIL_OR_PASSWORD_ERROR = BaseException.of("emailOrPasswordError", "Email or password error");

}
