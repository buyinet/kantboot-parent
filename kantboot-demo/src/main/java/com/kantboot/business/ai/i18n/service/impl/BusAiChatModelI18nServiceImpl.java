package com.kantboot.business.ai.i18n.service.impl;

import com.kantboot.business.ai.i18n.dao.repository.BusAiChatModelI18nRepository;
import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelI18n;
import com.kantboot.business.ai.i18n.service.IBusAiChatModelI18nService;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusAiChatModelI18nServiceImpl implements IBusAiChatModelI18nService {

    @Resource
    private BusAiChatModelI18nRepository repository;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Override
    public List<BusAiChatModelI18n> getByBodyIds(List<Long> bodyIds, String languageCode) {
        if (languageCode == null) {
            languageCode = httpRequestHeaderUtil.getLanguageCode();
        }
        return repository.getByBodyIds(bodyIds, languageCode);
    }
}
