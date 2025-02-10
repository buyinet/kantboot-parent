package com.kantboot.user.balance.domain.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangeRecordDTO {

    /**
     * 事由编码
     */
    @Column(name = "reason_code", length = 64)
    private String reasonCode;

    /**
     * 余额类型编码
     */
    private String balanceTypeCode;

    /**
     * 用户账号id
     */
    private Long userAccountId;

    /**
     * 数量
     */
    private BigDecimal number;

}
