package com.kantboot.user.balance.schedule;

import com.kantboot.user.balance.constants.UserAccountBalanceChangeRecordStatusCodeConstants;
import com.kantboot.user.balance.dao.repository.UserAccountBalanceChangeRecordRepository;
import com.kantboot.user.balance.domain.entity.UserAccountBalanceChangeRecord;
import com.kantboot.user.balance.service.IUserAccountBalanceService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class UserBalanceSchedule {

    @Resource
    private IUserAccountBalanceService service;

    @Resource
    private UserAccountBalanceChangeRecordRepository recordRepository;

    @Scheduled(fixedRate = 1000 * 5)
    public void userBalanceScheduleHandle() {
        // 获取所有状态为“处理中（processing）”的记录
        List<UserAccountBalanceChangeRecord> byStatusCode
                = recordRepository.findByStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.PROCESSING);
        int size = byStatusCode.size();
        if (size > 0) {
            log.info("用户余额变更记录处理中，数量：{}", byStatusCode.size());
        }
        byStatusCode.forEach(record -> {
            service.handle(record.getId());
        });
        // 获取所有状态为“未处理（notProcessed）”，且超过三分钟的记录
        List<UserAccountBalanceChangeRecord> byStatusCodeAndGmtCreate
                = recordRepository.findByStatusCodeAndGmtCreate(
                UserAccountBalanceChangeRecordStatusCodeConstants.NOT_PROCESSED,
                new Date(System.currentTimeMillis() - 1000 * 60 * 3));
        int size2 = byStatusCodeAndGmtCreate.size();
        if (size2 > 0) {
            log.info("用户余额变更记录未处理，数量：{}", byStatusCodeAndGmtCreate.size());
        }
        byStatusCodeAndGmtCreate.forEach(record -> {
            service.handle(record.getId());
        });
    }

}
