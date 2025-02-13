package com.kantboot.functional.pay.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.functional.pay.order.constants.PayOrderStatusCodeConstants;
import com.kantboot.functional.pay.order.dao.repository.FunctionalPayOrderLogRepository;
import com.kantboot.functional.pay.order.dao.repository.FunctionalPayOrderRepository;
import com.kantboot.functional.pay.order.domain.dto.PayOrderGenerateDTO;
import com.kantboot.functional.pay.order.domain.dto.PaySuccessDTO;
import com.kantboot.functional.pay.order.domain.dto.RefundDTO;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrderLog;
import com.kantboot.functional.pay.order.exception.FunctionalPayOrderException;
import com.kantboot.functional.pay.order.service.IFunctionalPayOrderService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.event.EventEmit;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

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

    /**
     * 添加到支付订单日志
     */
    private void addToPayOrderLog(FunctionalPayOrder order) {
        // 保存支付订单日志到数据库
        FunctionalPayOrderLog orderLog = BeanUtil.copyProperties(order, FunctionalPayOrderLog.class);
        orderLog.setId(null);
        orderLog.setPayOrderId(order.getId());
        logRepository.save(orderLog);
    }

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
        // 添加支付订单日志
        addToPayOrderLog(save);
        return save;
    }

    @Override
    public FunctionalPayOrder getById(Long id) {
        return repository.findById(id).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
    }

    @Transactional
    @Override
    public FunctionalPayOrder cancel(Long orderId) {
        // 锁住，防止取消订单和支付订单同时进行
        if(!cacheUtil.lock("payOrder:cancelOrPay"+orderId,10, TimeUnit.MINUTES)) {
            // 提示订单正在被操作
            throw FunctionalPayOrderException.PAY_ORDER_OPERATING;
        }

        FunctionalPayOrder functionalPayOrder = repository.findById(orderId).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(!PayOrderStatusCodeConstants.UNPAID.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态已不为未支付，不能取消
            throw FunctionalPayOrderException.PAY_ORDER_NOT_UNPAID;
        }
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.CANCELED);
        FunctionalPayOrder save = repository.save(functionalPayOrder);
        // 添加支付订单日志
        addToPayOrderLog(save);

        cacheUtil.unlock("payOrder:cancelOrPaySuccess"+orderId);
        return save;
    }


    @Override
    public void checkPrePay(Long payOrderId) {
        // 锁住，防止取消订单和支付订单同时进行
        if(!cacheUtil.lock("payOrder:checkPrePay"+payOrderId,10, TimeUnit.MINUTES)) {
            // 提示订单正在被操作
            throw FunctionalPayOrderException.PAY_ORDER_OPERATING;
        }
        // 检查订单状态是否为未支付
        FunctionalPayOrder functionalPayOrder
                = repository.findById(payOrderId).orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(PayOrderStatusCodeConstants.PAID.equals(functionalPayOrder.getStatusCode())) {
            // 提示订单状态为已支付，提示订单异常
            // TODO 订单异常 要增加处理方
            throw FunctionalPayOrderException.PAY_ORDER_NOT_UNPAID;
        }
        if(PayOrderStatusCodeConstants.CANCELED.equals(functionalPayOrder.getStatusCode())) {
            // 提示订单状态为已取消，提示订单异常
            throw FunctionalPayOrderException.PAY_ORDER_EXCEPTION;
        }
        // 发送支付订单检查
        eventEmit.to("functionalPayOrder:checkPrePay:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }


    @Transactional
    @Override
    public void payComplete(PaySuccessDTO dto) {
        Long payOrderId = dto.getPayOrderId();
        String payMethodCode = dto.getPayMethodCode();
        String payMethodAdditionalInfo = dto.getPayMethodAdditionalInfo();
        BigDecimal fee = dto.getFee();

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
        // 设置支付汇率
        functionalPayOrder.setFee(fee);
        // 设置订单实付金额
        functionalPayOrder.setPaidAmount(functionalPayOrder.getAmount().add(fee));

        // 保存支付订单到数据库
        repository.save(functionalPayOrder);
        // 添加支付订单日志
        addToPayOrderLog(functionalPayOrder);

        // 通知对应的业务系统
        eventEmit.to("functionalPayOrder:payOrderPaid:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }

    @Override
    public void refundApply(RefundDTO dto) {
        Long payOrderId = dto.getPayOrderId();
        FunctionalPayOrder functionalPayOrder = repository.findById(payOrderId)
                .orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);

        // 判断订单状态是否为已退款
        if(PayOrderStatusCodeConstants.REFUNDED.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为已退款
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDED;
        }

        // 判断订单是否为退款中
        if(PayOrderStatusCodeConstants.REFUNDING.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为退款中
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDING;
        }

        // 判断是否在等待退款确认中
        if(PayOrderStatusCodeConstants.REFUND_CHECKING.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为等待退款确认
            throw FunctionalPayOrderException.PAY_ORDER_WAIT_REFUND_CHECK;
        }

        // 判断订单是否非已支付
        if(!PayOrderStatusCodeConstants.PAID.equals(functionalPayOrder.getStatusCode())){
            // 提示该订单并未支付
            throw FunctionalPayOrderException.PAY_ORDER_NOT_PAID;
        }

        // 修改状态为等待退款确认
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.REFUND_CHECKING);
        functionalPayOrder.setRefundReasonCode(dto.getRefundReasonCode());
        functionalPayOrder.setRefundReasonDescription(dto.getRefundReasonDescription());
        functionalPayOrder.setRefundAmount(dto.getRefundAmount());
        functionalPayOrder.setIsAllRefund(dto.getIsAllRefund());
        functionalPayOrder.setIsSubtractFeeWhenRefund(dto.getIsSubtractFeeWhenRefund());
        functionalPayOrder.setRefundAdditionalInfo(dto.getRefundAdditionalInfo());
        repository.save(functionalPayOrder);

        // 添加支付订单日志
        addToPayOrderLog(functionalPayOrder);
    }

    @Override
    public void refund(RefundDTO dto) {
        Long payOrderId = dto.getPayOrderId();
        FunctionalPayOrder functionalPayOrder = repository.findById(payOrderId)
                .orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(PayOrderStatusCodeConstants.REFUNDED.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为已退款
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDED;
        }
        if(PayOrderStatusCodeConstants.REFUNDING.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为退款中
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDING;
        }
        if(!PayOrderStatusCodeConstants.REFUND_CHECKING.equals(functionalPayOrder.getStatusCode())
                || !PayOrderStatusCodeConstants.PAID.equals(functionalPayOrder.getStatusCode())
        ){
            // 提示订单状态为非支付状态
            throw FunctionalPayOrderException.PAY_ORDER_NOT_PAID;
        }

        // 设置订单状态为退款中
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.REFUNDING);
        functionalPayOrder.setRefundReasonCode(dto.getRefundReasonCode());
        functionalPayOrder.setRefundReasonDescription(dto.getRefundReasonDescription());
        functionalPayOrder.setRefundAmount(dto.getRefundAmount());
        functionalPayOrder.setIsAllRefund(dto.getIsAllRefund());
        functionalPayOrder.setIsSubtractFeeWhenRefund(dto.getIsSubtractFeeWhenRefund());
        functionalPayOrder.setRefundAdditionalInfo(dto.getRefundAdditionalInfo());
        repository.save(functionalPayOrder);
        // 添加支付订单日志
        addToPayOrderLog(functionalPayOrder);

        // 发送事件给支付方式处理
        eventEmit.to("functionalPayOrder:refund:toPayMethod:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }

    @Override
    public void refundById(Long payOrderId) {
        FunctionalPayOrder functionalPayOrder = repository.findById(payOrderId)
                .orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(PayOrderStatusCodeConstants.REFUNDED.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为已退款
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDED;
        }
        if(PayOrderStatusCodeConstants.REFUNDING.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为退款中
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDING;
        }
        if(!PayOrderStatusCodeConstants.REFUND_CHECKING.equals(functionalPayOrder.getStatusCode())
                || !PayOrderStatusCodeConstants.PAID.equals(functionalPayOrder.getStatusCode())
        ){
            // 提示订单状态为非支付状态
            throw FunctionalPayOrderException.PAY_ORDER_NOT_PAID;
        }

        // 设置订单状态为退款中
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.REFUNDING);
        repository.save(functionalPayOrder);

        // 添加到支付订单日志
        addToPayOrderLog(functionalPayOrder);

        // 发送事件给支付方式处理
        eventEmit.to("functionalPayOrder:refund:toPayMethod:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }

    @Override
    public void refundComplete(Long payOrderId) {
        FunctionalPayOrder functionalPayOrder = repository.findById(payOrderId)
                .orElseThrow(() -> FunctionalPayOrderException.PAY_ORDER_NOT_FOUND);
        if(PayOrderStatusCodeConstants.REFUNDED.equals(functionalPayOrder.getStatusCode())){
            // 提示订单状态为已退款
            throw FunctionalPayOrderException.PAY_ORDER_REFUNDED;
        }
        // 设置订单状态为已退款
        functionalPayOrder.setStatusCode(PayOrderStatusCodeConstants.REFUNDED);
        repository.save(functionalPayOrder);

        // 添加到支付订单日志
        addToPayOrderLog(functionalPayOrder);

        // 发送事件给业务系统处理
        eventEmit.to("functionalPayOrder:refundComplete:"+functionalPayOrder.getBusinessCode(),payOrderId);
    }
}