package com.kantboot.system.auth.dao.repository;

import com.kantboot.system.auth.domain.entity.SysAuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 关于角色的数据访问接口
 * Data access interface about role
 *
 * @author 方某方
 */
public interface SysAuthRoleRepository extends JpaRepository<SysAuthRole,Long> {

}
