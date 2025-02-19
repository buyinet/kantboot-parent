package com.kantboot.business.ai.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class DialogMessageSearchDTO implements Serializable {

    /**
     * 角色
     */
    private String role;

    /**
     * 内容
     */
    private String content;

    /**
     * 会话ID
     */
    private Long dialogId;

    /**
     * 最大ID
     */
    private Long maxId;

    /**
     * 最小ID
     */
    private Long minId;

}
