package com.kantboot.business.erp.domain.entity;

import com.kantboot.user.account.domain.entity.UserAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

/**
 * 关于 员工补卡申请 的实体类
 * Entity class about employee reissue clock in
 */
@Table(name="bus_erp_emp_reissue_clock_in_apply")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpEmpReissueClockInApply implements Serializable {

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
     * Modify time
     */
    @LastModifiedDate
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 补卡人的用户账号id
     * User account id of reissue
     */
    @Column(name = "user_account_id_of_reissue")
    private Long userAccountIdOfReissue;

    /**
     * 审核人的用户账号id
     * User account id of audit
     */
    @Column(name = "user_account_id_of_audit")
    private Long userAccountIdOfAudit;

    @OneToOne
    @JoinColumn(name = "user_account_id_of_reissue", referencedColumnName = "id", insertable = false, updatable = false)
    private UserAccount userAccountOfReissue;

    @OneToOne
    @JoinColumn(name = "user_account_id_of_audit", referencedColumnName = "id", insertable = false, updatable = false)
    private UserAccount userAccountOfAudit;

}
