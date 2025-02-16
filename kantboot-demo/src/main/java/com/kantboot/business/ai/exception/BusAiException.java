package com.kantboot.business.ai.exception;

import com.kantboot.util.rest.exception.BaseException;

public class BusAiException {

    /**
     * 该聊天不存在
     * The chat record does not exist
     */
    public static final BaseException CHAT_NOT_EXIST
            = BaseException.of("chatRecordNotExist","The chat record does not exist");

    /**
     * 该模型未创建完成
     * The model is not complete
     */
    public static final BaseException MODEL_NOT_COMPLETE
            = BaseException.of("modelNotComplete", "The model is not complete");

    /**
     * 该模型不存在
     * The model does not exist
     */
    public static final BaseException MODEL_NOT_EXIST
            = BaseException.of("modelNotExist", "The model does not exist");


}
