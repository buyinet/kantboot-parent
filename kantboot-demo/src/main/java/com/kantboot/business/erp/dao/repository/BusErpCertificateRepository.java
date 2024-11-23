package com.kantboot.business.erp.dao.repository;

import com.kantboot.business.erp.domain.entity.BusErpCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusErpCertificateRepository
    extends JpaRepository<BusErpCertificate, Long> {

    /**
     * 根据企业ID获取证书列表
     * Get the certificate list according to the enterprise ID
     */
    List<BusErpCertificate> getByEnterpriseId(Long enterpriseId);

}
