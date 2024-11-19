package com.kantboot.functional.template.service.impl;

import com.kantboot.functional.template.domain.entity.FunctionalTemplate;
import com.kantboot.functional.template.domain.entity.FunctionalTemplateI18n;
import com.kantboot.functional.template.domain.vo.FunctionalTemplateGenerateVO;
import com.kantboot.functional.template.exception.FunctionalTemplateException;
import com.kantboot.functional.template.repository.FunctionalTemplateI18nRepository;
import com.kantboot.functional.template.repository.FunctionalTemplateRepository;
import com.kantboot.functional.template.service.IFunctionalTemplateService;
import com.kantboot.functional.template.util.TemplateUtil;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class FunctionalTemplateServiceImpl implements IFunctionalTemplateService {

    @Resource
    private FunctionalTemplateI18nRepository i18nRepository;

    @Resource
    private FunctionalTemplateRepository repository;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    /**
     * 根据分组编码、编码和语言编码获取模板
     * @param code         编码
     * @param languageCode 语言编码
     * @return 模板
     */
    @SneakyThrows
    @Override
    public FunctionalTemplateGenerateVO getByCodeAndLanguageCode(String code,
                                                                 String languageCode, Object params) {
        FunctionalTemplateI18n byGroupCodeAndCodeAndLanguageCode = i18nRepository.findByCodeAndLanguageCode(code, languageCode);
        if (byGroupCodeAndCodeAndLanguageCode == null) {
            // 没有找到国际化模板，尝试获取默认模板
            FunctionalTemplate byGroupCodeAndCode = repository.findByCode(code);
            if (byGroupCodeAndCode == null) {
                // 没有找到模板，抛出异常，提示模板不存在
                throw FunctionalTemplateException.TEMPLATE_NOT_EXIST;
            }
            FunctionalTemplateGenerateVO functionalTemplateGenerateVO = new FunctionalTemplateGenerateVO();
            functionalTemplateGenerateVO.setTitle(byGroupCodeAndCode.getTitle());
            functionalTemplateGenerateVO.setContent(TemplateUtil.replaceParams(byGroupCodeAndCode.getContent(), params));

            return functionalTemplateGenerateVO;
        }
        FunctionalTemplateGenerateVO functionalTemplateGenerateVO = new FunctionalTemplateGenerateVO();
        functionalTemplateGenerateVO.setTitle(byGroupCodeAndCodeAndLanguageCode.getTitle());
        functionalTemplateGenerateVO.setContent(TemplateUtil.replaceParams(byGroupCodeAndCodeAndLanguageCode.getContent(), params));
        return functionalTemplateGenerateVO;
    }

    /**
     * 根据分组编码、编码获取模板
     * @return 模板
     */
    @Override
    public FunctionalTemplateGenerateVO getTemplate(String code, Object params) {
        return getByCodeAndLanguageCode(code, httpRequestHeaderUtil.getLanguageCode(), params);
    }


}
