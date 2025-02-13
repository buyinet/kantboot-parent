package com.kantboot.user.balance.domain.entity;

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
 * 用户账号余额变动记录实体类
 * @author 方某方
 */
@Entity
@Getter
@Setter
@Table(name = "user_account_balance_change_record")
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@DynamicInsert
public class UserAccountBalanceChangeRecord implements Serializable {

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
     * 事由编码
     */
    @Column(name = "reason_code", length = 64)
    private String reasonCode;

    /**
     * 余额类型编码
     */
    @Column(name = "balance_code", length = 64)
    private String balanceCode;

    /**
     * 用户id
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * 数量
     */
    @Column(name = "t_number")
    private BigDecimal number;

    /**
     * 状态编码
     * 未处理：notProcessed
     * 处理中：processing
     * 已处理：processed
     * 锁: lock
     * 处理失败：failed
     */
    @Column(name = "t_status_code", length = 64)
    private String statusCode;

    /**
     * 失败原由编码
     */
    @Column(name = "fail_reason_code", length = 64)
    private String failReasonCode;

}
