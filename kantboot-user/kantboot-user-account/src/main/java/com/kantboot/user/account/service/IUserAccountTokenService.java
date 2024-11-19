package com.kantboot.user.account.service;

/**
 * 用户账户令牌服务
 */
public interface IUserAccountTokenService {

    /**
     * 生成令牌
     */
    String generateToken(Long userAccountId);

    /**
     * 获取用户账户ID
     */
    Long getUserAccountIdBySelf();

    /**
     * token切换用户
     */
    void switchUser(Long userAccountId);

    /**
     * 删除自身的token
     */
    void removeTokenBySelf();


}
