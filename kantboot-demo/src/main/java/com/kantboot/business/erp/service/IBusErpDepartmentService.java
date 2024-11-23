package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.entity.BusErpDepartment;

import java.util.List;

public interface IBusErpDepartmentService {

    /**
     * 根据企业ID获取
     */
    List<BusErpDepartment> getBySelf(Long enterpriseId);

    /**
     * 添加
     * @param entity 部门实体
     * @return 返回添加成功的部门实体
     */
    BusErpDepartment addBySelf(BusErpDepartment entity);

    /**
     * 更新
     * @param entity 部门实体
     * @return 返回更新成功的部门实体
     */
    BusErpDepartment updateBySelf(BusErpDepartment entity);

    /**
     * 删除
     * @param id 部门ID
     */
    void deleteBySelf(Long id);

    /**
     * 根据ID获取
     * Get by ID
     *
     * @param id 部门ID
     *           Department ID
     */
    BusErpDepartment getById(Long id);

}
