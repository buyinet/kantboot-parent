package com.kantboot.user.balance.dao.repository;

import com.kantboot.user.balance.domain.entity.UserAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountBalanceRepository extends JpaRepository<UserAccountBalance, Long> {

    /**
     * 根据用户ID和余额类型代码查询
     */
    UserAccountBalance findByUserAccountIdAndBalanceCode(Long userAccountId, String balanceCode);

}
