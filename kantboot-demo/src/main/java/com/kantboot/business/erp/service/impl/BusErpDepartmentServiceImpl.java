package com.kantboot.business.erp.service.impl;

import com.kantboot.business.erp.dao.repository.BusErpDepartmentRepository;
import com.kantboot.business.erp.dao.repository.BusErpEnterpriseRepository;
import com.kantboot.business.erp.domain.entity.BusErpDepartment;
import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.exception.BusinessErpException;
import com.kantboot.business.erp.service.IBusErpDepartmentService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusErpDepartmentServiceImpl
    implements IBusErpDepartmentService {

    @Resource
    private BusErpDepartmentRepository repository;

    @Resource
    private BusErpEnterpriseRepository enterpriseRepository;

    @Resource
    private IUserAccountService userAccountService;

    @Override
    public List<BusErpDepartment> getBySelf(Long enterpriseId) {
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

    @Override
    public BusErpDepartment addBySelf(BusErpDepartment entity) {
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

    @Override
    public BusErpDepartment updateBySelf(BusErpDepartment entity) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpDepartment origin = repository.findById(entity.getId()).orElse(null);
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

    @Override
    public void deleteBySelf(Long id) {
        // 获取当前用户ID
        // Get the current user ID
        Long selfId = userAccountService.getSelfId();
        BusErpDepartment department = repository.findById(id).orElse(null);
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

    @Override
    public BusErpDepartment getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
