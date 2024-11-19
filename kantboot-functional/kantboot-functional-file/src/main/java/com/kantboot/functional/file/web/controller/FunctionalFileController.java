package com.kantboot.functional.file.web.controller;

import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.functional.file.service.IFunctionalFileService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件
 * 用于管理文件的上传、下载、删除等
 */
@RestController
@RequestMapping("/functional-file-web/file")
public class FunctionalFileController {

    @Resource
    private IFunctionalFileService fileService;

    /**
     * 上传文件
     * @param file 文件
     * @param groupCode 文件组编码
     * @param code 文件编码
     * @return 文件
     */
    @RequestMapping("/upload")
    public RestResult<FunctionalFile> upload(MultipartFile file, String groupCode, String code) {
        System.err.println(file.getSize());
        return RestResult.success(fileService.upload(file, groupCode,code), CommonSuccessStateCodeAndMsg.UPLOAD_SUCCESS);
    }

    /**
     * 根据文件id查询缩略图
     * @param fileId 文件id
     * @return 缩略图
     */
    @RequestMapping("/getThumbnailListByFileId")
    public RestResult<Object> getThumbnailListByFileId(
            @RequestParam("fileId") Long fileId){
        return RestResult.success(fileService.getThumbnailListByFileId(fileId),CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 根据文件组编码和编码查询缩略图
     */
    @RequestMapping("/getThumbnailListByGroupCodeAndCode")
    public RestResult<Object> getThumbnailListByGroupCodeAndCode(
            @RequestParam("fileGroupCode") String fileGroupCode,
            @RequestParam("fileCode") String fileCode){
        return RestResult.success(fileService.getThumbnailListByGroupCodeAndCode(fileGroupCode, fileCode),CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

    /**
     * 根据分组编码获取文件集合
     * @param groupCode 文件组编码
     * @return 文件列表
     */
    @RequestMapping("/getListByGroupCode")
    public RestResult<Object> getListByGroupCode(
            @RequestParam("groupCode") String groupCode){
        return RestResult.success(fileService.getListByGroupCode(groupCode),CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }


    /**
     * 访问文件
     * @param id 文件id
     * @return 文件
     */
    @RequestMapping("/visit/{id}")
    public ResponseEntity<FileSystemResource> visit(
            @PathVariable Long id){
        return fileService.visit(id);
    }

    /**
     * 根据分组编码和编码访问文件
     * @param groupCode 分组编码
     * @param code 编码
     * @return 文件
     */
    @RequestMapping("/visitByGroupCodeAndCode/{groupCode}/{code}")
    public ResponseEntity<FileSystemResource> visitByGroupCodeAndCode(
            @PathVariable String groupCode,
            @PathVariable String code){
        return fileService.visitByGroupCodeAndCode(groupCode, code);
    }

    /**
     * 访问缩略图
     * @param thumbnailId 缩略图id
     * @return 缩略图
     */
    @RequestMapping("/visitThumbnail/{thumbnailId}")
    public ResponseEntity<FileSystemResource> visitThumbnail(
            @PathVariable Long thumbnailId){
        return fileService.visitThumbnail(thumbnailId);
    }



}
