package com.kantboot.user.account.slot;

import com.kantboot.user.account.exception.UserAccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户账户模块的插槽
 * 用于提供用户账户模块的扩展点
 * User Account Slot
 * Use for provide extension point for user account module
 */
@Slf4j
@Component
public class UserAccountSlot {

    /**
     * 发送手机登录验证码的插槽
     * Send login verification code by phone slot
     */
    public void sendLoginVerificationCodeByPhone(String phoneAreaCode, String phone) {
        log.info("sendLoginVerificationCodeByPhone phoneAreaCode: {}, phone: {}", phoneAreaCode, phone);
        // 提示用户账户模块未使用短信插件
        throw UserAccountException.SMS_PLUGIN_NOT_USED;
    }

}
