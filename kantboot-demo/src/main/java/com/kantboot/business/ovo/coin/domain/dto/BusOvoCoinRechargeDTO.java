package com.kantboot.business.ovo.coin.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BusOvoCoinRechargeDTO implements Serializable {

    /**
     * 选项ID
     */
    private Long optionId;

    /**
     * 用户ID
     */
    private Long userAccountId;

    /**
     * 货币编码
     */
    private String currencyCode;

}
