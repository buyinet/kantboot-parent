package com.kantboot.business.erp.service.impl;

import com.kantboot.business.erp.dao.repository.BusErpCertificateRepository;
import com.kantboot.business.erp.dao.repository.BusErpEnterpriseRepository;
import com.kantboot.business.erp.domain.entity.BusErpCertificate;
import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.exception.BusinessErpException;
import com.kantboot.business.erp.service.IBusErpCertificateService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusErpCertificateServiceImpl implements IBusErpCertificateService {


    @Resource
    private BusErpCertificateRepository repository;

    @Resource
    private BusErpEnterpriseRepository enterpriseRepository;

    @Resource
    private IUserAccountService userAccountService;

    /**
     * 根据企业ID获取证书列表
     * Get the certificate list according to the enterprise ID
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     * @return 证书列表
     */
    @Override
    public List<BusErpCertificate> getBySelf(Long enterpriseId) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpEnterprise busErpEnterprise = enterpriseRepository.findById(enterpriseId).orElse(null);
        if(busErpEnterprise == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        // 判断是否是当前用户的企业
        // Determine whether it is the enterprise of the current user
        if(!selfId.equals(busErpEnterprise.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        return repository.getByEnterpriseId(enterpriseId);
    }

    /**
     * 添加
     * Add
     *
     * @param entity 部门实体
     *               Department entity
     * @return 返回添加成功的部门实体
     *         Return the department entity added successfully
     */
    @Override
    public BusErpCertificate addBySelf(BusErpCertificate entity) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpEnterprise busErpEnterprise = enterpriseRepository.findById(entity.getEnterpriseId()).orElse(null);
        if(busErpEnterprise == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        // 判断是否是当前用户的企业
        // Determine whether it is the enterprise of the current user
        if(!selfId.equals(busErpEnterprise.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        return repository.save(entity);
    }

    /**
     * 更新
     * Update
     *
     * @param entity 部门实体
     *               Department entity
     * @return 返回更新成功的部门实体
     *         Return the department entity updated successfully
     */
    @Override
    public BusErpCertificate updateBySelf(BusErpCertificate entity) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpCertificate origin = repository.findById(entity.getId()).orElse(null);
        if(origin == null){
            // 提示部门不存在
            // Prompt that the department does not exist
            throw BusinessErpException.DEPARTMENT_NOT_EXIST;
        }
        BusErpEnterprise busErpEnterprise = enterpriseRepository.findById(origin.getEnterpriseId()).orElse(null);
        if(busErpEnterprise == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        // 判断是否是当前用户的企业
        // Determine whether it is the enterprise of the current user
        if(!selfId.equals(busErpEnterprise.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        origin.setEnterpriseId(entity.getEnterpriseId());
        origin.setName(entity.getName());
        return repository.save(origin);
    }

    /**
     * 删除
     * Delete
     *
     * @param id 部门ID
     *           Department ID
     */
    @Override
    public void deleteBySelf(Long id) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpCertificate department = repository.findById(id).orElse(null);
        if(department == null){
            // 提示部门不存在
            // Prompt that the department does not exist
            throw BusinessErpException.DEPARTMENT_NOT_EXIST;
        }
        BusErpEnterprise busErpEnterprise = enterpriseRepository.findById(department.getEnterpriseId()).orElse(null);
        if(busErpEnterprise == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        // 判断是否是当前用户的企业
        // Determine whether it is the enterprise of the current user
        if(!selfId.equals(busErpEnterprise.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        repository.deleteById(id);
    }

    /**
     * 根据ID获取
     * Get by ID
     *
     * @param id 部门ID
     *           Department ID
     */
    @Override
    public BusErpCertificate getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<BusErpCertificate> getAll(Long enterpriseId) {
        return repository.getByEnterpriseId(enterpriseId);
    }
}
