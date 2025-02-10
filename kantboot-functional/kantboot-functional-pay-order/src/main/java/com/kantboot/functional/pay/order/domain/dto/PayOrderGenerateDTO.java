package com.kantboot.functional.pay.order.domain.dto;


import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 生成支付订单的DTO
 */
@Data
@Accessors(chain = true)
public class PayOrderGenerateDTO {

    /**
     * 用户id
     */
    private Long userAccountId;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 产品编码
     * 用于区分不同的产品
     * 例如：oMoney
     * 例如：oVip
     */
    private String productCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 货币
     * 例如：CNY
     * 例如：USD
     */
    private String currency;

    /**
     * 支付方式编码
     * 例如：wechatPay
     * 例如：aliPay
     */
    private String payMethodCode;


}