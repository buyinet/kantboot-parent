package com.kantboot.business.ai.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DialogSearchDTO implements Serializable {

    /**
     * 用户账号ID
     */
    private Long userAccountId;

    /**
     * 最大ID
     */
    private Long maxId;

    /**
     * 最小ID
     */
    private Long minId;

    /**
     * 会话ID
     */
    private Long dialogId;

    /**
     * modelId
     */
    private Long modelId;


}
