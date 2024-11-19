package com.kantboot.system.setting.service.impl;

import com.kantboot.system.setting.domain.entity.SysSetting;
import com.kantboot.system.setting.repository.SysSettingRepository;
import com.kantboot.system.setting.service.ISysSettingService;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 系统设置服务层实现类
 * 与上一个版本，暂时取消了redis缓存，虽然redis缓存可以提高性能，但是现阶段的项目规模不大，暂时不需要，也更好的维护
 * @author 方某方
 */
@Service
public class SysSettingServiceImpl implements ISysSettingService {

    @Resource
    private SysSettingRepository repository;

    @Override
    public HashMap<String,String> getMapByGroupCode(String groupCode) {
        HashMap<String,String> result = new HashMap<>(100);
        List<SysSetting> byGroupCode = repository.findByGroupCode(groupCode);
        for (SysSetting sysSetting : byGroupCode) {
            result.put(sysSetting.getCode(),sysSetting.getValue());
        }
        return result;
    }

    @Override
    public String getValue(String groupCode, String code) {
        SysSetting byGroupCodeAndCode = repository.findByGroupCodeAndCode(groupCode, code);
        if(byGroupCodeAndCode == null){
            throw BaseException.of("noHasSysSettingValue","不存在此配置，分组编码："+groupCode+"，编码："+code);
        }
        return byGroupCodeAndCode.getValue();
    }

    @Override
    public String getValue(String code) {
        SysSetting byCode = repository.findByCode(code);
        if(byCode == null){
            throw BaseException.of("noHasSysSettingValue","不存在此配置，编码："+code);
        }
        return byCode.getValue();
    }

    @Override
    public String getValueNoException(String code) {
        SysSetting byCode = repository.findByCode(code);
        if(byCode == null){
            return null;
        }
        return byCode.getValue();
    }

    @Override
    public SysSetting save(SysSetting sysSetting) {
        return repository.save(sysSetting);
    }

    @Override
    public void setValue(String groupCode, String code, String value) {
        SysSetting byGroupCodeAndCode = repository.findByGroupCodeAndCode(groupCode, code);
        byGroupCodeAndCode.setValue(value);
        repository.save(byGroupCodeAndCode);
    }

    @Override
    public void setValue(String code, String value) {
        SysSetting byCode = repository.findByCode(code);
        byCode.setValue(value);
        repository.save(byCode);
    }
}
