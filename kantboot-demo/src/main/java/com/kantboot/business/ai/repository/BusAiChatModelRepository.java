package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusAiChatModelRepository extends JpaRepository<BusAiChatModel,Long> {

    /**
     * 根据分类ID获取模型列表
     */
    List<BusAiChatModel> getByTypeId(Long typeId);

}
