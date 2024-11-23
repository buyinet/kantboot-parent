package com.kantboot.business.erp.domain.entity;

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
 * 员工证书 的实体类
 * Entity class about employee certificate
 */
@Table(name="bus_erp_emp_certificate")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpEmpCertificate  implements Serializable {

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
     * 员工ID
     * Employee ID
     */
    @Column(name = "emp_id")
    private Long empId;

    /**
     * 证书ID
     * Certificate ID
     */
    @Column(name = "certificate_id")
    private Long certificateId;

    /**
     * 有效期截至时间
     * Expiration date
     */
    @Column(name = "gmt_expire")
    private Date gmtExpire;

    /**
     * 证书
     * Certificate
     */
    @OneToOne
    @JoinColumn(name = "certificate_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BusErpCertificate certificate;

}
