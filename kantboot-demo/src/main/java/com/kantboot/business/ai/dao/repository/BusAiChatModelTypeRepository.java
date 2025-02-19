package com.kantboot.business.ai.dao.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusAiChatModelTypeRepository
        extends JpaRepository<BusAiChatModelType,Long> {

    /**
     * 根据排序倒序
     */
    @Query("FROM BusAiChatModelType ORDER BY sort ASC")
    List<BusAiChatModelType> getAllByOrderBySortAsc();


}
