package com.kantboot.business.philosophy.ai.character.dao.repository;

import com.kantboot.business.philosophy.ai.character.domain.entity.BusPhilosophyAiCharacterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusPhilosophyAiCharacterTypeRepository
        extends JpaRepository<BusPhilosophyAiCharacterType,Long> {

    /**
     * 根据排序倒序
     */
    @Query("FROM BusPhilosophyAiCharacterType ORDER BY sort ASC")
    List<BusPhilosophyAiCharacterType> getAllByOrderBySortAsc();


}
