package com.kantboot.functional.pay.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.functional.pay.order.constants.PayOrderStatusCodeConstants;
import com.kantboot.functional.pay.order.dao.repository.FunctionalPayOrderRepository;
import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrderLog;
import com.kantboot.functional.pay.order.exception.FunctionalPayOrderException;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class FunctionalPayOrderServiceImpl implements IFunctionalPayOrderService {

    @Resource
    private FunctionalPayOrderRepository repository;

    @Override
    public FunctionalPayOrder generate(PayOrderGenerateDTO dto) {
        // 生成支付订单
        FunctionalPayOrder order = new FunctionalPayOrder();
        // 设置用户账号ID
        order.setUserAccountId(dto.getUserAccountId());
        // 设置订单金额
        order.setAmount(dto.getAmount());
        // 设置产品编码
        order.setProductCode(dto.getProductCode());
        // 设置货币类型
        order.setCurrency(dto.getCurrency());
        // 设置支付方式编码
        order.setPayMethodCode(dto.getPayMethodCode());
        // 设置订单描述
        order.setDescription(dto.getDescription());
        // 设置订单状态
        order.setStatusCode(PayOrderStatusCodeConstants.UNPAID);

        // 保存支付订单到数据库
        FunctionalPayOrder save = repository.save(order);
        // 保存支付订单日志到数据库
        FunctionalPayOrderLog orderLog = BeanUtil.copyProperties(save, FunctionalPayOrderLog.class);
        orderLog.setId(null);
        orderLog.setPayOrderId(save.getId());
        return save;
    }

    @Override
    public FunctionalPayOrder getById(Long id) {
        return repository.findById(id).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
    }


}