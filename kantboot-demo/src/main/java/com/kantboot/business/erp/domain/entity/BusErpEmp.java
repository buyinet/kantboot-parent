package com.kantboot.business.erp.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 关于 员工 的实体类
 * Entity class about employee
 */
@Table(name="bus_erp_emp")
@Entity
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class BusErpEmp implements Serializable {

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
     * 员工姓名
     * Employee name
     */
    @Column(name = "t_name")
    private String name;

    /**
     * 员工头像的文件ID
     * File ID of employee avatar
     */
    @Column(name = "file_id_of_avatar")
    private Long fileIdOfAvatar;

    /**
     * 性别编码
     * Gender code
     */
    @Column(name = "gender_code")
    private String genderCode;

    /**
     * 生日
     */
    @Column(name = "gmt_birthday")
    private Date gmtBirthday;

    /**
     * 员工编号
     * Employee number
     */
    @Column(name = "t_no")
    private String no;

    /**
     * 企业ID
     * Enterprise ID
     */
    @Column(name = "enterprise_id")
    private Long enterpriseId;

    /**
     * 部门ID
     * Department ID
     */
    @Column(name = "department_id")
    private Long departmentId;

    /**
     * 上班时间
     * Work time
     */
    @Column(name = "work_time")
    private String workTime;

    /**
     * 下班时间
     * Off work time
     */
    @Column(name = "off_work_time")
    private String offWorkTime;

    /**
     * 下班时间是否是次日
     * Whether off work time is next day
     */
    @Column(name = "is_next_day_off_work")
    private Boolean isNextDayOffWork;

    /**
     * 入职时间
     * Entry time
     */
    @Column(name = "gmt_entry")
    private Date gmtEntry;

    /**
     * 用户名
     * Username
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     * Password
     */
    @Column(name = "password")
    private String password;

    /**
     * 邮箱
     * Email
     */
    @Column(name = "email")
    private String email;

    /**
     * 手机区号
     * Phone area code
     */
    @Column(name = "phone_area_code")
    private String phoneAreaCode;

    /**
     * 手机号
     * Phone number
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 用户账号ID
     */
    @Column(name = "user_account_id")
    private Long userAccountId;

    /**
     * 关联员工的证书
     * Certificates of associated employees
     */
    @OneToMany
    @JoinColumn(name="emp_id")
    @Fetch(FetchMode.SELECT)
    private List<BusErpEmpCertificate> empCertificates;

    /**
     * 假期模板ID
     */
    @Column(name = "vacation_template_id")
    private Long vacationTemplateId;

    @OneToOne
    @JoinColumn(name = "vacation_template_id", insertable = false, updatable = false)
    private BusErpVacationTemplate vacationTemplate;

}
