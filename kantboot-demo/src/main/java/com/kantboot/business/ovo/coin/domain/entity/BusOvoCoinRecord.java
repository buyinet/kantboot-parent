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

/**
 * O币账户记录
 * Account record
 */
@Table(name="bus_ovo_coin_record")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusOvoCoinRecord implements Serializable {

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
     * 类型编码
     * Type code
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 数量
     * Number
     */
    @Column(name = "number")
    private BigDecimal number;

    /**
     * 用户账号ID
     * User account ID
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * 充值的货币类型编码
     * Currency
     */
    @Column(name = "currency_code")
    private String currencyCode;

    /**
     * 金额
     */
    @Column(name = "amount")
    private BigDecimal amount;


}
