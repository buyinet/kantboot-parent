package com.kantboot.user.location.service.impl;

import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.user.location.dao.repository.UserAccountLocationLogRepository;
import com.kantboot.user.location.dao.repository.UserAccountLocationRepository;
import com.kantboot.user.location.domain.entity.UserAccountLocation;
import com.kantboot.user.location.domain.entity.UserAccountLocationLog;
import com.kantboot.user.location.service.IUserAccountLocationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAccountLocationServiceImpl implements IUserAccountLocationService {

    @Resource
    private UserAccountLocationRepository repository;

    @Resource
    private UserAccountLocationLogRepository logRepository;

    @Resource
    private IUserAccountService userAccountService;

    @Override
    public UserAccountLocation save(UserAccountLocation entity) {
        UserAccountLocation byUserAccountId = repository.findByUserAccountId(entity.getUserAccountId());
        if (byUserAccountId == null) {
            byUserAccountId = repository.save(entity);
            UserAccountLocationLog userAccountLocationLog = new UserAccountLocationLog();
            userAccountLocationLog.setUserAccountId(entity.getUserAccountId());
            userAccountLocationLog.setLatitude(entity.getLatitude());
            userAccountLocationLog.setLongitude(entity.getLongitude());
            userAccountLocationLog.setIp(entity.getIp());
            logRepository.save(userAccountLocationLog);
            return byUserAccountId;
        }

        // 如果entity的latitude和longitude和byUserAccountId的latitude和longitude相同，则不更新
        if (entity.getLatitude().equals(byUserAccountId.getLatitude()) && entity.getLongitude().equals(byUserAccountId.getLongitude())) {
            return repository.save(entity);
        }

        UserAccountLocationLog userAccountLocationLog = new UserAccountLocationLog();
        userAccountLocationLog.setUserAccountId(entity.getUserAccountId());
        userAccountLocationLog.setLatitude(entity.getLatitude());
        userAccountLocationLog.setLongitude(entity.getLongitude());
        userAccountLocationLog.setIp(entity.getIp());
        logRepository.save(userAccountLocationLog);

        return repository.save(entity);
    }

    @Override
    public UserAccountLocation saveSelf(UserAccountLocation entity) {
        entity.setUserAccountId(userAccountService.getSelfId());
        return repository.save(entity);
    }
}
