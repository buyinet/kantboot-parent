package com.kantboot.functional.verify.code.repository;

import com.kantboot.functional.verify.code.domain.dto.SendVerifyCodeDTO;
import com.kantboot.functional.verify.code.domain.entity.FunctionalVerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 验证码的持久化操作
 * @author 方某方
 */
public interface FunctionalVerifyCodeRepository extends JpaRepository<FunctionalVerifyCode, Long> {

    /**
     * 根据手机号和类型编码和场景编码查询验证码
     * @param param 查询参数
     * @return 验证码
     */
    @Query("""
            FROM FunctionalVerifyCode fvc
            WHERE fvc.to = :#{#param.to}
            AND fvc.typeCode = :#{#param.typeCode}
            AND fvc.sceneCode = :#{#param.sceneCode}
            """
    )
    List<FunctionalVerifyCode> findParam(@Param("param") SendVerifyCodeDTO param);

}
