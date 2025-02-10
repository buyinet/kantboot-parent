package com.kantboot.functional.sms.service;

import com.kantboot.functional.sms.domain.dto.SmsMessageDTO;

public interface IFunctionalSmsService {

    /**
     * 发送验证码
     */
    void send(SmsMessageDTO messageDTO);

}
