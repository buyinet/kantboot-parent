package com.kantboot.api.qinucloud.domain.dto;

import lombok.Data;

/**
 * 七牛云请求参数
 * Qiniu cloud request parameters
 *
 * @author 方某方
 */
@Data
public class ApiQiniuRequestDTO {

    /**
     * 请求方法
     * Request method
     */
    String method;

    /**
     * host
     */
    String host;

    /**
     * 请求路径
     * Request path
     */
    String path;

    /**
     * content-type
     */
    String contentType;


    /**
     * 请求体
     * Request body
     */
    String body;

}
