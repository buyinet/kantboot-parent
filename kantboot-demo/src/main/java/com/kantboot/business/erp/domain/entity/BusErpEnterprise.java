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
 * 关于 企业 的实体类
 * Entity class about company
 */
@Table(name="bus_erp_enterprise")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpEnterprise implements Serializable {

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
     * 企业名称
     * Company name
     */
    @Column(name = "t_name")
    private String name;

    /**
     * 企业logo的文件ID
     */
    @Column(name = "file_id_of_logo")
    private Long fileIdOfLogo;

    /**
     * 企业管理者的用户账号ID
     * User account ID of enterprise manager
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

}
