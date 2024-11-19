package com.kantboot.api.qinucloud.exception;

import com.kantboot.util.rest.exception.BaseException;

/**
 * 七牛云的异常
 * Qiniu cloud exception
 */
public class ApiQiniuCloudException {

    /**
     * 七牛云模板不存在
     */
    public static final BaseException QINIU_CLOUD_ERR_CODE_OF_INVALID_ARGUMENT =
            BaseException.of("qiniuCloudErrCodeOfInvalidArgument","The Qiniu cloud template does not exist");


}
