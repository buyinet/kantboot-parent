package com.kantboot.user.account.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户的属性扩展
 * User attribute extension
 */
@Data
@MappedSuperclass
public class UserAccountAttrExt implements Serializable {

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
     */
    @Column(name = "gmt_entry")
    private Date gmtEntry;

}
