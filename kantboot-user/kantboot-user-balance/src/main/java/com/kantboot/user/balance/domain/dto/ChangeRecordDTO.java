package com.kantboot.user.balance.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class ChangeRecordDTO {

    /**
     * 事由编码
     */
    private String reasonCode;

    /**
     * 余额类型编码
     */
    private String balanceCode;

    /**
     * 用户账号id
     */
    private Long userAccountId;

    /**
     * 数量
     */
    private BigDecimal number;

}
