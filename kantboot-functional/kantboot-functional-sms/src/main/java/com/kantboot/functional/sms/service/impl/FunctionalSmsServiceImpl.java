package com.kantboot.functional.sms.service.impl;

import com.kantboot.functional.sms.constants.MessageStatusCodeConstants;
import com.kantboot.functional.sms.domain.dto.SmsMessageDTO;
import com.kantboot.functional.sms.domain.entity.FunctionalSms;
import com.kantboot.functional.sms.repository.FunctionalSmsRepository;
import com.kantboot.functional.sms.service.IFunctionalSmsService;
import com.kantboot.functional.sms.slot.FunctionalSmsSlot;
import com.kantboot.util.event.EventEmit;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FunctionalSmsServiceImpl implements IFunctionalSmsService {

    @Resource
    private FunctionalSmsRepository repository;

    @Resource
    private FunctionalSmsSlot slot;

    @Resource
    private EventEmit eventEmit;

    @Override
    public void send(SmsMessageDTO messageDTO) {
        FunctionalSms functionalSms = new FunctionalSms();
        functionalSms.setPhoneAreaCode(messageDTO.getPhoneAreaCode());
        functionalSms.setPhone(messageDTO.getPhone());
        functionalSms.setContent(messageDTO.getContent());
        functionalSms.setMessageTypeCode(messageDTO.getMessageTypeCode());
        // 短信发送中
        functionalSms.setMessageStatusCode(MessageStatusCodeConstants.STATUS_CODE_SENDING);
        FunctionalSms save = repository.save(functionalSms);
        sendEnd(save, messageDTO);
    }

    public void sendEnd(FunctionalSms save, SmsMessageDTO messageDTO) {
        try {
            // 发送短信
            slot.send(messageDTO);
        } catch (BaseException e) {
            log.error("发送短信失败，失败原因：{}", e.getMessage());
            // 发送失败，更新消息状态
            save.setMessageStatusCode(MessageStatusCodeConstants.STATUS_CODE_FAIL);
            // 设置失败原因编码
            save.setMessageStatusFailReasonCode(e.getStateCode());
            // 设置失败原因
            save.setMessageStatusFailReason(e.getMessage());
            // 保存失败消息
            repository.save(save);
        }
        // 发送成功，更新消息状态
        save.setMessageStatusCode(MessageStatusCodeConstants.STATUS_CODE_SUCCESS);
        repository.save(save);
    }


}