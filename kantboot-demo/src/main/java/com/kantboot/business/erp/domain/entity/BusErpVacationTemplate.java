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
import java.util.List;

/**
 * 关于 假期模板 的实体类
 * Entity class about vacation template
 * @author 方某方
 */
@Table(name="bus_erp_vacation_template")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpVacationTemplate implements Serializable {

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
     * 企业ID
     */
    @Column(name = "enterprise_id")
    private Long enterpriseId;

    /**
     * 模板名称
     * Template name
     */
    @Column(name = "t_name")
    private String name;

    /**
     * 周六是否休息
     * Is Saturday rest
     */
    @Column(name = "is_saturday_rest")
    private Boolean isSaturdayRest;

    /**
     * 周日是否休息
     * Is Sunday rest
     */
    @Column(name = "is_sunday_rest")
    private Boolean isSundayRest;

    @OneToMany
    @JoinColumn(name = "vacation_template_id")
    private List<BusErpVacationRestDay> restDays;

    /**
     * 是否默认
     * Is default
     */
    @Column(name = "is_default")
    private Boolean isDefault;

}
