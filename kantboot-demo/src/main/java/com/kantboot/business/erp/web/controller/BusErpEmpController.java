package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpEmp;
import com.kantboot.business.erp.service.IBusErpEmpService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business-erp-web/erpEmp")
public class BusErpEmpController {

    @Resource
    private IBusErpEmpService service;

    /**
     * 添加员工
     * Add employee
     *
     * @param emp 员工
     *            Employee
     *
     * @return 添加的员工
     *         Added employee
     */
    @RequestMapping("/add")
    public RestResult<?> add(@RequestBody BusErpEmp emp) {
        return RestResult.success(service.add(emp), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 更新员工
     * Update employee
     *
     * @param emp 员工
     *            Employee
     * @return 更新的员工
     *          Updated employee
     */
    @RequestMapping("/update")
    public RestResult<?> update(@RequestBody BusErpEmp emp) {
        return RestResult.success(service.update(emp), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 查询员工
     * Query employees by enterprise ID
     *
     * @param emp 员工
     *            Employee
     * @return 员工列表
     *          List of employees
     */
    @RequestMapping("/get")
    public RestResult<?> get(@RequestBody BusErpEmp emp) {
        return RestResult.success(service.get(emp), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 删除员工
     * Delete employee
     *
     * @param id 员工ID
     *           Employee ID
     * @return 删除结果
     *          Delete result
     */
    @RequestMapping("/delete")
    public RestResult<?> delete(@RequestParam("id") Long id) {
        service.delete(id);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 根据ID查询员工
     * Query employees by ID
     *
     * @param id 员工ID
     *           Employee ID
     * @return 员工
     *          Employee
     */
    @RequestMapping("/getById")
    public RestResult<?> getById(@RequestParam("id") Long id) {
        return RestResult.success(service.getById(id), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 修改密码
     * Change password
     *
     * @param id 员工ID
     *           Employee ID
     * @param password 密码
     *                 Password
     * @return 修改结果
     *          Change result
     */
    @RequestMapping("/changePassword")
    public RestResult<?> changePassword(@RequestParam("id") Long id, @RequestParam("password") String password) {
        service.changePassword(id, password);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/login")
    public RestResult<?> login(@RequestParam("username") String username, @RequestParam("password") String password,
    @RequestParam("enterpriseId") Long enterpriseId
    ) {
        return RestResult.success(service.login(username, password, enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    @RequestMapping("/getSelf")
    public RestResult<?> getSelf(@RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getSelf(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
