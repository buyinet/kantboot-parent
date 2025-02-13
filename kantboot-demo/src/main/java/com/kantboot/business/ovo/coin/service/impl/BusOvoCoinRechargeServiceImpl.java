package com.kantboot.business.ovo.coin.service.impl;

import com.kantboot.business.ovo.coin.domain.dto.BusOvoCoinRechargeDTO;
import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeOption;
import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeRecord;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRechargeOptionRepository;
import com.kantboot.business.ovo.coin.reporisoty.BusOvoCoinRechargeRecordRepository;
import com.kantboot.business.ovo.coin.service.IBusOvoCoinRechargeService;
import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BusOvoCoinRechargeServiceImpl implements IBusOvoCoinRechargeService {

    @Resource
    private BusOvoCoinRechargeRecordRepository rechargeRecordRepository;

    @Resource
    private BusOvoCoinRechargeOptionRepository rechargeOptionRepository;

    @Resource
    private IFunctionalPayOrderService payOrderService;


    @Transactional
    @Override
    public FunctionalPayOrder generatePayOrder(BusOvoCoinRechargeDTO dto) {
        BusOvoCoinRechargeOption rechargeOption = rechargeOptionRepository.findById(dto.getOptionId())
                .orElseThrow(() -> BaseException.of("rechargeOptionIsNotExist",
                        "The recharge option is not exist"));
        // TODO 汇率转换，现在统统使用人民币
        dto.setCurrencyCode("CNY");

        // 生成订单
        FunctionalPayOrder payOrder = payOrderService.generate(new PayOrderGenerateDTO()
                .setUserAccountId(dto.getUserAccountId())
                .setAmount(rechargeOption.getAmount())
                .setCurrency("CNY")
                .setBusinessCode("ovo.coin.recharge")
        );


        BusOvoCoinRechargeRecord rechargeRecord = new BusOvoCoinRechargeRecord()
                .setNumber(rechargeOption.getNumber())
                .setUserAccountId(dto.getUserAccountId())
                .setCurrencyCode(dto.getCurrencyCode())
                .setAmount(rechargeOption.getAmount())
                .setAmountOfOption(rechargeOption.getAmount())
                .setOptionId(dto.getOptionId())
                .setStatusCode("notProcessed")
                .setPayOrderId(payOrder.getId());

        // 保存充值记录
        rechargeRecordRepository.save(rechargeRecord);

        return payOrder;
    }

}
