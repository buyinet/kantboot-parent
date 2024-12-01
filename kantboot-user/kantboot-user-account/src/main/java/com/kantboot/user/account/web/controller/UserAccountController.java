package com.kantboot.user.account.web.controller;

import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-account-web/userAccount")
public class UserAccountController {

    @Resource
    private IUserAccountService service;

    /**
     * 获取自己的用户账号
     * @return 自身的用户账号信息
     */
    @RequestMapping("/getSelf")
    public RestResult<Object> getSelf() {
        return RestResult.success(service.getSelf(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 根据id获取用户账号
     * getById
     */
    @RequestMapping("/getById")
    public RestResult<Object> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 根据ids获取用户账号
     */
    @RequestMapping("/getByIds")
    public RestResult<Object> getByIds(@RequestParam("ids") List<Long> ids) {
        return RestResult.success(service.getByIds(ids), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 保存自己的用户账号
     * @param userAccount 用户账号信息
     * @return 保存后的用户账号信息
     */
    @RequestMapping("/saveSelf")
    public RestResult<Object> saveSelf(@RequestBody UserAccount userAccount) {
        return RestResult.success(service.saveSelf(userAccount), CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 修改密码
     */
    @RequestMapping("/changePassword")
    public RestResult<Void> changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        service.changePassword(oldPassword, newPassword);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.CHANGE_SUCCESS);
    }

    /**
     * 判断是否存在手机号
     */
    @RequestMapping("/existsByPhone")
    public RestResult<Boolean> existsByPhone(
            @RequestParam("phoneAreaCode") String phoneAreaCode,
            @RequestParam("phone") String phone) {
        return RestResult.success(service.existsByPhone(phoneAreaCode, phone), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
