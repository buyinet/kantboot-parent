package com.kantboot.api.qinucloud.service;

/**
 * 七牛云短信服务
 * Qiniu cloud SMS service
 */
public interface IApiQiniuSmsService {

    /**
     * 发送一条短信
     * Send a message
     *
     * @param phone 手机号
     *              phone number
     *
     * @param content 信息
     *                information
     */
    void sendFulltextMessageOne(String phone, String content);

}
