package com.kantboot.api.google.translate.exception;

import com.kantboot.util.rest.exception.BaseException;

public class ApiTranslateException {

    /**
     * 翻译文本错误
     * Translate text error
     */
    public static final BaseException TRANSLATE_TEXT_ERROR
            = BaseException.of("translateTextError", "Translate text error");

}
