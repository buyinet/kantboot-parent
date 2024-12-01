package com.kantboot.global.auth;

import com.kantboot.system.auth.dao.repository.SysAuthPermissionUriRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 全局过滤器工具类
 * Global filter utility class
 */
@Component
public class GlobalInterceptorUtil {

    @Resource
    private SysAuthPermissionUriRepository repository;

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
        // 不放行名单
        List<String> uris = getPermissionUris();
        for (String u : uris) {
            if (uri.matches(u.replace("*", ".*"))) {
                return false;
            }
        }
        return true;
    }

}
