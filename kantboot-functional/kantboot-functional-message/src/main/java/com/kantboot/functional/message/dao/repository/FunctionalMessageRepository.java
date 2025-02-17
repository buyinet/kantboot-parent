package com.kantboot.functional.message.dao.repository;

import com.kantboot.functional.message.domain.entity.FunctionalMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalMessageRepository extends JpaRepository<FunctionalMessage, Long> {
}
