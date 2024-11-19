package com.kantboot.user.account.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.kantboot.user.account.dao.repository.UserAccountTokenRepository;
import com.kantboot.user.account.domain.entity.UserAccountToken;
import com.kantboot.user.account.exception.UserAccountException;
import com.kantboot.user.account.service.IUserAccountTokenService;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserAccountTokenServiceImpl implements IUserAccountTokenService {

    @Resource
    private UserAccountTokenRepository repository;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    /**
     * 获取过期时长
     */
    private long getExpireTime() {
        return 1000 * 60 * 60 * 24 * 7;
    }

    @Override
    public String generateToken(Long userAccountId) {
        // UUID
        String uuid = IdUtil.simpleUUID();
        // 时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 用户账号id+时间戳的MD5
        String md5 = new Digester(DigestAlgorithm.MD5)
                .digestHex(userAccountId + "@" + currentTimeMillis);
        // UUID
        String uuid2 = IdUtil.simpleUUID();
        // 用户id的36进制
        String userAccountId36 = Long.toString(userAccountId, 36);
        // 时间戳的36进制
        String currentTimeMillis36 = Long.toString(currentTimeMillis, 36);
        // 令牌
        String token = uuid + "@" + userAccountId36 + "@" + currentTimeMillis36 + "@" + md5 + "@" + uuid2;
        // 将token随机大写
        for (int i = 0; i < token.length(); i++) {
            if (Math.random() > 0.5) {
                token = token.substring(0, i) + token.substring(i, i + 1).toUpperCase() + token.substring(i + 1);
            }
        }

        UserAccountToken userAccountToken = new UserAccountToken();
        userAccountToken.setUserAccountId(userAccountId);
        userAccountToken.setToken(token);
        // 过期时间
        Date gmtExpire = new Date(currentTimeMillis + getExpireTime());
        userAccountToken.setGmtExpire(gmtExpire);
        // 保存
        repository.save(userAccountToken);
        return token;
    }

    @Override
    public Long getUserAccountIdBySelf() {
        String token = httpRequestHeaderUtil.getToken();
        // 如果token为空
        if (token == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }

        UserAccountToken byToken = repository.findByToken(token);
        // 如果token不存在
        if (byToken == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        // 获取用户账号ID
        Long userAccountId = byToken.getUserAccountId();
        if (userAccountId == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        return userAccountId;
    }

    @Override
    public void switchUser(Long userAccountId) {
        String token = httpRequestHeaderUtil.getToken();
        UserAccountToken userAccountToken = repository.findByToken(token);
        if (userAccountToken == null) {
            // 账号未登录
            throw UserAccountException.NOT_LOGIN;
        }
        userAccountToken.setUserAccountId(userAccountId);
        // 保存
        repository.save(userAccountToken);
    }

    @Override
    public void removeTokenBySelf() {
        String token = httpRequestHeaderUtil.getToken();
        if (token == null) {
            return;
        }
        UserAccountToken byToken = repository.findByToken(token);
        if (byToken != null) {
            repository.delete(byToken);
        }
    }
}
