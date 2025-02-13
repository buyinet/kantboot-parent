package com.kantboot.user.balance.service.impl;

import com.kantboot.user.balance.constants.UserAccountBalanceChangeRecordStatusCodeConstants;
import com.kantboot.user.balance.dao.repository.UserAccountBalanceChangeRecordRepository;
import com.kantboot.user.balance.dao.repository.UserAccountBalanceRepository;
import com.kantboot.user.balance.domain.dto.ChangeRecordDTO;
import com.kantboot.user.balance.domain.entity.UserAccountBalance;
import com.kantboot.user.balance.domain.entity.UserAccountBalanceChangeRecord;
import com.kantboot.user.balance.service.IUserAccountBalanceService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserAccountBalanceServiceImpl implements IUserAccountBalanceService {

    @Resource
    private UserAccountBalanceChangeRecordRepository changeRecordRepository;

    @Resource
    private UserAccountBalanceRepository repository;

    @Resource
    private CacheUtil cacheUtil;

    @Override
    public UserAccountBalanceChangeRecord add(ChangeRecordDTO record) {
        // 创建一条记录,且设置状态为“未处理”
        UserAccountBalanceChangeRecord changeRecord = new UserAccountBalanceChangeRecord()
                .setBalanceCode(record.getBalanceCode())
                .setNumber(record.getNumber())
                .setReasonCode(record.getReasonCode())
                .setUserAccountId(record.getUserAccountId())
                .setStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.NOT_PROCESSED);
        UserAccountBalanceChangeRecord save = changeRecordRepository.save(changeRecord);
        return handle(save.getId());
    }

    @Override
    public UserAccountBalanceChangeRecord handle(Long recordId) {
        Optional<UserAccountBalanceChangeRecord> byId1 = changeRecordRepository.findById(recordId);
        if (byId1.isEmpty()) {
            // 余额记录不存在
            throw BaseException.of("balanceRecordNotExist", "Balance record does not exist");
        }

        UserAccountBalanceChangeRecord record = byId1.get();

        // 如果为是扣除余额时
        if (record.getNumber().doubleValue() < 0) {
            // 如果是扣除余额，则判断余额是否充足
            UserAccountBalance balance =
                    repository.findByUserAccountIdAndBalanceCode(record.getUserAccountId(), record.getBalanceCode());
            if (balance == null || balance.getNumber().doubleValue() < -(record.getNumber().doubleValue())) {
                // 设置状态为“处理失败”
                record.setStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.FAILED);
                // 设置失败原因编码为“余额不足”
                record.setFailReasonCode("balanceNotEnough");
                changeRecordRepository.save(record);
                // 余额不足
                throw BaseException.of("balanceNotEnough", "Balance not enough");
            }
        }

        String cacheKey = "userAccountBalanceChangeRecord:handleSuccess:"
                + record.getUserAccountId() + ":" + record.getBalanceCode();

        if(!cacheUtil.lock(cacheKey,2, TimeUnit.HOURS)){
            // 有别的金额在处理
            log.info("Have another amount processing");
            // 将余额记录设置为“处理中”
            record.setStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.PROCESSING);
            return changeRecordRepository.save(record);
        }

        UserAccountBalance balance =
                repository.findByUserAccountIdAndBalanceCode(record.getUserAccountId(), record.getBalanceCode());

        if(balance == null){
            balance = new UserAccountBalance()
                    .setUserAccountId(record.getUserAccountId())
                    .setBalanceCode(record.getBalanceCode())
                    .setNumber(BigDecimal.ZERO);
        }

        // 添加金额
        balance.setNumber(balance.getNumber().add(record.getNumber()));

        repository.save(balance);

        // 设置状态为“处理成功”
        record.setStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.PROCESSED);
        UserAccountBalanceChangeRecord save = changeRecordRepository.save(record);

        // 解锁
        cacheUtil.unlock(cacheKey);
        return save;
    }

    @Override
    public void handleFail(Long id, String failReasonCode) {
        Optional<UserAccountBalanceChangeRecord> byId = changeRecordRepository.findById(id);
        if (byId.isPresent()) {
            // 设置状态为“处理失败”
            UserAccountBalanceChangeRecord record = byId.get();
            record.setStatusCode(UserAccountBalanceChangeRecordStatusCodeConstants.FAILED);
            // 设置失败原因编码
            record.setFailReasonCode(failReasonCode);
            changeRecordRepository.save(record);
        }
    }
}
