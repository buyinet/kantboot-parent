package com.kantboot.official.plugin.user.account;

import com.kantboot.user.account.slot.UserAccountSlot;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class KantbootOfficialPluginUserAccountConfig {

    @Bean
    public UserAccountSlot userAccountSlot(){
        return new UserAccountSlot(){

            @Override
            public void sendLoginVerifyCodeByPhone(String phoneAreaCode, String phone) {
                // TODO Auto-generated method stub
            }

            @Override
            public void sendLoginVerifyCodeByEmail(String email) {
                // TODO Auto-generated method stub
            }

            @Override
            public Boolean matchLoginVerifyCodeByPhone(String phoneAreaCode, String phone, String verifyCode) {
                return super.matchLoginVerifyCodeByPhone(phoneAreaCode, phone, verifyCode);
            }

            @Override
            public Boolean matchLoginVerifyCodeByEmail(String email, String verifyCode) {
                return super.matchLoginVerifyCodeByEmail(email, verifyCode);
            }

        };
    }

}
