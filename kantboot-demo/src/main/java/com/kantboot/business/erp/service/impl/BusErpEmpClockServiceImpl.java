package com.kantboot.business.erp.service.impl;

import com.kantboot.business.erp.dao.repository.BusErpEmpClockRepository;
import com.kantboot.business.erp.dao.repository.BusErpEmpRepository;
import com.kantboot.business.erp.domain.dto.BusErpEmpClockSearchDTO;
import com.kantboot.business.erp.domain.entity.BusErpEmp;
import com.kantboot.business.erp.domain.entity.BusErpEmpClock;
import com.kantboot.business.erp.service.IBusErpEmpClockService;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.sc.service.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BusErpEmpClockServiceImpl
        extends BaseServiceImpl<BusErpEmpClock, Long>
        implements IBusErpEmpClockService {

    @Resource
    private BusErpEmpClockRepository repository;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Resource
    private BusErpEmpRepository empRepository;

    @Resource
    private IUserAccountService userAccountService;

    @Override
    public BusErpEmpClock clock(BusErpEmpClock entity) {
        // 设置打卡时间为当前时间
        // Set the clock in time to the current time
        entity.setGmtClock(new Date());

        // 设置IP
        // Set IP
        entity.setIp(httpRequestHeaderUtil.getIp());

        // 获取上次的打卡记录
        // Get the last clock in record
        BusErpEmpClock lastClock = repository.findFirstByEmpIdOrderByGmtClockDesc(entity.getEmpId());

        // 如果上次打卡记录为空，直接保存
        // If the last clock in record is empty，save directly
        if (lastClock == null) {
            return repository.save(entity);
        }

        // 如果上次打卡记录的打卡类型和本次打卡类型不相同，直接保存
        // If the clock in type of the last clock in record is different from the clock in type of this clock in, save directly
        if (!lastClock.getType().equals(entity.getType())) {
            return repository.save(entity);
        }

        // 如果上次的打卡记录是上班，且本次打卡也是上班，type为1代表上班，2代表下班
        // If the last clock in record is clock in and this clock in is also clock in，type 1 means clock in，2 means clock out
        if (lastClock.getType().equals(1) && entity.getType().equals(1)) {
            // 如果上次打卡时间和本次打卡时间相差小于等于12小时，不保存
            // If the difference between the last clock in time and this clock in time is less than or equal to 12 hours, do not save
            if (entity.getGmtClock().getTime() - lastClock.getGmtClock().getTime() <= 12 * 60 * 60 * 1000) {
                return lastClock;
            }
        }

        // 如果上次的打卡记录是下班，且本次打卡也是下班，type为1代表上班，2代表下班
        // If the last clock in record is clock out and this clock in is also clock out，type 1 means clock in，2 means clock out
        if (lastClock.getType().equals(2) && entity.getType().equals(2)) {
            entity.setId(lastClock.getId());
            return repository.save(entity);
        }

        return repository.save(entity);
    }

    @Override
    public BusErpEmpClock getLastClockBySelf(Long enterpriseId) {
        BusErpEmp emp = empRepository.getByUserAccountIdAndEnterpriseId(userAccountService.getSelfId(), enterpriseId);
        return repository.findFirstByEmpIdOrderByGmtClockDesc(emp.getId());
    }

    @Override
    public BusErpEmpClock clockBySelf(Integer type, Long enterpriseId) {
        BusErpEmp emp = empRepository.getByUserAccountIdAndEnterpriseId(userAccountService.getSelfId(), enterpriseId);

        BusErpEmpClock entity = new BusErpEmpClock();

        // 设置员工ID
        // Set employee ID
        entity.setEmpId(emp.getId());

        // 设置打卡类型
        // Set clock in type
        entity.setType(type);

        // 设置为非补卡
        // Set to non-reissue
        entity.setIsReissue(false);

        return clock(entity);
    }

    @Override
    public PageResult searchBySelf(PageParam<BusErpEmpClockSearchDTO> param) {
        Long enterpriseId = param.getData().getEnterpriseId();
        BusErpEmp emp = empRepository.getByUserAccountIdAndEnterpriseId(userAccountService.getSelfId(), enterpriseId);

        if (param.getData() == null) {
            // 如果查询条件为空，初始化一个空的查询条件
            // If the query condition is empty, initialize an empty query condition
            param.setData(new BusErpEmpClockSearchDTO());
        }

        // 设置员工ID
        // Set employee ID
        param.getData().setEmpId(emp.getId());

        // 设置查询条件的打卡时间范围为当前月份
        // Set the clock in time range of the query condition to the current month
        Page<BusErpEmpClock> search = repository.search(param.getData(), param.getPageable());
        return PageResult.of(search);
    }

    /**
     * 根据ID获取打卡记录
     * Get clock in record by ID
     */
    @Override
    public BusErpEmpClock getById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
