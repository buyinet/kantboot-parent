package com.kantboot.business.philosophy.ai.character.dao.repository;

import com.kantboot.business.philosophy.ai.character.domain.entity.BusPhilosophyCharacterLanguageSupport;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface BusPhilosophyCharacterLanguageSupportRepository extends JpaRepository<BusPhilosophyCharacterLanguageSupport,Long> {

    /**
     * 根据角色id删除所有语言支持
     * @param modelId 模型id
     */
    @Transactional
    @Modifying
    void deleteByCharacterId(Long modelId);

}
