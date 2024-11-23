package com.kantboot.business.erp.service.impl;

import com.kantboot.business.erp.dao.repository.BusErpEnterpriseRepository;
import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.exception.BusinessErpException;
import com.kantboot.business.erp.service.IBusErpEnterpriseService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusErpEnterpriseServiceImpl implements IBusErpEnterpriseService {

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    private BusErpEnterpriseRepository repository;

    @Override
    public BusErpEnterprise addBySelf(BusErpEnterprise enterprise) {
        enterprise.setUserAccountId(userAccountService.getSelfId());
        return repository.save(enterprise);
    }

    @Override
    public BusErpEnterprise updateBySelf(BusErpEnterprise enterprise) {
        Long selfId = userAccountService.getSelfId();
        BusErpEnterprise origin = repository.findById(enterprise.getId()).orElse(null);
        if(origin == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        if(!selfId.equals(origin.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        origin.setName(enterprise.getName());
        origin.setFileIdOfLogo(enterprise.getFileIdOfLogo());
        return repository.save(origin);
    }

    @Override
    public List<BusErpEnterprise> getBySelf() {
        // 获取当前用户的企业
        // Get the enterprise of the current user
        return repository.getByUserAccountId(userAccountService.getSelfId());
    }

    @Override
    public void deleteBySelf(Long id) {
        Long selfId = userAccountService.getSelfId();
        BusErpEnterprise enterprise = repository.findById(id).orElse(null);
        if(enterprise == null){
            // 提示企业不存在
            // Prompt that the enterprise does not exist
            throw BusinessErpException.ENTERPRISE_NOT_EXIST;
        }
        if(!selfId.equals(enterprise.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        repository.deleteById(id);
    }

    @Override
    public BusErpEnterprise getById(Long id) {
        return repository.findById(id).orElse(null);
    }
}