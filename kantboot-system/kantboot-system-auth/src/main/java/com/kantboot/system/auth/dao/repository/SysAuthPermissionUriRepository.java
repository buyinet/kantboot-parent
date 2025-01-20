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

    /**
     * 根据权限ids获取权限URL
     * Get permission URL by permission ids
     *
     * @param permissionIds 权限ids
     *                      Permission ids
     * @return 权限URL
     *          Permission URL
     */
    @Query("""
            SELECT uri.uri FROM SysAuthPermissionUri uri
            WHERE uri.permissionId IN (:permissionIds)
    """)
    List<String> findUrisByPermissionIds(List<Long> permissionIds);

    /**
     * 根据权限codes获取权限URL
     */
    @Query("""
            SELECT uri.uri FROM SysAuthPermissionUri uri
            WHERE uri.permissionCode IN (:permissionCodes)
            """)
    List<String> findUrisByPermissionCodes(List<String> permissionCodes);

}
