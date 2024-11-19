package com.kantboot.user.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.user.account.dao.repository.UserAccountRepository;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.exception.UserAccountException;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.user.account.service.IUserAccountTokenService;
import com.kantboot.user.account.util.UserAccountSaveCheckUtil;
import com.kantboot.util.crypto.password.impl.KantbootPassword;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

    @Resource
    private UserAccountRepository repository;

    @Resource
    private KantbootPassword kantbootPassword;

    @Resource
    private UserAccountSaveCheckUtil userAccountSaveCheckUtil;

    @Resource
    private IUserAccountTokenService userAccountTokenService;

    /**
     * 密码加密
     * 必须在校验密码之后调用
     */
    @Override
    public String encryptPassword(String password) {
        // 密码加密
        return kantbootPassword.encrypt(password);
    }

    /**
     * 创建新用户账号
     */
    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        // 设置为临时账号
        // 如果临时账号为空，则设置为true
        if (userAccount.getIsTemporary() == null){
            userAccount.setIsTemporary(true);
        }
        // 如果userAccount的isAdmin为空，则设置为false
        if (userAccount.getIsAdmin() == null) {
            userAccount.setIsAdmin(false);
        }
        // 校验用户账号
        userAccountSaveCheckUtil.check(userAccount);
        // 密码加密
        userAccount.setPassword(encryptPassword(userAccount.getPassword()));
        // 保存用户账号
        UserAccount save = repository.save(userAccount);
        UserAccount result = BeanUtil.copyProperties(save, UserAccount.class);
        // 将密码置空
        result.setPassword(null);
        return result;
    }

    @Override
    public UserAccount getById(Long id) {
        UserAccount userAccount = repository.findById(id).orElse(null);
        if (userAccount == null) {
            // 用户账号不存在
            throw UserAccountException.NOT_EXIST;
        }
        return userAccount;
    }

    @Override
    public List<UserAccount> getByIds(List<Long> ids) {
        return repository.getByIds(ids);
    }

    @Override
    public Long getSelfId() {
        Long userAccountId = userAccountTokenService.getUserAccountIdBySelf();
        if (userAccountId == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        return userAccountId;
    }

    @Override
    public UserAccount getSelf() {
        Long userAccountId = userAccountTokenService.getUserAccountIdBySelf();
        UserAccount userAccount = repository.findById(userAccountId).orElse(null);
        if (userAccount == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        userAccount.setPassword(null);
        return userAccount;
    }

    @Override
    public UserAccount saveSelf(UserAccount userAccount) {
        // 获取当前用户
        Long selfId = getSelfId();
        UserAccount userAccountInDb = repository.findById(selfId).orElse(null);
        if (userAccountInDb == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        // 设置头像
        userAccountInDb.setFileIdOfAvatar(userAccount.getFileIdOfAvatar());
        // 设置昵称
        userAccountInDb.setNickname(userAccount.getNickname());
        // 保存用户账号
        UserAccount save = repository.save(userAccountInDb);
        UserAccount result = BeanUtil.copyProperties(save, UserAccount.class);
        // 将密码置空
        result.setPassword(null);
        return result;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        // 获取当前用户
        Long selfId = getSelfId();
        UserAccount userAccount = repository.findById(selfId).orElse(null);
        if (userAccount == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        // 校验新密码
        userAccountSaveCheckUtil.checkPassword(newPassword);

        // 校验密码
        if (!kantbootPassword.matches(oldPassword, userAccount.getPassword())) {
            // 密码错误
            throw UserAccountException.OLD_PASSWORD_ERROR;
        }
        // 密码加密
        userAccount.setPassword(encryptPassword(newPassword));
        // 保存用户账号
        repository.save(userAccount);
    }

    @Override
    public boolean existsByPhone(String phoneAreaCode, String phone) {
        return repository.existsByPhoneAreaCodeAndPhone(phoneAreaCode, phone);
    }

    @Override
    public UserAccount getByEmail(String email) {
        UserAccount byEmail = repository.findByEmail(email);
        if (byEmail == null) {
            // 用户账号不存在
            throw UserAccountException.NOT_EXIST;
        }
        return byEmail;
    }
}
