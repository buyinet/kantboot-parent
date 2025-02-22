package com.kantboot.api.segmind.ai.chat.method;

public abstract class ApiSegmindMethod {

    /**
     * 运行
     *
     * @param responseStr 响应的字符串
     * @param content     内容
     * @param done        是否完成
     */
    public abstract void run(String responseStr, String content, Boolean done);

    /**
     * 完成
     *
     * @param content 内容
     */
    public abstract void finish(String content);

}
