package com.kantboot.system.auth.dao.repository;

import com.kantboot.system.auth.domain.entity.SysAuthPermissionUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SysAuthPermissionUriRepository extends JpaRepository<SysAuthPermissionUri,Long> {

    /**
     * 获取所有权限URL
     */
    @Query("select uri.uri from SysAuthPermissionUri uri")
    List<String> findUris();

}
