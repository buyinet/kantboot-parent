package com.kantboot.business.erp.domain.dto;

import lombok.Data;

@Data
public class BusErpEmpClockSearchDTO {

    /**
     * 用户账号ID
     * User account ID
     */
    private Long userAccountId;

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
