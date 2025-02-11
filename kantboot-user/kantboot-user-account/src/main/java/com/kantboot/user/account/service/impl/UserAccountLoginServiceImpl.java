package com.kantboot.user.account.service.impl;


import com.kantboot.user.account.dao.repository.UserAccountRepository;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.domain.vo.LoginVO;
import com.kantboot.user.account.exception.UserAccountException;
import com.kantboot.user.account.service.IUserAccountLoginService;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.user.account.service.IUserAccountTokenService;
import com.kantboot.user.account.slot.UserAccountSlot;
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

    @Resource
    private UserAccountSlot userAccountSlot;

    @Resource
    private IUserAccountService userAccountService;

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
    public LoginVO loginByEmailAndPassword(String email, String password) {
        UserAccount byEmail = repository.findByEmail(email);
        if (byEmail == null) {
            // 账号不存在，但是提示账号或密码错误
            throw UserAccountException.EMAIL_OR_PASSWORD_ERROR;
        }
        // 如果密码为空
        if (byEmail.getPassword() == null) {
            // 该账号未设置密码
            throw UserAccountException.PASSWORD_NOT_SET;
        }
        // 密码校验
        Boolean matches = kantbootPassword.matches(password, byEmail.getPassword());
        if (!matches) {
            // 账号或密码错误
            throw UserAccountException.EMAIL_OR_PASSWORD_ERROR;
        }

        // 生成令牌
        String token = userAccountTokenService.generateToken(byEmail.getId());
        return new LoginVO()
                .setToken(token)
                .setUserAccount(byEmail);
    }

    public void sendLoginVerificationCodeByPhone(String phoneAreaCode, String phone) {
        userAccountSlot.sendLoginVerifyCodeByPhone(phoneAreaCode, phone);
    }

    @Override
    public void sendLoginVerificationCodeByEmail(String email) {
        userAccountSlot.sendLoginVerifyCodeByEmail(email);
    }

    @Override
    public LoginVO loginByPhoneVerificationCode(String phoneAreaCode, String phone, String verifyCode) {
        if (!userAccountSlot.matchLoginVerifyCodeByPhone(phoneAreaCode, phone, verifyCode)) {
            // 验证码错误
            throw UserAccountException.VERIFY_CODE_ERROR;
        }
        if(userAccountService.existsByPhone(phoneAreaCode, phone)){
            UserAccount byPhone = userAccountService.getByPhone(phoneAreaCode, phone);
            // 生成令牌
            String token = userAccountTokenService.generateToken(byPhone.getId());
            return new LoginVO()
                    .setToken(token)
                    .setUserAccount(byPhone);
        }
        UserAccount userAccount = userAccountService.createUserAccount(new UserAccount()
                .setPhoneAreaCode(phoneAreaCode)
                .setPhone(phone));
        return new LoginVO()
                .setToken(userAccountTokenService.generateToken(userAccount.getId()))
                .setUserAccount(userAccount);
    }


    @Override
    public LoginVO loginByEmailAndVerificationCode(String email, String verificationCode) {
       if (!userAccountSlot.matchLoginVerifyCodeByEmail(email, verificationCode)) {
           // 验证码错误
           throw UserAccountException.VERIFY_CODE_ERROR;
       }
       if(userAccountService.existsByEmail(email)){
           UserAccount byEmail = userAccountService.getByEmail(email);
           // 生成令牌
           String token = userAccountTokenService.generateToken(byEmail.getId());
           return new LoginVO()
                   .setToken(token)
                   .setUserAccount(byEmail);
       }
       UserAccount userAccount = userAccountService.createUserAccount(new UserAccount()
               .setEmail(email));
       return new LoginVO()
               .setToken(userAccountTokenService.generateToken(userAccount.getId()))
               .setUserAccount(userAccount);
    }

    @Override
    public void logout() {
        // 退出登录
        userAccountTokenService.removeTokenBySelf();
    }

    @Override
    public LoginVO loginByUserAccountId(Long userAccountId) {
        UserAccount byId = repository.findById(userAccountId).orElse(null);
        if (byId == null) {
            // 账号不存在
            throw UserAccountException.ACCOUNT_NOT_EXIST;
        }
        // 生成令牌
        String token = userAccountTokenService.generateToken(byId.getId());
        return new LoginVO()
                .setToken(token)
                .setUserAccount(byId);
    }

}
