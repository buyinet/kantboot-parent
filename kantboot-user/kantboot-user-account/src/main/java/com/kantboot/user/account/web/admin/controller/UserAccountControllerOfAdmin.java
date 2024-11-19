package com.kantboot.user.account.web.admin.controller;

import com.kantboot.user.account.dao.repository.UserAccountRepository;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.crypto.password.impl.KantbootPassword;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.exception.BaseException;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-account-web/admin/userAccount")
public class UserAccountControllerOfAdmin extends BaseAdminController<UserAccount,Long> {

    @Resource
    private UserAccountRepository repository;

    @Resource
    private KantbootPassword kantbootPassword;

    @Resource
    private IUserAccountService service;

    @Override
    @RequestMapping("/save")
    public RestResult<Object> save(@RequestBody UserAccount entity) {
        // 如果用户名不为空
        if(entity.getUsername()!=null){
            // 判断用户名是否存在
            UserAccount byUsername = repository.findByUsername(entity.getUsername());
            if (byUsername != null && !byUsername.getId().equals(entity.getId())) {
                throw BaseException.of("usernameIsExist", "用户名已存在");
            }
        }
        // 如果密码不为空与ID不为空
        if(entity.getPassword()!=null && entity.getId()!=null){
            // 根据ID查询用户
            UserAccount byId = repository.findById(entity.getId()).orElse(null);
            // 如果用户不存在
            if(byId==null){
                throw BaseException.of("userNotExist", "用户不存在");
            }
            // 如果密码不相等
            if(!byId.getPassword().equals(entity.getPassword())){
                String password = entity.getPassword();
                String encrypt = kantbootPassword.encrypt(password.trim());
                entity.setPassword(encrypt);
            }
        }
        // 如果密码不为空与ID为空
        if(entity.getPassword()!=null && entity.getId()==null){
            String password = entity.getPassword();
            String encrypt = kantbootPassword.encrypt(password.trim());
            entity.setPassword(encrypt);
        }
        repository.save(entity);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 根据邮箱获取
     */
    @RequestMapping("/getByEmail")
    public RestResult<Object> getByEmail(@RequestParam("email") String email) {
        return RestResult.success(service.getByEmail(email), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
