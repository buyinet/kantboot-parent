package com.kantboot.system.dict.slot;

import com.kantboot.util.rest.exception.BaseException;
import org.springframework.stereotype.Component;

@Component
public class SysDictI18nSlot {

    public String translateText(String sourceLanguageCode, String targetLanguage, String text) {
        // 提示没有插件实现全局翻译
        // No plugin implements global translation
        throw BaseException.of("NoPluginImplementsGlobalTranslation","No plugin implements global translation");
    }
}
