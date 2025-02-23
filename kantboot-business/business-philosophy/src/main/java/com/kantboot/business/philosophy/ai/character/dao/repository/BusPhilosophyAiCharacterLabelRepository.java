package com.kantboot.business.philosophy.ai.character.dao.repository;

import com.kantboot.business.philosophy.ai.character.domain.entity.BusPhilosophyAiCharacterLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusPhilosophyAiCharacterLabelRepository extends JpaRepository<BusPhilosophyAiCharacterLabel,Long> {

    /**
     * 根据角色ID删除角色
     */
    void deleteByCharacterId(Long characterId);

}
