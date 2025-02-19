package com.kantboot.business.ai.repository;

import com.kantboot.business.ai.domain.entity.BusAiChatDialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusAiChatRepository extends JpaRepository<BusAiChatDialog,Long> {
}
