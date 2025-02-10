package com.kantboot.functional.sms.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SmsMessageDTO implements Serializable {

    /**
     * 内容
     */
    private String content;

    /**
     * 接受者
     */
    private String to;

    /**
     * 手机区号
     */
    private String phoneAreaCode;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 消息类型编码
     */
    private String messageTypeCode;


}
