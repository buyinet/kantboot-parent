package com.kantboot.official.plugin.user.account;

import com.kantboot.functional.email.dto.EmailMessageDTO;
import com.kantboot.functional.email.service.IFunctionalEmailService;
import com.kantboot.functional.sms.domain.dto.SmsMessageDTO;
import com.kantboot.functional.sms.service.IFunctionalSmsService;
import com.kantboot.functional.template.domain.vo.FunctionalTemplateGenerateVO;
import com.kantboot.functional.template.service.IFunctionalTemplateService;
import com.kantboot.functional.verify.code.domain.dto.SendVerificationCodeDTO;
import com.kantboot.functional.verify.code.domain.entity.FunctionalVerificationCode;
import com.kantboot.functional.verify.code.service.IFunctionalVerificationCodeService;
import com.kantboot.user.account.slot.UserAccountSlot;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 插件配置
 * @author 方某方
 */
@Configuration
public class KantbootOfficialPluginUserAccountConfig {

    @Resource
    private IFunctionalEmailService functionalEmailService;

    @Resource
    private IFunctionalSmsService functionalSmsService;

    @Resource
    private IFunctionalTemplateService functionalTemplateService;

    @Resource
    private IFunctionalVerificationCodeService functionalVerifyCodeService;

    @Bean
    public UserAccountSlot userAccountSlot(){
        return new UserAccountSlot(){

            @Override
            public void sendLoginVerifyCodeByPhone(String phoneAreaCode, String phone) {
                SendVerificationCodeDTO dto = new SendVerificationCodeDTO()
                        .setTo(phoneAreaCode + phone)
                        .setTypeCode("sms")
                        .setSceneCode("login")
                        .setExpire(300000L);

                FunctionalVerificationCode send = functionalVerifyCodeService.send(dto);

                FunctionalTemplateGenerateVO template =
                        functionalTemplateService.getTemplate("smsLoginVerificationCode",
                                Map.of("value", send.getValue()));

                functionalSmsService.send(new SmsMessageDTO()
                        .setPhoneAreaCode(phoneAreaCode)
                        .setPhone(phone)
                        .setMessageTypeCode("login")
                        .setContent(template.getContent())
                );

            }

            @Override
            public void sendLoginVerifyCodeByEmail(String email) {
                SendVerificationCodeDTO dto = new SendVerificationCodeDTO()
                        .setTo(email)
                        .setTypeCode("email")
                        .setSceneCode("login")
                        .setExpire(300000L);

                FunctionalVerificationCode send = functionalVerifyCodeService.send(dto);

                FunctionalTemplateGenerateVO template =
                        functionalTemplateService.getTemplate("emailLoginVerificationCode",
                                Map.of("value", send.getValue()));

                functionalEmailService.send(new EmailMessageDTO()
                        .setEmail(email)
                        .setSubject(template.getTitle())
                        .setContent(template.getContent())
                        .setIsHtml(true)
                );
            }

            @Override
            public Boolean matchLoginVerifyCodeByPhone(String phoneAreaCode, String phone, String verifyCode) {
                return functionalVerifyCodeService.verify(new SendVerificationCodeDTO()
                        .setTo(phoneAreaCode + phone)
                        .setTypeCode("sms")
                        .setSceneCode("login"), verifyCode);
            }

            @Override
            public Boolean matchLoginVerifyCodeByEmail(String email, String verifyCode) {
                return functionalVerifyCodeService.verify(new SendVerificationCodeDTO()
                        .setTo(email)
                        .setTypeCode("email")
                        .setSceneCode("login"), verifyCode);
            }

        };
    }




}
