package com.kantboot.business.ai.dao.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusAiChatModelRepository extends JpaRepository<BusAiChatModel,Long> {

    /**
     * 根据分类ID获取模型列表
     */
    @Query("SELECT m FROM BusAiChatModel m WHERE m.typeId =?1 ORDER BY m.priority DESC")
    List<BusAiChatModel> getByTypeId(Long typeId);

    @Query("SELECT m FROM BusAiChatModel m ORDER BY m.priority DESC")
    List<BusAiChatModel> getAll();

}
