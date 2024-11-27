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
 * 关于 假期模板-休息日 的实体类
 * Entity class about vacation template-rest day
 * @author 方某方
 */
@Table(name="bus_erp_vacation_rest_day")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpVacationRestDay implements Serializable {

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
     * 假期模板ID
     * Vacation template ID
     */
    @Column(name = "vacation_template_id")
    private Long vacationTemplateId;

    /**
     * 日期
     * Date
     */
    @Column(name = "t_date")
    private Date date;

}
