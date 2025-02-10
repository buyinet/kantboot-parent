package com.kantboot.functional.sms.repository;

import com.kantboot.functional.sms.domain.entity.FunctionalSms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalSmsRepository extends JpaRepository<FunctionalSms, Long>{
}
