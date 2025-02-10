package com.kantboot.user.balance.service;

import com.kantboot.user.balance.domain.dto.ChangeRecordDTO;
import com.kantboot.user.balance.domain.entity.UserAccountBalanceChangeRecord;

public interface IUserAccountBalanceService {

    /**
     * 添加余额记录
     */
    UserAccountBalanceChangeRecord add(ChangeRecordDTO record);

    /**
     * 处理成功
     */
    UserAccountBalanceChangeRecord handle(Long id);

    /**
     * 处理失败
     */
    void handleFail(Long id, String failReasonCode);


}
