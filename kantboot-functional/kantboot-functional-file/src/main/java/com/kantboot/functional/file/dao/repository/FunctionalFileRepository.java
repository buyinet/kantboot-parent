package com.kantboot.functional.file.dao.repository;

import com.kantboot.functional.file.domain.dto.FunctionalFileSearchDTO;
import com.kantboot.functional.file.domain.entity.FunctionalFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FunctionalFileRepository extends JpaRepository<FunctionalFile, Long> {

    /**
     * 根据md5和文件组编码获取文件
     *
     * @param md5       文件md5
     * @param groupCode 文件组编码
     * @return 文件
     */
    List<FunctionalFile> findByMd5AndGroupCode(String md5, String groupCode);

    /**
     * 根据md5和文件组编码获取第一个
     *
     * @param md5       文件md5
     * @param groupCode 文件组编码
     * @return 文件
     */
    FunctionalFile findFirstByMd5AndGroupCode(String md5, String groupCode);

    /**
     * 根据分组编码和编码获取文件
     *
     * @param groupCode 文件组编码
     * @param code      文件编码
     * @return 文件
     */
    FunctionalFile findByGroupCodeAndCode(String groupCode, String code);

    /**
     * 获取某个分组下的文件
     *
     * @param groupCode 文件组编码
     * @return 文件
     */
    List<FunctionalFile> findByGroupCode(String groupCode);

    @Query("""
            FROM FunctionalFile AS p
            WHERE
            (
            (:#{#param.id} IS NULL OR p.id = :#{#param.id})
            AND :#{#param.groupCode} IS NULL OR p.groupCode = :#{#param.groupCode})
            AND (:#{#param.code} IS NULL OR p.code = :#{#param.code})
            AND (:#{#param.name} IS NULL OR p.name LIKE CONCAT('%',:#{#param.name},'%'))
            AND (:#{#param.md5} IS NULL OR p.md5 = :#{#param.md5})
            AND (:#{#param.sizeMin} IS NULL OR p.size >= :#{#param.sizeMin})
            AND (:#{#param.sizeMax} IS NULL OR p.size <= :#{#param.sizeMax})
            AND (:#{#param.type} IS NULL OR p.type = :#{#param.type})
            AND (:#{#param.gmtCreateStart} IS NULL OR p.gmtCreate >= :#{#param.gmtCreateStart})
            AND (:#{#param.gmtCreateEnd} IS NULL OR p.gmtCreate <= :#{#param.gmtCreateEnd})
            AND (:#{#param.gmtModifiedStart} IS NULL OR p.gmtModified >= :#{#param.gmtModifiedStart})
            AND (:#{#param.gmtModifiedEnd} IS NULL OR p.gmtModified <= :#{#param.gmtModifiedEnd})
            ORDER BY p.gmtModified DESC
            """)
    Page<FunctionalFile> getBodyData(
            @Param("param") FunctionalFileSearchDTO param,
            Pageable pageable);


}
