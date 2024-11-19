package com.kantboot.functional.file.service;

import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.functional.file.domain.entity.FunctionalFileThumbnail;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IFunctionalFileService {

    /**
     * 根据id查询文件
     * @param id 文件id
     * @return 文件
     */
    FunctionalFile getById(Long id);

    /**
     * 上传文件(需要指定文件组编码)
     * @param file 文件
     * @param groupCode 文件组编码
     * @param code 文件编码
     * @return 文件
     */
    FunctionalFile upload(MultipartFile file, String groupCode, String code);

    /**
     * 上传文件缩略图
     * @param file 文件
     */
    void uploadThumbnail(FunctionalFile file);

    /**
     * 根据文件id获取缩略图
     * @param fileId 文件id
     */
    List<FunctionalFileThumbnail> getThumbnailListByFileId(Long fileId);

    /**
     * 根据文件组编码和编码获取缩略图
     * @param groupCode 文件组编码
     */
    List<FunctionalFileThumbnail> getThumbnailListByGroupCodeAndCode(String groupCode, String code);


    /**
     * 根据文件组编码获取文件列表
     * @param groupCode 文件组编码
     */
    List<FunctionalFile> getListByGroupCode(String groupCode);

    /**
     * 查看文件
     * @param id 文件id
     * @return 文件
     */
    ResponseEntity<FileSystemResource> visit(Long id);

    /**
     * 根据分组编码和编码访问文件
     * @param groupCode 文件组编码
     */
    ResponseEntity<FileSystemResource> visitByGroupCodeAndCode(String groupCode, String code);

    /**
     * 查看文件缩略
     * @param thumbnailId 缩略图id
     */
    ResponseEntity<FileSystemResource> visitThumbnail(Long thumbnailId);


}
