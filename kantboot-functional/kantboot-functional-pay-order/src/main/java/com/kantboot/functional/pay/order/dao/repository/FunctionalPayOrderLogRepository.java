package com.kantboot.functional.pay.order.dao.repository;

import com.kantboot.functional.pay.order.domian.entity.FunctionalPayOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalPayOrderLogRepository extends JpaRepository<FunctionalPayOrderLog, Long> {
}
