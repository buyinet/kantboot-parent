package com.kantboot.user.account.dao.repository;

import com.kantboot.user.account.domain.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

    /**
     * 根据用户名查询
     */
    UserAccount findByUsername(String username);

    /**
     * 根据手机号查询
     */
    UserAccount findByPhoneAreaCodeAndPhone(String phoneAreaCode, String phone);

    /**
     * 根据邮箱查询
     */
    UserAccount findByEmail(String email);

    /**
     * 根据手机区号和手机号码查询是否存在
     * @param phoneAreaCode 手机区号
     * @param phone 手机号码
     * @return 是否存在
     */
    boolean existsByPhoneAreaCodeAndPhone(String phoneAreaCode, String phone);

    /**
     * 根据邮箱查询是否存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean existsByEmail(String email);

    /**
     * 根据用户名查询是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);

    /**
     * 根据ids查询
     */
    @Query("""
            select ua from UserAccount ua
            where ua.id in :ids
    """)
    List<UserAccount> getByIds(List<Long> ids);

}
