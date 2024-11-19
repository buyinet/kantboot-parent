package com.kantboot.business.erp.domain.entity;

import com.kantboot.user.account.domain.entity.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 关于“员工打卡”的实体类
 * Entity class about employee clock
 */
@Table(name="bus_erp_emp_clock")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpEmpClock implements Serializable {

    /**
     * 主键
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 员工的用户账号ID，用来关联员工的用户账号
     * User account ID of employee, used to associate employee's user account
     */
    @Column(name = "t_user_account_id")
    private Long userAccountId;

    /**
     * 打卡时间
     * Clock in time
     */
    @Column(name = "gmt_clock")
    private Date gmtClock;

    /**
     * 打卡时的经度
     * Longitude of clock
     */
    @Column(name = "longitude")
    private BigDecimal longitude;

    /**
     * 打卡时的纬度
     * Latitude of clock
     */
    @Column(name = "latitude")
    private BigDecimal latitude;

    /**
     * 打卡时的IP
     * IP of clock
     */
    @Column(name = "ip")
    private String ip;

    /**
     * 打卡类型
     * 1: 上班打卡
     * 2: 下班打卡
     *
     * Clock in type
     * 1: Clock in
     * 2: Clock out
     */
    @Column(name = "t_type")
    private Integer type;

    /**
     * 是否为补卡
     * Whether it is a reissue
     */
    @Column(name = "is_reissue")
    private Boolean isReissue;

    /**
     * 补卡原因
     * Reissue reason
     */
    @Column(name = "reissue_reason", length = 3000)
    private String reissueReason;

    /**
     * 审核补卡的用户账号ID
     * User account ID of audit reissue
     */
    @Column(name = "user_account_id_of_audit_reissue")
    private Long userAccountIdOfAuditReissue;

    /**
     * 补卡时间
     * Reissue time
     */
    @Column(name = "gmt_reissue")
    private Date gmtReissue;

    @OneToOne
    @JoinColumn(name = "user_account_id_of_audit_reissue", referencedColumnName = "id", insertable = false, updatable = false)
    private UserAccount userAccountOfAuditReissue;


}
