package com.kantboot.functional.pay.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.functional.pay.order.constants.PayOrderStatusCodeConstants;
import com.kantboot.functional.pay.order.dao.repository.FunctionalPayOrderLogRepository;
import com.kantboot.functional.pay.order.dao.repository.FunctionalPayOrderRepository;
import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrderLog;
import com.kantboot.functional.pay.order.exception.FunctionalPayOrderException;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.event.EventEmit;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class FunctionalPayOrderServiceImpl implements IFunctionalPayOrderService {

    @Resource
    private FunctionalPayOrderRepository repository;

    @Resource
    private FunctionalPayOrderLogRepository logRepository;

    @Resource
    private EventEmit eventEmit;

    @Resource
    private CacheUtil cacheUtil;

    @Override
    public FunctionalPayOrder generate(PayOrderGenerateDTO dto) {
        // 生成支付订单
        FunctionalPayOrder order = new FunctionalPayOrder();
        // 设置用户账号ID
        order.setUserAccountId(dto.getUserAccountId());
        // 设置订单金额
        order.setAmount(dto.getAmount());
        // 设置业务编码
        order.setBusinessCode(dto.getBusinessCode());
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
        logRepository.save(orderLog);
        return save;
    }

    @Override
    public FunctionalPayOrder getById(Long id) {
        return repository.findById(id).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
    }

    @Transactional
    @Override
    public FunctionalPayOrder cancel(Long id) {
        // 锁住，防止取消和支付订单同时进行
        if(!cacheUtil.lock("payOrder:cancelOrPaySuccess"+id)){
            // 提示订单正在被操作
            throw FunctionalPayOrderException.PAY_ORDER_OPERATING;
        }

        FunctionalPayOrder functionalPayOrder = repository.findById(id).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(!PayOrderStatusCodeConstants.UNPAID.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态已不为未支付，不能取消
            throw FunctionalPayOrderException.PAY_ORDER_NOT_UNPAID;
        }
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.CANCELED);
        FunctionalPayOrder save = repository.save(functionalPayOrder);
        // 添加到日志
        FunctionalPayOrderLog orderLog = BeanUtil.copyProperties(save, FunctionalPayOrderLog.class);
        orderLog.setId(null);
        orderLog.setPayOrderId(save.getId());

        cacheUtil.unlock("payOrder:cancelOrPaySuccess"+id);
        return save;
    }

    @Transactional
    @Override
    public void paySuccess(Long payOrderId,String payMethodCode, String payMethodAdditionalInfo) {
        FunctionalPayOrder functionalPayOrder = repository.findById(payOrderId)
                .orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);

        if(PayOrderStatusCodeConstants.PAID.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为已支付，提示订单异常
            // TODO 订单异常 要增加处理方式
            throw FunctionalPayOrderException.PAY_ORDER_EXCEPTION;
        }

        // 设置订单状态为已支付
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.PAID);
        // 设置订单状态的支付方式
        functionalPayOrder.setPayMethodCode(payMethodCode);
        // 设置支付方式的额外信息
        functionalPayOrder.setPayMethodAdditionalInfo(payMethodAdditionalInfo);
        repository.save(functionalPayOrder);
        // 添加到日志
        FunctionalPayOrderLog orderLog = BeanUtil.copyProperties(functionalPayOrder, FunctionalPayOrderLog.class);
        orderLog.setId(null);
        orderLog.setPayOrderId(functionalPayOrder.getId());
        logRepository.save(orderLog);
        // 通知对应的业务系统
        eventEmit.to("functionalPayOrder:payOrderPaid:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }

}