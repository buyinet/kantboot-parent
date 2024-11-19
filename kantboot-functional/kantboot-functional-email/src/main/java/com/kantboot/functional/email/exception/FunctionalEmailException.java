package com.kantboot.functional.email.exception;


import com.kantboot.util.rest.exception.BaseException;

/**
 * 邮件功能的异常
 */
public class FunctionalEmailException {

    /**
     * 邮箱服务器错误
     */
    public static BaseException EMAIL_SERVER_ERROR = new BaseException("emailServerError", "Email server error");

}
