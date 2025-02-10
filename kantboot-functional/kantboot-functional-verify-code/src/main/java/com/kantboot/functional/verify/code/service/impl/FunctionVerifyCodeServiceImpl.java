package com.kantboot.functional.verify.code.service.impl;

import com.kantboot.functional.verify.code.domain.dto.SendVerifyCodeDTO;
import com.kantboot.functional.verify.code.domain.entity.FunctionalVerifyCode;
import com.kantboot.functional.verify.code.repository.FunctionalVerifyCodeRepository;
import com.kantboot.functional.verify.code.service.IFunctionalVerifyCodeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FunctionVerifyCodeServiceImpl implements IFunctionalVerifyCodeService {

    @Resource
    private FunctionalVerifyCodeRepository repository;

    /**
     * 生成验证码
     * @return 验证码
     */
    @Override
    public String generateCode() {
        // 生成6位数字验证码
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }

    /**
     * 发送验证码
     * @param dto 验证码发送数据传输对象
     */
    @Override
    public FunctionalVerifyCode send(SendVerifyCodeDTO dto) {
        // 生成验证码
        String code = generateCode();
        // 过期时长 默认300秒
        long expire = dto.getExpire() == null ? 300L : dto.getExpire();
        // 过期时间
        Date gmtExpire = new Date((System.currentTimeMillis() + expire * 1000)+5000);

        FunctionalVerifyCode verifyCode = new FunctionalVerifyCode();
        verifyCode.setTo(dto.getTo());
        verifyCode.setTypeCode(dto.getTypeCode());
        verifyCode.setSceneCode(dto.getSceneCode());
        verifyCode.setValue(code);
        verifyCode.setGmtExpire(gmtExpire);

        // 保存到数据库
        return repository.save(verifyCode);
    }

    /**
     * 校验
     *
     * @param dto 验证码发送数据传输对象
     * @param verifyCode 验证码
     * @return 是否验证通过
     */
    @Override
    public Boolean verify(SendVerifyCodeDTO dto, String verifyCode) {
        if (dto == null || verifyCode == null) {
            return false;
        }
        List<FunctionalVerifyCode> param = repository.findParam(dto);
        for (FunctionalVerifyCode functionalVerifyCode : param) {
            if(verifyExpire(functionalVerifyCode)){
                // 如果过期就删除
                repository.delete(functionalVerifyCode);
            }
            if (functionalVerifyCode.getValue().equals(verifyCode)) {
                // 如果验证码就删除此条数据，并返回true
                repository.delete(functionalVerifyCode);
                return true;
            }
        }
        return false;
    }

    /**
     * 验证是否过期
     */
    private Boolean verifyExpire(FunctionalVerifyCode functionalVerifyCode) {
        Date now = new Date();
        return functionalVerifyCode.getGmtExpire().after(now);
    }
}
