package com.kantboot.business.ai.i18n.event;

import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import com.kantboot.business.ai.domain.entity.BusAiChatModelType;
import com.kantboot.business.ai.i18n.dao.repository.BusAiChatModelI18nRepository;
import com.kantboot.business.ai.i18n.dao.repository.BusAiChatModelLabelI18nRepository;
import com.kantboot.business.ai.i18n.dao.repository.BusAiChatModelTypeI18nRepository;
import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelI18n;
import com.kantboot.business.ai.i18n.module.entity.BusAiChatModelTypeI18n;
import com.kantboot.business.ai.service.IBusAiChatModelService;
import com.kantboot.business.ai.service.IBusAiChatModelTypeService;
import com.kantboot.system.dict.domain.entity.SysLanguage;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.system.dict.service.ISysLanguageService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.event.annotation.EventOn;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于主体的保存事件
 */
@Component
public class BodySaveEvent {

    @Resource
    private BusAiChatModelTypeI18nRepository typeI18nRepository;

    @Resource
    private CacheUtil cacheUtil;

    @Resource
    private IBusAiChatModelTypeService typeService;

    @Resource
    private ISysDictI18nService dictI18nService;

    @Resource
    private ISysLanguageService languageService;

    @Resource
    private BusAiChatModelI18nRepository i18nRepository;

    @Resource
    private BusAiChatModelLabelI18nRepository labelI18nRepository;

    @Resource
    private IBusAiChatModelService modelService;



    @Async
    @Transactional
    @EventOn("busAiChatModelType:save")
    public void onBusAiChatModelTypeSave(Long bodyId) {
        if(!cacheUtil.lock("busAiChatModelType:save:"+bodyId)){
            throw BaseException.of("typeInOperation","The operation is being processed");
        }
        // 删除所有关于该主体的国际化
        typeI18nRepository.deleteByBodyId(bodyId);
        BusAiChatModelType byId = typeService.getById(bodyId);
        List<SysLanguage> bySupport = languageService.getBySupport();
        List<BusAiChatModelTypeI18n> i18ns = new ArrayList<>();
        for (SysLanguage language : bySupport) {
            // 通过英文翻译成全部支持的语言
            String s = dictI18nService.translateText("en", language.getCode(), byId.getName());
            BusAiChatModelTypeI18n i18n = new BusAiChatModelTypeI18n();
            i18n.setBodyId(bodyId);
            i18n.setLanguageCode(language.getCode());
            i18n.setName(s);
            i18ns.add(i18n);
        }
        typeI18nRepository.saveAll(i18ns);
        cacheUtil.unlock("busAiChatModelType:save:"+bodyId);
    }

    @EventOn("busAiChatModel.save")
    public void onBusAiChatModelSave(Long bodyId) {
        if(!cacheUtil.lock("busAiChatModel:save:"+bodyId)){
            throw BaseException.of("modelInOperation","The operation is being processed");
        }
        // 删除所有关于该主体的国际化
        i18nRepository.deleteByBodyId(bodyId);
        labelI18nRepository.deleteByModelId(bodyId);

        BusAiChatModel model = modelService.getById(bodyId);


        List<SysLanguage> bySupport = languageService.getBySupport();
        List<BusAiChatModelTypeI18n> i18ns = new ArrayList<>();
        for (SysLanguage language : bySupport) {
            // 通过英文翻译成全部支持的语言
            String name = dictI18nService.translateText("en", language.getCode(), model.getName());
            String description = dictI18nService.translateText("en", language.getCode(), model.getDescription());
            String introduction = dictI18nService.translateText("en", language.getCode(), model.getIntroduction());
            BusAiChatModelI18n modelI18n = new BusAiChatModelI18n();
            modelI18n.setBodyId(bodyId);
            modelI18n.setLanguageCode(language.getCode());
            modelI18n.setName(name);
            modelI18n.setDescription(description);
            modelI18n.setIntroduction(introduction);
            i18nRepository.save(modelI18n);
            // TODO 标签国际化
        }

    }

}
