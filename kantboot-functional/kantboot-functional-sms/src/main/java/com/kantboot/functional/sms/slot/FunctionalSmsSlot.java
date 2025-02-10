package com.kantboot.functional.sms.slot;

import com.kantboot.api.qinucloud.service.IApiQiniuSmsService;
import com.kantboot.functional.sms.domain.dto.SmsMessageDTO;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 短信插槽
 * 默认插槽，使用七牛云短信
 */
@Component
public class FunctionalSmsSlot {

    @Resource
    private IApiQiniuSmsService apiQiniuSmsService;

    /**
     * 发送短信
     * 默认插槽，默认为七牛云，所以只能发送中国大陆的短信（+86）
     */
    public void send(SmsMessageDTO messageDTO) {
        String phoneAreaCode = messageDTO.getPhoneAreaCode();
        if(phoneAreaCode == null||phoneAreaCode.isEmpty()){
            phoneAreaCode = "86";
        }
        if(!phoneAreaCode.equals("86")){
            throw BaseException.of("phoneAreaCodeErrorDefault", "暂不支持该地区短信发送");
        }

        apiQiniuSmsService.sendFulltextMessageOne(messageDTO.getPhone(), messageDTO.getContent());
    }

}
