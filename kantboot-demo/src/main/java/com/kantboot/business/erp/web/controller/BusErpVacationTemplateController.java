package com.kantboot.business.erp.web.controller;

import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;
import com.kantboot.business.erp.service.IBusErpVacationTemplateService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 假期模板控制器
 * Vacation template controller
 *
 * @author 方某方
 *
 */
@RestController
@RequestMapping("/business-erp-web/vacationTemplate")
public class BusErpVacationTemplateController {

    @Resource
    private IBusErpVacationTemplateService service;

    /**
     * 添加自定义模板
     * Add custom template
     *
     * @param template 模板
     *                 Template
     */
    @RequestMapping("addBySelf")
    public RestResult<?> addBySelf(@RequestBody BusErpVacationTemplate template) {
        service.addBySelf(template);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.SAVE_SUCCESS);
    }

    /**
     * 修改自定义模板
     * Modify custom template
     *
     * @param template 模板
     *                 Template
     */
    @RequestMapping("updateBySelf")
    public RestResult<?> updateBySelf(@RequestBody BusErpVacationTemplate template) {
        service.updateBySelf(template);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.CHANGE_SUCCESS);
    }

    /**
     * 根据ID删除模板
     * Delete template by ID
     *
     * @param id ID
     */
    @RequestMapping("deleteById")
    public RestResult<?> deleteById(
            @RequestParam("id") Long id) {
        service.deleteBySelf(id);
        return RestResult.success(null, CommonSuccessStateCodeAndMsg.REMOVE_SUCCESS);
    }

    /**
     * 根据企业id获取
     * Get by enterprise id
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @return 模板列表
     *        Template list
     */
    @RequestMapping("getByEnterpriseId")
    public RestResult<?> getByEnterpriseId(
            @RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getByEnterpriseId(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 获取企业默认模板
     * Get enterprise default template
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @return 模板
     *         Template
     */
    @RequestMapping("getDefaultByEnterpriseId")
    public RestResult<?> getDefaultByEnterpriseId(
            @RequestParam("enterpriseId") Long enterpriseId) {
        return RestResult.success(service.getDefaultByEnterpriseId(enterpriseId), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
