package com.kantboot.business.erp.exception;

import com.kantboot.util.rest.exception.BaseException;

public class BusErpEnterpriseException {

    /**
     * 企业不存在
     * Enterprise not exist
     */
    public static final BaseException ENTERPRISE_NOT_EXIST
            = BaseException.of("enterpriseNotExist", "Enterprise not exist");

    /**
     * 非法操作
     * Illegal operation
     */
    public static final BaseException ILLEGAL_OPERATION
            = BaseException.of("illegalOperation", "Illegal operation");

}
