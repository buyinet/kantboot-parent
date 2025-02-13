package com.kantboot.functional.pay.order.domian.entity;

import com.kantboot.util.jpa.type.KantbootGenerationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体
 * @author 方某方
 */
@Entity
@Getter
@Setter
@Table(name = "functional_pay_order")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class FunctionalPayOrder implements Serializable {

    /**
     * 主键
     */
    @Id
    @GenericGenerator(name = "snowflakeId",strategy = KantbootGenerationType.SNOWFLAKE)
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后一次修改时间
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 发起人的用户id
     */
    @Column(name="user_account_id")
    private Long userAccountId;

    /**
     * 订单金额
     */
    @Column(name="amount")
    private BigDecimal amount;

    /**
     * 订单状态，驼峰式
     * 未支付 unpaid
     * 已支付 paid
     * 已退款 refunded
     * 已取消 canceled
     * 异常 error
     */
    @Column(name="status_code")
    private String statusCode;

    /**
     * 校验失败原因编码
     * 出于国际化与查找原因的考虑，使用编码
     */
    @Column(name="paid_check_fail_reason_code")
    private String paidAfterCheckFailReasonCode;

    /**
     * 校验失败原因描述
     * 如果非国际化项目，可以直接使用文字
     */
    @Column(name="paid_check_fail_reason_description")
    private String paidAfterCheckFailReasonDescription;

    /**
     * 业务编码
     * 用于区分不同的业务类型
     * 例如：ovoClion
     */
    @Column(name="business_code")
    private String businessCode;

    /**
     * 描述
     */
    @Column(name="description")
    private String description;

    /**
     * 货币
     * 例如：CNY
     * 例如：USD
     */
    @Column(name="currency")
    private String currency;

    /**
     * 支付方式编码
     * 例如：wechatPay
     * 例如：alipay
     */
    @Column(name="pay_method_code")
    private String payMethodCode;

    /**
     * 支付方式的附加信息
     */
    @Column(name="pay_method_additional_info",length = 30000)
    private String payMethodAdditionalInfo;

    /**
     * 手续费
     */
    @Column(name="fee")
    private BigDecimal fee;

    /**
     * 实付金额
     */
    @Column(name="paid_amount")
    private BigDecimal paidAmount;

}
