package com.kantboot.business.erp.service.impl;

import com.kantboot.business.erp.dao.repository.BusErpEnterpriseRepository;
import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.exception.BusErpEnterpriseException;
import com.kantboot.business.erp.service.IBusErpEnterpriseService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
            throw BusErpEnterpriseException.ENTERPRISE_NOT_EXIST;
        }
        if(!selfId.equals(origin.getUserAccountId())){
            // 提示非法操作
            // Prompt illegal operation
            throw BusErpEnterpriseException.ILLEGAL_OPERATION;
        }
        origin.setName(enterprise.getName());
        return repository.save(origin);
    }

}