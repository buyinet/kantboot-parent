package com.kantboot.functional.pay.order.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 币种为当时币种
 */
@Data
public class RefundDTO implements Serializable {

    /**
     * 支付单号
     */
    private Long payOrderId;

    /**
     * 退款原因编码
     */
    private String refundReasonCode;

    /**
     * 退款原因描述
     */
    private String refundReasonDescription;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 是否全额退款
     */
    private Boolean isAllRefund;

    /**
     * 退款是是否扣除手续费
     */
    private Boolean isSubtractFeeWhenRefund;

    /**
     * 实际退款金额
     */
    private BigDecimal actualRefundAmount;

    /**
     * 退款时的附加信息
     */
    private String refundAdditionalInfo;

}
