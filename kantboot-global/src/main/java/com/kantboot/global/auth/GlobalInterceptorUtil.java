package com.kantboot.global.auth;

import com.kantboot.system.auth.dao.repository.SysAuthPermissionRepository;
import com.kantboot.system.auth.dao.repository.SysAuthPermissionUriRepository;
import com.kantboot.user.account.service.IUserAccountAuthRoleService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局过滤器工具类
 * Global filter utility class
 */
@Slf4j
@Component
public class GlobalInterceptorUtil {

    @Resource
    private SysAuthPermissionUriRepository repository;

    @Resource
    private SysAuthPermissionRepository permissionRepository;

    @Resource
    private SysAuthPermissionUriRepository permissionUriRepository;

    @Resource
    private IUserAccountAuthRoleService userAccountAuthRoleService;




    /**
     * 权限Url列表
     * Permission Url list
     */
    public List<String> getPermissionUris() {
        List<String> uris = repository.findUris();
        // 去除重复
        uris = uris.stream().distinct().toList();
        return uris;
    }

    /**
     * 检测是否放行
     * Check if it is released
     */
    public boolean checkPass(String uri) {
        // 开始时间
        // Start time
        long start = System.currentTimeMillis();

        // 不放行名单
        List<String> uris = getPermissionUris();
        //  是否放行
        //  Whether to release
        boolean pass = true;
        //  不放行的uri
        // Uri that is not released
        String passUri = "";

        for (String u : uris) {
            if (uri.matches(u.replace("*", ".*"))) {
                pass = false;
                passUri = u;
                break;
            }
        }
        if(!pass){
            // 获取当前用户的权限
            // Get the current user's permissions
            List<Long> permissionIds = userAccountAuthRoleService.getRoleIdsBySelf();
            // 获取当前uri的权限
            // Get the permission of the current uri
            List<String> urisByPermissionIds = permissionUriRepository.findUrisByPermissionIds(permissionIds);
            // 是否有权限
            // Whether there is permission
            boolean hasPermission = false;
            for (String u : urisByPermissionIds) {
                if (uri.matches(u.replace("*", ".*"))) {
                    hasPermission = true;
                    break;
                }
            }
            // 结束时间
            // End time
            long end = System.currentTimeMillis();
            log.info("Step1, Check whether the uri is released, uri: {}, pass: {}, passUri: {}, hasPermission: {}, time: {}ms", uri, pass, passUri, hasPermission, end - start);
            return hasPermission;
        }
        // 结束时间
        // End time
        long end = System.currentTimeMillis();
        log.info("Step2, Check whether the uri is released, uri: {}, pass: {}, passUri: {}, time: {}ms", uri, pass, passUri, end - start);
        return true;
    }

}
