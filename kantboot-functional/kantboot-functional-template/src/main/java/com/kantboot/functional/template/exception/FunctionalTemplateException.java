package com.kantboot.functional.template.exception;

import com.kantboot.util.rest.exception.BaseException;

/**
 * 用来处理FunctionalTemplate模块的异常
 * Exception handler for FunctionalTemplate module
 */
public class FunctionalTemplateException {

    /**
     * 模板分组不存在
     * Template group not exist
     */
    public static final BaseException TEMPLATE_GROUP_NOT_EXIST
            = BaseException.of("templateGroupNotExist","Template group not exist");

    /**
     * 模板分组编码已存在
     * Template group code exist
     */
    public static final BaseException TEMPLATE_GROUP_CODE_EXIST
            = BaseException.of("templateGroupCodeExist", "Template group code exist");

    /**
     * 模板不存在
     * Template not exist
     */
    public static final BaseException TEMPLATE_NOT_EXIST
            = BaseException.of("templateNotExist", "Template not exist");

    /**
     * 模板编码已存在
     * Template code exist
     */
    public static final BaseException TEMPLATE_CODE_EXIST
            = BaseException.of("templateCodeExist", "Template code exist");

}
