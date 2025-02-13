package com.kantboot.business.ovo.coin.reporisoty;

import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusOvoCoinRechargeRecordRepository extends JpaRepository<BusOvoCoinRechargeRecord, Long> {

    BusOvoCoinRechargeRecord findByPayOrderId(Long payOrderId);

}
