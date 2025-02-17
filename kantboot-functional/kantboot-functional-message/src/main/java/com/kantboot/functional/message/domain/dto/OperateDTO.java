package com.kantboot.functional.message.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperateDTO implements Serializable {

    /**
     * 操作编码
     */
    private String operateCode;

    /**
     * 操作数据
     */
    private Object data;

}
