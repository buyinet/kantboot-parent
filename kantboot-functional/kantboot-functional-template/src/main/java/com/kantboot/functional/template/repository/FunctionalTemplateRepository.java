package com.kantboot.functional.template.repository;

import com.kantboot.functional.template.domain.entity.FunctionalTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalTemplateRepository extends JpaRepository<FunctionalTemplate, Long> {

    /**
     * 根据分组编码和编码查询
     * @param groupCode 分组编码
     * @param code 编码
     * @return 模板
     */
    FunctionalTemplate findByGroupCodeAndCode(String groupCode, String code);

    /**
     * 根据编码查询
     */
    FunctionalTemplate findByCode(String code);

}
