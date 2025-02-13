package com.kantboot.business.ovo.coin.event;

import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeOption;
import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeRecord;
import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRecord;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRechargeOptionRepository;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRechargeRecordRepository;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRecordRepository;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.user.balance.domain.dto.ChangeRecordDTO;
import com.kantboot.user.balance.service.IUserAccountBalanceService;
import com.kantboot.util.event.annotation.EventOn;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BusOvoCoinEvent {

    @Resource
    private BusOvoCoinRechargeRecordRepository rechargeRecordRepository;

    @Resource
    private BusOvoCoinRecordRepository coinRecordRepository;

    @Resource
    private IUserAccountBalanceService userAccountBalanceService;

    @Resource
    private IFunctionalPayOrderService functionalPayOrderService;

    @Resource
    private BusOvoCoinRechargeOptionRepository optionRepository;

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

    @EventOn("functionalPayOrder:checkPrePay:ovo.coin.recharge")
    public void ovoCoinRechargeCheckPrePay(Long payOrderId) {
        BusOvoCoinRechargeRecord byPayOrderId = rechargeRecordRepository.findByPayOrderId(payOrderId);
        // 判断订单是否存在
        if(byPayOrderId==null) {
            // 提示订单不存在
            throw BaseException.of("ovoCoinRechargeOrderNotExist", "The order does not exist");
        }
        // 判断订单是否已经完成
        if(byPayOrderId.getStatusCode().equals("success")) {
            // 提示订单已经完成
            throw BaseException.of("ovoCoinRechargeOrderPaid", "The order has been completed");
        }
        FunctionalPayOrder byId = functionalPayOrderService.getById(payOrderId);
        // 判断订单金额是否已修改
        if(!byPayOrderId.getAmount().equals(byId.getAmount())) {
            // 提示订单金额已修改
            throw BaseException.of("ovoCoinRechargeOrderAmountChanged", "The order amount has been modified");
        }
        Optional<BusOvoCoinRechargeOption> byId1 = optionRepository.findById(byPayOrderId.getOptionId());
        if(!byId1.isPresent()) {
            // 提示充值选项已不存在
            throw BaseException.of("ovoCoinRechargeOptionNotExist", "The recharge option does not exist");
        }
        BusOvoCoinRechargeOption option = byId1.get();
        // 检查价格是否一致
        if(!option.getAmount().equals(byPayOrderId.getAmount())) {
            // 提示充值选项价格已变化
            throw BaseException.of("ovoCoinRechargeOptionPriceChanged", "The recharge option price has been changed");
        }

    }

}
