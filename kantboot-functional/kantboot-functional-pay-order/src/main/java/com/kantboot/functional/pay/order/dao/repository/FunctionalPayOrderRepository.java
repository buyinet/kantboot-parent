package com.kantboot.functional.pay.order.dao.repository;

import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 支付订单数据访问接口
 * @author 方某方
 */
public interface FunctionalPayOrderRepository
    extends JpaRepository<FunctionalPayOrder, Long> {
}
