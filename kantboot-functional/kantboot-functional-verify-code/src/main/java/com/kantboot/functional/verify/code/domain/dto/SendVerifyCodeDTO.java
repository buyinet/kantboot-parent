package com.kantboot.functional.verify.code.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 发送验证码的数据传输对象
 */
@Data
@Accessors(chain = true)
public class SendVerifyCodeDTO implements Serializable {

    /**
     * 接收者
     */
    private String to;

    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 场景编码
     */
    private String sceneCode;

    /**
     * 过期时长
     * 单位：秒
     */
    private Long expire;

}
