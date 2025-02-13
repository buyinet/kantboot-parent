package com.kantboot.business.ovo.coin.event;

import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeRecord;
import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRecord;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRechargeRecordRepository;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRecordRepository;
import com.kantboot.user.balance.domain.dto.ChangeRecordDTO;
import com.kantboot.user.balance.service.IUserAccountBalanceService;
import com.kantboot.util.event.annotation.EventOn;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class BusOvoCoinEvent {

    @Resource
    private BusOvoCoinRechargeRecordRepository rechargeRecordRepository;

    @Resource
    private BusOvoCoinRecordRepository coinRecordRepository;

    @Resource
    private IUserAccountBalanceService userAccountBalanceService;

    @EventOn("functionalPayOrder:payOrderPaid:ovo.coin.recharge")
    public void ovoCoinRechargePayOrderPaid(Long payOrderId) {
        BusOvoCoinRechargeRecord byPayOrderId = rechargeRecordRepository.findByPayOrderId(payOrderId);
        BusOvoCoinRecord record = new BusOvoCoinRecord()
                .setTypeCode("recharge")
                .setAmount(byPayOrderId.getAmount())
                .setUserAccountId(byPayOrderId.getUserAccountId())
                .setNumber(byPayOrderId.getNumber());
        coinRecordRepository.save(record);
        userAccountBalanceService.add(
                new ChangeRecordDTO()
                        .setUserAccountId(byPayOrderId.getUserAccountId())
                        .setReasonCode("ovo.coin.recharge")
                        .setNumber(byPayOrderId.getNumber())
                        .setBalanceCode("ovoCoin")
        );

    }

}
