package com.kantboot.functional.template.service;

import com.kantboot.functional.template.domain.vo.FunctionalTemplateGenerateVO;

public interface IFunctionalTemplateService {

    /**
     * 根据分组编码、编码和语言编码获取模板
     * @param code 编码
     * @param languageCode 语言编码
     * @param params 参数
     * @return 模板
     */
    FunctionalTemplateGenerateVO getByCodeAndLanguageCode(String code, String languageCode, Object params);

    /**
     * 根据分组编码获取模板
     * @return 模板
     */
    FunctionalTemplateGenerateVO getTemplate(String code, Object params);

}
