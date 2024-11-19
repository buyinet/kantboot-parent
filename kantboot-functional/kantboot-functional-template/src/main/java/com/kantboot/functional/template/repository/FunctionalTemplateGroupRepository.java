package com.kantboot.functional.template.repository;

import com.kantboot.functional.template.domain.entity.FunctionalTemplateGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FunctionalTemplateGroupRepository extends JpaRepository<FunctionalTemplateGroup, Long>{

    @Query(
            """
                FROM FunctionalTemplateGroup
                ORDER BY priority DESC
           """
    )
    List<FunctionalTemplateGroup> getList();

}
