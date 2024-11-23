package com.kantboot.business.erp.service;

import com.kantboot.business.erp.domain.dto.BusErpEmpClockSearchDTO;
import com.kantboot.business.erp.domain.entity.BusErpEmpClock;
import com.kantboot.util.jpa.param.PageParam;
import com.kantboot.util.jpa.result.PageResult;
import com.kantboot.util.sc.service.IBaseService;

/**
 * 中文:
 *    员工打卡服务
 * English:
 *    Employee clock in service
 */
public interface IBusErpEmpClockService extends IBaseService<BusErpEmpClock,Long> {

    /**
     * 打卡
     * Clock
     *
     * @param clock 打卡信息
     *              Clock information
     *
     */
    BusErpEmpClock clock(BusErpEmpClock clock);

    /**
     * 用户自身打卡
     * User self clock
     *
     * @param type 打卡类型
     *             Clock type
     *        enterpriseId 企业ID
     *             Enterprise ID
     */
    BusErpEmpClock clockBySelf(Integer type,Long enterpriseId);

    /**
     * 获取用户自身的最近一次打卡记录
     * Get the latest clock in record of the user itself
     *
     * @param enterpriseId 企业ID
     *                     Enterprise ID
     *
     * @return BusErpEmpClockIn 最后一次打卡记录
     *                          The last clock in record
     */
    BusErpEmpClock getLastClockBySelf(Long enterpriseId);


    /**
     * 查询用户自身的打卡记录
     * Query the clock in record of the user itself
     *
     * @param param 查询参数
     *              Query parameter
     *
     * @return PageResult<BusErpEmpClock> 打卡记录
     *                                   Clock in record
     */
    PageResult searchBySelf(PageParam<BusErpEmpClockSearchDTO> param);

    /**
     * 根据ID获取打卡记录
     * Get the clock in record according to the ID
     */
    BusErpEmpClock getById(Long id);


}
