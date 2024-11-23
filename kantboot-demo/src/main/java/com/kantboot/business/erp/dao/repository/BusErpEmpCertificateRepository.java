package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpEmpCertificate;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BusErpEmpCertificateRepository
        extends JpaRepository<BusErpEmpCertificate, Long> {

    /**
     * 根据empId删除员工证书
     * Delete employee certificate by empId
     *
     * @param empId 员工ID
     *              Employee ID
     */
    @Query("""
    DELETE FROM BusErpEmpCertificate WHERE empId = :empId
    """)
    @Transactional
    @Modifying
    void deleteByEmpId(Long empId);

}
