package com.kantboot.user.balance.web.admin.controller;

import com.kantboot.user.balance.domain.dto.ChangeRecordDTO;
import com.kantboot.user.balance.domain.entity.UserAccountBalance;
import com.kantboot.user.balance.service.IUserAccountBalanceService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import com.kantboot.util.sc.controller.BaseAdminController;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-balance-web/admin/userAccountBalance")
public class UserAccountBalanceControllerOfAdmin
        extends BaseAdminController<UserAccountBalance,Long> {

    @Resource
    private IUserAccountBalanceService service;

    @RequestMapping("/add")
    public RestResult<Object> add(@RequestBody ChangeRecordDTO dto) {
        return RestResult.success(service.add(dto), CommonSuccessStateCodeAndMsg.OPERATION_SUCCESS);
    }


}
