package com.kantboot.functional.message.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageDTO implements Serializable {

    /**
     * 事件类型
     * 是WebSocket处理、还是个推、还是UDP处理
     */
    private String toolCode;

    /**
     * 地址
     */
    private String address;

    /**
     * 用户账户ID
     */
    private Long userAccountId;

    /**
     * 事件名称
     */
    private String emit;

    /**
     * 数据
     */
    private Object data;

}
