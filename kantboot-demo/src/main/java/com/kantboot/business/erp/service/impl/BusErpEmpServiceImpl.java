package com.kantboot.business.erp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.business.erp.dao.repository.BusErpEmpCertificateRepository;
import com.kantboot.business.erp.dao.repository.BusErpEmpRepository;
import com.kantboot.business.erp.dao.repository.BusErpEnterpriseRepository;
import com.kantboot.business.erp.domain.entity.BusErpEmp;
import com.kantboot.business.erp.domain.entity.BusErpEmpCertificate;
import com.kantboot.business.erp.domain.entity.BusErpEnterprise;
import com.kantboot.business.erp.exception.BusinessErpException;
import com.kantboot.business.erp.service.IBusErpEmpService;
import com.kantboot.user.account.domain.entity.UserAccount;
import com.kantboot.user.account.domain.vo.LoginVO;
import com.kantboot.user.account.service.IUserAccountLoginService;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.crypto.password.IBasePassword;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BusErpEmpServiceImpl implements IBusErpEmpService {

    @Resource
    private BusErpEnterpriseRepository erpEnterpriseRepository;

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    private IUserAccountLoginService userAccountLoginService;

    @Resource
    private BusErpEmpRepository repository;

    @Resource
    private IBasePassword basePassword;

    @Resource
    private BusErpEmpCertificateRepository empCertificateRepository;

    /**
     * 添加员工
     * Add employee
     *
     * @param emp 员工
     *            Employee
     * @return 添加的员工
     * Added employee
     */
    @Override
    public BusErpEmp add(BusErpEmp emp) {

        emp.setId(null);

        // 获取用户自身的账号ID
        // Get the user's own account ID
        Long selfId = userAccountService.getSelfId();

        // 企业ID
        // Enterprise ID
        Long enterpriseId = emp.getEnterpriseId();

        // 部门ID
        // Department ID
        Long departmentId = emp.getDepartmentId();

        if (enterpriseId == null) {
            // 提示未选择企业
            // Prompt that no enterprise is selected
            throw BusinessErpException.NO_ENTERPRISE_SELECTED;
        }

        if (departmentId == null) {
            // 提示未选择部门
            // Prompt that no department is selected
            throw BusinessErpException.NO_DEPARTMENT_SELECTED;
        }

        BusErpEnterprise busErpEnterprise =
                erpEnterpriseRepository.findById(enterpriseId)
                        .orElseThrow(BusinessErpException.NO_ENTERPRISE_SELECTED);

        // 如果企业的创建者不是当前用户，提示非法操作
        // If the creator of the enterprise is not the current user, prompt illegal operation
        if (!busErpEnterprise.getUserAccountId().equals(selfId)) {
            throw BusinessErpException.ILLEGAL_OPERATION;
        }

        if (emp.getUsername() != null) {
            // 如果用户名已存在，提示用户名已存在
            // If the username already exists, prompt that the username already exists
            if(repository.existsByUsernameAndEnterpriseId(emp.getUsername(),enterpriseId)){
                throw BusinessErpException.USERNAME_EXIST;
            }
        }

        // 如果密码不为空
        // If the password is not empty
        if (emp.getPassword() != null) {
            // 加密密码
            // Encrypt password
            emp.setPassword(basePassword.encrypt(emp.getPassword()));
        }

        List<BusErpEmpCertificate> empCertificates = BeanUtil.copyToList(emp.getEmpCertificates(), BusErpEmpCertificate.class);
        emp.setEmpCertificates(null);
        BusErpEmp save = repository.save(emp);


        if (empCertificates == null) {
            empCertificates = new ArrayList<>();
        }

        for (BusErpEmpCertificate certificate : empCertificates) {
            if (certificate.getCertificateId() == null) {
                continue;
            }
            certificate.setEmpId(save.getId());
        }
        empCertificateRepository.saveAll(empCertificates);

        // 保存员工
        // Save employee
        return save;
    }

    @Override
    public BusErpEmp update(BusErpEmp emp) {
        // 获取用户自身的账号ID
        // Get the user's own account ID
        Long selfId = userAccountService.getSelfId();
        // 不修改密码
        // Do not modify the password
        emp.setPassword(null);
        // 查询员工ID的企业
        // Query the enterprise of the employee ID
        BusErpEmp origin = repository.findById(emp.getId()).orElse(null);
        if (origin == null) {
            // 提示员工不存在
            // Prompt that the employee does not exist
            throw BusinessErpException.EMPLOYEE_NOT_EXIST;
        }
        // 查询员工ID的企业
        // Query the enterprise of the employee ID
        BusErpEnterprise busErpEnterprise
                = erpEnterpriseRepository.findById(origin.getEnterpriseId()).orElseThrow(BusinessErpException.ENTERPRISE_NOT_EXIST);
        // 如果企业的创建者不是当前用户，提示非法操作
        // If the creator of the enterprise is not the current user, prompt illegal operation
        if (!busErpEnterprise.getUserAccountId().equals(selfId)) {
            throw BusinessErpException.ILLEGAL_OPERATION;
        }

        if(emp.getEnterpriseId()==null){
            emp.setEnterpriseId(origin.getEnterpriseId());
        }
        if(emp.getUsername()!=null){
            // 如果用户名已存在，提示用户名已存在
            // If the username already exists, prompt that the username already exists
            if(!("".equals(emp.getUsername()))&&!(emp.getUsername()==null)
                    &&!(emp.getUsername().equals(origin.getUsername()))
                    &&repository.existsByUsernameAndEnterpriseId(emp.getUsername(),origin.getEnterpriseId())){
                throw BusinessErpException.USERNAME_EXIST;
            }
        }



        repository.deleteById(emp.getId());
        List<BusErpEmpCertificate> empCertificates = BeanUtil.copyToList(emp.getEmpCertificates(), BusErpEmpCertificate.class);
        emp.setEmpCertificates(null);
        BusErpEmp save = repository.save(emp);
        if (empCertificates == null) {
            empCertificates = new ArrayList<>();
        }
        for (BusErpEmpCertificate certificate : empCertificates) {
            if (certificate.getCertificateId() == null) {
                continue;
            }
            certificate.setEmpId(save.getId());
        }
        empCertificateRepository.saveAll(empCertificates);
        return save;
    }

    @Override
    public void delete(Long id) {
        // 获取用户自身的账号ID
        // Get the user's own account ID
        Long selfId = userAccountService.getSelfId();
        // 查询员工ID的企业
        // Query the enterprise of the employee ID
        BusErpEmp emp = repository.findById(id).orElse(null);
        if (emp == null) {
            // 提示员工不存在
            // Prompt that the employee does not exist
            throw BusinessErpException.EMPLOYEE_NOT_EXIST;
        }
        // 查询员工ID的企业
        // Query the enterprise of the employee ID
        BusErpEnterprise busErpEnterprise
                = erpEnterpriseRepository.findById(emp.getEnterpriseId()).orElseThrow(BusinessErpException.ENTERPRISE_NOT_EXIST);
        // 如果企业的创建者不是当前用户，提示非法操作
        // If the creator of the enterprise is not the current user, prompt illegal operation
        if (!busErpEnterprise.getUserAccountId().equals(selfId)) {
            throw BusinessErpException.ILLEGAL_OPERATION;
        }

        empCertificateRepository.deleteByEmpId(id);
        repository.deleteById(id);
    }

    @Override
    public List<BusErpEmp> get(BusErpEmp emp) {
        return repository.search(emp);
    }


    @Override
    public BusErpEmp getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void changePassword(Long id, String password) {
        BusErpEmp emp = repository.findById(id).orElse(null);
        if (emp == null) {
            // 提示员工不存在
            // Prompt that the employee does not exist
            throw BusinessErpException.EMPLOYEE_NOT_EXIST;
        }
        // 获取用户自身的账号ID
        // Get the user's own account ID
        Long selfId = userAccountService.getSelfId();
        // 查询员工ID的企业
        // Query the enterprise of the employee ID
        BusErpEnterprise busErpEnterprise
                = erpEnterpriseRepository.findById(emp.getEnterpriseId()).orElseThrow(BusinessErpException.ENTERPRISE_NOT_EXIST);
        // 如果企业的创建者不是当前用户，提示非法操作
        // If the creator of the enterprise is not the current user, prompt illegal operation
        if (!busErpEnterprise.getUserAccountId().equals(selfId)) {
            throw BusinessErpException.ILLEGAL_OPERATION;
        }
        // 加密密码
        // Encrypt password
        emp.setPassword(basePassword.encrypt(password));
        repository.save(emp);
    }

    @Override
    public LoginVO login(String username, String password,Long enterpriseId) {
        BusErpEmp emp = repository.getByUsernameAndEnterpriseId(username, enterpriseId);
        if (emp == null) {
            // 提示用户名或密码错误
            // Prompt that the username or password is incorrect
            log.error("用户名或密码错误-1");
            throw BusinessErpException.USERNAME_OR_PASSWORD_ERROR;
        }
        if (!basePassword.matches(password, emp.getPassword())) {
            // 提示用户名或密码错误
            // Prompt that the username or password is incorrect
            log.error("用户名或密码错误-2");
            throw BusinessErpException.USERNAME_OR_PASSWORD_ERROR;
        }
        if (!emp.getEnterpriseId().equals(enterpriseId)) {
            // 提示用户名或密码错误
            // Prompt that the username or password is incorrect
            log.error("用户名或密码错误-3");
            throw BusinessErpException.USERNAME_OR_PASSWORD_ERROR;
        }
        Long userAccountId = emp.getUserAccountId();
        if (userAccountId == null) {
            // 创建用户账号
            // Create user account
            UserAccount userAccount = userAccountService.createUserAccount(new UserAccount());
            emp.setUserAccountId(userAccount.getId());
            repository.save(emp);
        }
        return userAccountLoginService.loginByUserAccountId(emp.getUserAccountId());
    }

    @Override
    public BusErpEmp getSelf(Long enterpriseId) {
        Long selfId = userAccountService.getSelfId();
        return repository.getByUserAccountIdAndEnterpriseId(selfId, enterpriseId);
    }
}
