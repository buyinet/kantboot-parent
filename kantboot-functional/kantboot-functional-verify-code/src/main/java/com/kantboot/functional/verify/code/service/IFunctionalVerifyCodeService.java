package com.kantboot.functional.verify.code.service;


import com.kantboot.functional.verify.code.domain.dto.SendVerifyCodeDTO;
import com.kantboot.functional.verify.code.domain.entity.FunctionalVerifyCode;

/**
 * 验证码服务
 */
public interface IFunctionalVerifyCodeService {

    /**
     * 生成验证码
     * @return 验证码
     */
    String generateCode();

    /**
     * 发送验证码
     * @param dto 验证码发送数据传输对象
     */
    FunctionalVerifyCode send(SendVerifyCodeDTO dto);

    /**
     * 校验
     * @param dto 验证码发送数据传输对象
     * @param verifyCode 验证码
     * @return 是否验证通过
     */
    Boolean verify(SendVerifyCodeDTO dto,String verifyCode);

}
