package com.kantboot.system.auth.dao.repository;

import com.kantboot.system.auth.domain.entity.SysAuthRolePermission;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 角色权限关联
 * Role permission association
 *
 * @author 方某方
 *
 */
public interface SysAuthRolePermissionRepository extends JpaRepository<SysAuthRolePermission,Long> {

    /**
     * 通过角色ids获取权限ids
     * Get permission ids by role ids
     *
     * @param roleIds 角色ids
     *                Role ids
     * @return 权限ids
     *          Permission ids
     */
    @Query("""
            SELECT rp.permissionId FROM SysAuthRolePermission rp
            WHERE rp.roleId IN (:roleIds)
    """)
    List<Long> getPermissionIdsByRoleIds(List<Long> roleIds);

    /**
     * 通过角色ids获取权限codes
     */
    @Query("""
            SELECT p.code FROM SysAuthRolePermission rp
            JOIN SysAuthPermission p ON rp.permissionId = p.id
            WHERE rp.roleId IN (:roleIds)
            """)
    List<String> getPermissionCodesByRoleIds(List<Long> roleIds);

    /**
     * 根据角色Id删除
     */
    @Modifying
    @Transactional
    @Query("delete from SysAuthRolePermission rp where rp.roleId = :roleId")
    void deleteByRoleId(Long roleId);

}
