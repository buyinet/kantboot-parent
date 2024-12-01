package com.kantboot.business.erp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.business.erp.dao.repository.BusErpVacationRestDayRepository;
import com.kantboot.business.erp.dao.repository.BusErpVacationTemplateRepository;
import com.kantboot.business.erp.domain.entity.BusErpVacationRestDay;
import com.kantboot.business.erp.domain.entity.BusErpVacationTemplate;
import com.kantboot.business.erp.service.IBusErpVacationTemplateService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 假期模板的业务实现
 * Vacation template business implementation
 *
 * @author 方某方
 */
@Service
public class BusErpVacationTemplateServiceImpl
        implements IBusErpVacationTemplateService {

    @Resource
    private BusErpVacationTemplateRepository repository;

    @Resource
    private BusErpVacationRestDayRepository restDayRepository;

    @Override
    public void addBySelf(BusErpVacationTemplate template) {
        // 复制另外休息日
        // Copy rest days
        List<BusErpVacationRestDay> restDays = BeanUtil.copyToList(template.getRestDays(), BusErpVacationRestDay.class);
        if (restDays == null) {
            // 如果没有另外休息日，就设置为空列表
            // If there are no other rest days, set to an empty list
            restDays = List.of();
        }
        // 将模板的休息日设置为空，以防止保存时出现冗余数据
        // Set the rest days of the template to null to prevent redundant data when saving
        template.setRestDays(null);
        // 保存模板到数据库
        // Save the template to the database
        BusErpVacationTemplate save = repository.save(template);
        // 设置休息日的模板id
        // Set the template id of the rest day
        for (BusErpVacationRestDay restDay : restDays) {
            restDay.setVacationTemplateId(save.getId());
        }
        // 保存休息日到数据库
        // Save rest days to the database
        restDayRepository.saveAll(restDays);
    }

    @Override
    public void updateBySelf(BusErpVacationTemplate template) {
        // 复制另外休息日
        // Copy rest days
        List<BusErpVacationRestDay> restDays = BeanUtil.copyToList(template.getRestDays(), BusErpVacationRestDay.class);
        if (restDays == null) {
            // 如果没有另外休息日，就设置为空列表
            // If there are no other rest days, set to an empty list
            restDays = List.of();
        }
        // 将模板的休息日设置为空，以防止保存时出现冗余数据
        // Set the rest days of the template to null to prevent redundant data when saving
        template.setRestDays(null);
        // 保存模板到数据库
        // Save the template to the database
        BusErpVacationTemplate save = repository.save(template);
        // 设置休息日的模板id
        // Set the template id of the rest day
        for (BusErpVacationRestDay restDay : restDays) {
            restDay.setVacationTemplateId(save.getId());
        }
        // 删除原有的休息日
        // Delete the original rest day
        restDayRepository.deleteByVacationTemplateId(save.getId());
        // 保存休息日到数据库
        // Save rest days to the database
        restDayRepository.saveAll(restDays);
    }

    @Override
    public void deleteBySelf(Long id) {
        // 删除休息日
        // Delete rest days
        restDayRepository.deleteByVacationTemplateId(id);

        // 删除模板
        // Delete the template
        repository.deleteById(id);
    }

    @Override
    public List<BusErpVacationTemplate> getByEnterpriseId(Long enterpriseId) {
        return repository.findByEnterpriseId(enterpriseId);
    }


    @Override
    public BusErpVacationTemplate getDefaultByEnterpriseId(Long enterpriseId) {
        // 查询默认的假期模板
        // Query the default vacation template
        List<BusErpVacationTemplate> byEnterpriseIdAndIsDefault = repository.findByEnterpriseIdAndIsDefault(enterpriseId, true);
        // 如果length为0，说明没有默认的假期模板，返回null
        if (byEnterpriseIdAndIsDefault.isEmpty()) {
            return null;
        }
        return byEnterpriseIdAndIsDefault.getFirst();
    }
}











