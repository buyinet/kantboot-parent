package com.kantboot.user.account.service;

import com.kantboot.user.account.domain.entity.UserAccount;

import java.util.List;

/**
 * 用户账户服务
 */
public interface IUserAccountService {

    /**
     * 加密密码
     */
    String encryptPassword(String password);

    /**
     * 创建新用户账号
     */
    UserAccount createUserAccount(UserAccount userAccount);

    /**
     * 获取用户账号ID
     */
    UserAccount getById(Long id);

    /**
     * 根据用户账号IDS
     */
    List<UserAccount> getByIds(List<Long> ids);

    /**
     * 获取自身信息的ID
     */
    Long getSelfId();

    /**
     * 获取自身信息
     */
    UserAccount getSelf();

    /**
     * 保存自身信息
     */
    UserAccount saveSelf(UserAccount userAccount);

    /**
     * 修改密码
     */
    void changePassword(String oldPassword, String newPassword);

    /**
     * 判断是否存在手机号
     */
    boolean existsByPhone(String phoneAreaCode, String phone);

    /**
     * 根据邮箱获取用户
     */
    UserAccount getByEmail(String email);

}
