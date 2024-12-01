package com.kantboot.system.auth.dao.repository;

import com.kantboot.system.auth.domain.entity.SysAuthPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysAuthPermissionRepository extends JpaRepository<SysAuthPermission,Long> {
}
