package com.kantboot.functional.file.dao.repository;

import com.kantboot.functional.file.domain.entity.FunctionalFileThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FunctionalFileThumbnailRepository extends JpaRepository<FunctionalFileThumbnail,Long> {

    /**
     * 根据文件id查询缩略图
     */
    List<FunctionalFileThumbnail> findByFileIdOrderByQualityAsc(Long fileId);
}
