package com.kantboot.business.ai.method;

public abstract class BusAIChatMethod {

    /**
     * 运行状态
     */
    public abstract void run(String responseStr, String content, Boolean done);

    /**
     * 完成状态
     */
    public abstract void finish(String content);

}
