package com.kantboot.business.ovo.coin.reporisoty;

import com.kantboot.business.ovo.coin.domain.entity.BusOvoCoinRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusOvoCoinRecordRepository
        extends JpaRepository<BusOvoCoinRecord, Long> {
}
