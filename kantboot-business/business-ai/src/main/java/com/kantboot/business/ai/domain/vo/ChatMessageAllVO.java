package com.kantboot.business.ai.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ChatMessageAllVO implements Serializable {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

}
