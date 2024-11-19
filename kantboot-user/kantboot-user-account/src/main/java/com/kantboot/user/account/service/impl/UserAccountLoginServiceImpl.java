package com.kantboot.user.account.service.impl;


import com.kantboot.user.account.dao.repository.UserAccountRepository;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.domain.vo.LoginVO;
import com.kantboot.user.account.exception.UserAccountException;
import com.kantboot.user.account.service.IUserAccountLoginService;
import com.kantboot.user.account.service.IUserAccountTokenService;
import com.kantboot.util.crypto.password.impl.KantbootPassword;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAccountLoginServiceImpl implements IUserAccountLoginService {

    @Resource
    private UserAccountRepository repository;

    @Resource
    private IUserAccountTokenService userAccountTokenService;

    @Resource
    private KantbootPassword kantbootPassword;

    @Override
    public LoginVO loginByUsernameAndPassword(String username, String password) {
        UserAccount byUsername = repository.findByUsername(username);
        if (byUsername == null) {
            // 账号不存在，但是提示账号或密码错误
            throw UserAccountException.USERNAME_OR_PASSWORD_ERROR;
        }
        // 如果密码为空
        if (byUsername.getPassword() == null) {
            // 该账号未设置密码
            throw UserAccountException.PASSWORD_NOT_SET;
        }
        // 密码校验
        if (!kantbootPassword.matches(password, byUsername.getPassword())) {
            // 账号或密码错误
            throw UserAccountException.USERNAME_OR_PASSWORD_ERROR;
        }

        // 生成令牌
        String token = userAccountTokenService.generateToken(byUsername.getId());
        return new LoginVO()
                .setToken(token)
                .setUserAccount(byUsername);
    }

    @Override
    public void sendPhoneVerificationCode(String phoneAreaCode, String phone) {

    }

    @Override
    public void logout() {
        // 退出登录
        userAccountTokenService.removeTokenBySelf();
    }
}
