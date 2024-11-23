package com.kantboot.business.erp.exception;

import com.kantboot.util.rest.exception.BaseException;

public class BusinessErpException {

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

    /**
     * 部门不存在
     * Department not exist
     */
    public static final BaseException DEPARTMENT_NOT_EXIST
            = BaseException.of("departmentNotExist", "Department not exist");

    /**
     * 未选择企业
     * No enterprise selected
     */
    public static final BaseException NO_ENTERPRISE_SELECTED
            = BaseException.of("noEnterpriseSelected", "No enterprise selected");

    /**
     * 未选择部门
     * No department selected
     */
    public static final BaseException NO_DEPARTMENT_SELECTED
            = BaseException.of("noDepartmentSelected", "No department selected");

    /**
     * 用户名已存在
     * Username exist
     */
    public static final BaseException USERNAME_EXIST
            = BaseException.of("usernameExist", "Username exist");

    /**
     * 员工不存在
     * Employee not exist
     */
    public static final BaseException EMPLOYEE_NOT_EXIST
            = BaseException.of("employeeNotExist", "Employee not exist");

    /**
     * 用户名或密码错误
     * Username or password error
     */
    public static final BaseException USERNAME_OR_PASSWORD_ERROR
            = BaseException.of("usernameOrPasswordError", "Username or password error");
}
