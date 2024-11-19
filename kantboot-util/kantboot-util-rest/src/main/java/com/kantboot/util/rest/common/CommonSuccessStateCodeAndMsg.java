package com.kantboot.util.rest.common;

import com.kantboot.util.rest.result.entity.StateCodeAndMsgEntity;

/**
 * 通用成功状态码和消息
 * Common success status code and message
 */
public class CommonSuccessStateCodeAndMsg {

    /**
     * 获取成功
     * Get success
     */
    public static final StateCodeAndMsgEntity GET_SUCCESS = StateCodeAndMsgEntity.of("getSuccess", "Get Success");

    /**
     * 设置成功
     * Set success
     */
    public static final StateCodeAndMsgEntity SET_SUCCESS = StateCodeAndMsgEntity.of("setSuccess", "Set Success");

    /**
     * 删除成功
     * Remove success
     */
    public static final StateCodeAndMsgEntity REMOVE_SUCCESS = StateCodeAndMsgEntity.of("removeSuccess", "Remove Success");

    /**
     * 修改成功
     * Change success
     */
    public static final StateCodeAndMsgEntity CHANGE_SUCCESS = StateCodeAndMsgEntity.of("changeSuccess", "Change Success");

    /**
     * 退出登录成功
     * Logout success
     */
    public static final StateCodeAndMsgEntity LOGOUT_SUCCESS = StateCodeAndMsgEntity.of("logoutSuccess", "Logout Success");

    /**
     * 操作成功
     * Operation success
     */
    public static final StateCodeAndMsgEntity OPERATION_SUCCESS = StateCodeAndMsgEntity.of("operationSuccess", "Operation Success");

    /**
     * 保存成功
     * Save success
     */
    public static final StateCodeAndMsgEntity SAVE_SUCCESS = StateCodeAndMsgEntity.of("saveSuccess", "Save Success");

    /**
     * 上传成功
     * Upload success
     */
    public static final StateCodeAndMsgEntity UPLOAD_SUCCESS = StateCodeAndMsgEntity.of("uploadSuccess", "Upload Success");

    /**
     * 发送成功
     * Send success
     */
    public static final StateCodeAndMsgEntity SEND_SUCCESS = StateCodeAndMsgEntity.of("sendSuccess", "Send Success");

    /**
     * 登录成功
     * Login success
     */
    public static final StateCodeAndMsgEntity LOGIN_SUCCESS = StateCodeAndMsgEntity.of("loginSuccess", "Login Success");

    /**
     * 注册成功
     * Register success
     */
    public static final StateCodeAndMsgEntity REGISTER_SUCCESS = StateCodeAndMsgEntity.of("registerSuccess", "Register Success");

    /**
     * 绑定成功
     * Bind success
     */
    public static final StateCodeAndMsgEntity BIND_SUCCESS = StateCodeAndMsgEntity.of("bindSuccess", "Bind Success");

    /**
     * 跳过绑定成功
     * Skip bind success
     */
    public static final StateCodeAndMsgEntity SKIP_BIND_SUCCESS = StateCodeAndMsgEntity.of("skipBindSuccess", "Skip Bind Success");

    /**
     * 发送验证码成功
     * Send verification code success
     */
    public static final StateCodeAndMsgEntity SEND_VERIFICATION_CODE_SUCCESS = StateCodeAndMsgEntity.of("sendVerificationCodeSuccess", "Send Verification Code Success");

    /**
     * 发布成功
     * Publish success
     */
    public static final StateCodeAndMsgEntity PUSH_SUCCESS = StateCodeAndMsgEntity.of("pushSuccess", "Push Success");


}
