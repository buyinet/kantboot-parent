package com.kantboot.business.philosophy.ai.character.dao.repository;

import com.kantboot.business.philosophy.ai.character.domain.entity.BusPhilosophyAiCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusPhilosophyAiCharacterRepository extends JpaRepository<BusPhilosophyAiCharacter,Long> {

    /**
     * 根据分类ID获取模型列表
     */
    @Query("SELECT m FROM BusPhilosophyAiCharacter m WHERE m.typeId =?1 ORDER BY m.priority DESC")
    List<BusPhilosophyAiCharacter> getByTypeId(Long typeId);

    @Query("SELECT m FROM BusPhilosophyAiCharacter m ORDER BY m.priority DESC")
    List<BusPhilosophyAiCharacter> getAll();

}
