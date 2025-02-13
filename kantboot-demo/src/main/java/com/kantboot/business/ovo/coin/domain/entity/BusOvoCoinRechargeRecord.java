package com.kantboot.business.ovo.coin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name="bus_ovo_coin_recharge_record")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusOvoCoinRechargeRecord implements Serializable {

    /**
     * 主键
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     * Create time
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     * Modified time
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 数量
     * Number
     */
    @Column(name = "number")
    private BigDecimal number;

    /**
     * 用户账号ID
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * 币种代码
     */
    @Column(name = "currency_code")
    private String currencyCode;

    /**
     * 对应相应币种的金额
     */
    @Column(name = "amount_of_currency")
    private BigDecimal amount;

    /**
     * 选项相应的金额
     */
    @Column(name = "amount_of_option")
    private BigDecimal amountOfOption;

    /**
     * 选项ID
     */
    @Column(name = "option_id")
    private Long optionId;

    /**
     * 支付订单ID
     */
    @Column(name = "pay_order_id")
    private Long payOrderId;

    /**
     * 状态编码
     * Status code
     * 未处理：notProcessed
     * 处理中：processing
     * 处理成功：success
     * 已取消：cancelled
     */
    @Column(name = "status_code")
    private String statusCode;

}
