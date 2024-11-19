package com.kantboot.functional.file.dao.repository;


import com.kantboot.functional.file.domain.entity.FunctionalFileGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FunctionalFileGroupRepository extends JpaRepository<FunctionalFileGroup, Long>
{

    /**
     * 中文: 根据文件组编码获取文件组
     * English: Get file group by file group code
     * @param code 中文：文件组编码
     *             English：file group code
     * @return 中文: 文件组
     *         English: file group
     */
    FunctionalFileGroup findByCode(String code);

    /**
     * 中文: 判断文件组是否存在
     * English: Determine whether the file group exists
     * @param code 中文: 文件组编码
     *             English: file group code
     * @return 中文: 是否存在
     *         English: whether it exists
     */
    Boolean existsByCode(String code);

}
