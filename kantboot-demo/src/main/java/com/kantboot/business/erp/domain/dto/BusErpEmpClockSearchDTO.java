package com.kantboot.business.erp.domain.dto;

import lombok.Data;

@Data
public class BusErpEmpClockSearchDTO {

    /**
     * 员工ID
     * Employee ID
     */
    private Long empId;

    /**
     * 企业ID
     * Enterprise ID
     */
    private Long enterpriseId;

    /**
     * 开始打卡时间
     * Start clock in time
     */
    private String gmtClockStart;

    /**
     * 结束打卡时间
     * End clock in time
     */
    private String gmtClockEnd;

}
