package com.kantboot.user.account.service.impl;

import com.kantboot.user.account.dao.repository.UserAccountRepository;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.service.IUserAccountBindService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserAccountBindServiceImpl implements IUserAccountBindService {

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    private UserAccountRepository repository;

    @Override
    public void skipBind() {
        UserAccount userAccount = userAccountService.getSelf();
        userAccount.setIsTemporary(false);
        repository.save(userAccount);
    }

}
