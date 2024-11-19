package com.kantboot.functional.template.repository;

import com.kantboot.functional.template.domain.entity.FunctionalTemplateI18n;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalTemplateI18nRepository extends JpaRepository<FunctionalTemplateI18n, Long> {

    /**
     * 根据分组编码、编码和语言编码查询
     * @param code 编码
     * @param languageCode 语言编码
     * @return 模板
     */
    FunctionalTemplateI18n findByCodeAndLanguageCode(String code, String languageCode);
}
