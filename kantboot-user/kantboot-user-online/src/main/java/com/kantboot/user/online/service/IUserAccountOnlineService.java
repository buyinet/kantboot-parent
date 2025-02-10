package com.kantboot.user.online.service;

public interface IUserAccountOnlineService {

    /**
     * 隐身
     */
    void invisible(Long userAccountId, Boolean isInvisible);

    /**
     * 对自己隐身
     */
    void invisibleSelf(Boolean isInvisible);

    /**
     * 心跳
     */
    void heartbeat(Long userAccountId);

    /**
     * 对自己心跳
     */
    void heartbeatSelf();

    /**
     * 上线
     */
    void online(Long userAccountId);

    /**
     * 自己上线
     */
    void onlineSelf();

    /**
     * 离线
     */
    void offline(Long userAccountId);

    /**
     * 自己离线
     */
    void offlineSelf();

}
