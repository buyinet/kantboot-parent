package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpEmp;
import com.kantboot.user.account.domain.vo.LoginVO;

import java.util.List;

/**
 * 员工服务
 * Employee service
 */
public interface IBusErpEmpService {

    /**
     * 添加员工
     * Add employee
     *
     * @param emp 员工
     *            Employee
     *
     * @return 添加的员工
     *        Added employee
     */
    BusErpEmp add(BusErpEmp emp);

    /**
     * 修改员工
     * Modify employee
     *
     * @param emp 员工
     *            Employee
     *
     * @return 修改的员工
     *        Modified employee
     */
    BusErpEmp update(BusErpEmp emp);

    /**
     * 删除员工
     * Delete employee
     *
     * @param id 员工ID
     *           Employee ID
     */
    void delete(Long id);

    /**
     * 查询员工
     * Query employees by enterprise ID
     *
     * @return 员工
     *        Employee
     */
    List<BusErpEmp> get(BusErpEmp emp);

    /**
     * 根据ID获取员工
     * Get employee by ID
     *
     * @param id 员工ID
     *           Employee ID
     *
     * @return 员工
     *       Employee
     */
    BusErpEmp getById(Long id);

    /**
     * 修改密码
     * Change password
     *
     * @param id 员工ID
     *           Employee ID
     * @param password 密码
     *                 Password
     */
    void changePassword(Long id, String password);

    /**
     * 登录
     * Login
     *
     * @param username 用户名
     *                 Username
     * @param password 密码
     *                 Password
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     *
     * @return 登录结果
     *        Login result
     */
    LoginVO login(String username, String password,Long enterpriseId);


    /**
     * 获取自身信息
     * Get self information
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     */
    BusErpEmp getSelf(Long enterpriseId);

}
