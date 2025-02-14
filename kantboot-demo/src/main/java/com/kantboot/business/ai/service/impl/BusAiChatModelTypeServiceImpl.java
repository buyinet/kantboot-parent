package com.kantboot.business.ai.service.impl;

import com.kantboot.business.ai.domain.entity.BusAiChatModelType;
import com.kantboot.business.ai.repository.BusAiChatModelTypeRepository;
import com.kantboot.business.ai.service.IBusAiChatModelTypeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusAiChatModelTypeServiceImpl implements IBusAiChatModelTypeService {

    @Resource
    private BusAiChatModelTypeRepository repository;

    @Override
    public List<BusAiChatModelType> getAll() {
        return repository.getAllByOrderBySortAsc();
    }

    @Override
    public BusAiChatModelType getById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
