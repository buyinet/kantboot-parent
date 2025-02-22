package com.kantboot.business.ai.i18n.service.impl;

import com.kantboot.business.ai.i18n.dao.repository.BusAiChatModelTypeI18nRepository;
import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelTypeI18n;
import com.kantboot.business.ai.i18n.service.IBusAiChatModelTypeI18nService;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusAiChatModelTypeI18nServiceImpl implements IBusAiChatModelTypeI18nService {

    @Resource
    private BusAiChatModelTypeI18nRepository i18nRepository;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Override
    public List<BusAiChatModelTypeI18n> getAll(String languageCode) {
        if (languageCode==null) {
            languageCode = httpRequestHeaderUtil.getLanguageCode();
         }
        return i18nRepository.getByLanguageCode(languageCode);
    }

}
