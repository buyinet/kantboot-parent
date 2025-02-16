package com.kantboot.business.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.business.ai.domain.dto.BusAiChatModelDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLabel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelLanguageSupport;
import com.kantboot.business.ai.domain.entity.BusAiChatModelPresets;
import com.kantboot.business.ai.repository.*;
import com.kantboot.business.ai.service.IBusAiChatModelService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.event.EventEmit;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusAiChatModelServiceImpl implements IBusAiChatModelService {

    @Resource
    private BusAiChatModelRepository repository;

    @Resource
    private BusAiChatModelLabelRepository labelRepository;

    @Resource
    private BusAiChatModelPresetsRepository presetsRepository;

    @Resource
    private BusAiChatModelSettingRepository settingRepository;

    @Resource
    private BusAiChatModelLanguageSupportRepository languageSupportRepository;

    @Resource
    private EventEmit emit;

    @Resource
    private CacheUtil cacheUtil;

    @Override
    public List<BusAiChatModel> getByTypeId(Long typeId) {
        return repository.getByTypeId(typeId);
    }

    @Override
    public List<BusAiChatModel> getAll() {
        return repository.getAll();
    }

    @Override
    public BusAiChatModel getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void newSave(BusAiChatModelDTO dto) {
        if(dto.getId()!=null){
            if(cacheUtil.hasKey("busAiChatModel:save:"+dto.getId())){
                // 提示正在操作中
                throw BaseException.of("modelInOperation","The operation is being processed");
            }

            languageSupportRepository.deleteByModelId(dto.getId());
            labelRepository.deleteByModelId(dto.getId());
            presetsRepository.deleteByModelId(dto.getId());
            settingRepository.deleteByModelId(dto.getId());
            repository.deleteById(dto.getId());
        }
        BusAiChatModel entity = BeanUtil.copyProperties(dto, BusAiChatModel.class);
        entity.setLabels(null);
        entity.setLanguageSupports(null);
        BusAiChatModel save = repository.save(entity);
        // 发送事件-模型保存成功
        emit.to("busAiChatModel.save",save.getId());
        if (save.getId() == null) {
            return;
        }
        if(dto.getLabels()!=null){
            for (BusAiChatModelLabel label : dto.getLabels()) {
                label.setModelId(save.getId());
            }
            labelRepository.saveAll(dto.getLabels());
        }
        if(dto.getPresets()!=null){
            for (BusAiChatModelPresets preset : dto.getPresets()) {
                preset.setModelId(save.getId());
            }
            presetsRepository.saveAll(dto.getPresets());
        }
        if(dto.getLanguageSupports()!=null){
            for (BusAiChatModelLanguageSupport languageSupport : dto.getLanguageSupports()) {
                languageSupport.setModelId(save.getId());
            }
            languageSupportRepository.saveAll(dto.getLanguageSupports());
        }
        if(dto.getSetting()!=null){
            dto.getSetting().setModelId(save.getId());
            settingRepository.save(dto.getSetting());
        }


    }
}
