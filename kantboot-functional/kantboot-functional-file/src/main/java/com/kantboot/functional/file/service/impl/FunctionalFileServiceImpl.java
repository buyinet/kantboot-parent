package com.kantboot.functional.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.file.dao.repository.FunctionalFileGroupRepository;
import com.kantboot.functional.file.dao.repository.FunctionalFileRecordRepository;
import com.kantboot.functional.file.dao.repository.FunctionalFileRepository;
import com.kantboot.functional.file.dao.repository.FunctionalFileThumbnailRepository;
import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.functional.file.domain.entity.FunctionalFileGroup;
import com.kantboot.functional.file.domain.entity.FunctionalFileRecord;
import com.kantboot.functional.file.domain.entity.FunctionalFileThumbnail;
import com.kantboot.functional.file.exception.FunctionalFileException;
import com.kantboot.functional.file.service.IFunctionalFileService;
import com.kantboot.functional.file.util.FileUtil;
import com.kantboot.functional.file.util.FunctionalFileUtil;
import com.kantboot.user.account.service.IUserAccountService;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FunctionalFileServiceImpl implements IFunctionalFileService {

    @Resource
    private FunctionalFileRepository repository;

    @Resource
    private FunctionalFileGroupRepository functionalFileGroupRepository;

    @Resource
    private FunctionalFileThumbnailRepository thumbnailRepository;

    @Resource
    private FunctionalFileRecordRepository fileRecordRepository;

    @Resource
    private FunctionalFileUtil functionalFileUtil;

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Resource
    private IUserAccountService userAccountService;

    @Resource
    private CacheUtil cacheUtil;

    /**
     * 根据id查询文件
     *
     * @param id 文件id
     * @return 文件
     */
    @Override
    public FunctionalFile getById(Long id) {
        // 从redis中获取
        FunctionalFile fileByIdFromRedis = functionalFileUtil.getFileByIdFromRedis(id);
        if (fileByIdFromRedis != null) {
            return fileByIdFromRedis;
        }
        // 从数据库中获取
        FunctionalFile file = repository.findById(id).orElse(null);
        if (file != null) {
            // 存入redis
            functionalFileUtil.setFileByIdToRedis(file);
            return file;
        }
        // 提示文件不存在
        throw FunctionalFileException.FILE_NOT_EXIST;
    }

    /**
     * 获取用户id
     */
    private Long getUserIdOfUpload() {
        try {
            Long selfId = userAccountService.getSelfId();
            if (selfId != null) {
                return selfId;
            }
        } catch (NullPointerException e) {
            log.error("获取用户ID失败: {}", e.getMessage());
        } catch (BaseException e) {
            log.error("获取用户ID失败: {}", e.getMessage());
        }
        return null;
    }


    /**
     * 上传文件(需要指定文件组编码)
     *
     * @param file      文件
     * @param groupCode 文件组编码
     * @param code      文件编码
     * @return 文件
     */
    @SneakyThrows
    @Override
    public FunctionalFile upload(MultipartFile file, String groupCode, String code) {
        // 判断文件组是否存在
        if (!functionalFileGroupRepository.existsByCode(groupCode)) {
            log.info("文件组不存在: {}", groupCode);
            // 提示文件组不存在
            throw FunctionalFileException.FILE_GROUP_NOT_EXIST;
        }

        // 获取文件MD5
        String md5 = DigestUtils.md5DigestAsHex(file.getBytes());
        // 获取上传时文件名
        String originalName = file.getOriginalFilename();

        // 如果文件后缀是m4a，则修改为mp3
        // 因为.m4a文件会在很多操作系统上，使用后直接删除
        if (originalName != null && originalName.endsWith(".m4a")) {
            originalName = originalName.replace(".m4a", ".mp3");
        }

        // 上传文件
        FunctionalFileRecord fileRecord = new FunctionalFileRecord();
        // 设置上传者id
        fileRecord.setUserIdOfUpload(getUserIdOfUpload());
        // 设置上传时ip
        fileRecord.setIpOfUpload(httpRequestHeaderUtil.getIp());
        // 设置文件md5
        fileRecord.setMd5(md5);
        // 设置文件组
        fileRecord.setGroupCode(groupCode);
        // 设置文件原始名
        fileRecord.setOriginalName(originalName);
        // 设置文件编码
        fileRecord.setCode(code);
        // 设置文件大小
        fileRecord.setSize(file.getSize());
        // 设置文件类型
        if (originalName != null) {
            fileRecord.setType(FileUtil.getSuffix(originalName));
        }
        // 设置文件名
        fileRecord.setName(fileRecord.getMd5() + "." + fileRecord.getType());
        // 设置文件ContentType
        fileRecord.setContentType(FileUtil.getContentType(originalName));
        // 设置文件code
        fileRecord.setCode(code);
        // 保存文件记录
        fileRecordRepository.save(fileRecord);

        // 通过md5和文件大小判断文件是否已经存在
        List<FunctionalFile> byMd5AndGroupCode = repository.findByMd5AndGroupCode(md5, groupCode);
        // 如果文件已存在
        if (byMd5AndGroupCode != null && !byMd5AndGroupCode.isEmpty()) {
            log.info("文件已存在相同的MD5值：{}", byMd5AndGroupCode.get(0).getName());
            return byMd5AndGroupCode.get(0);
        }

        FunctionalFile fileEntity = BeanUtil.copyProperties(fileRecord, FunctionalFile.class);
        // 设置文件id为空，防止保存时覆盖已存在的文件
        fileEntity.setId(null);
        FunctionalFile result = repository.save(fileEntity);

        // 获取文件组
        FunctionalFileGroup byCode = functionalFileGroupRepository.findByCode(groupCode);
        // 获取文件组路径
        String path = byCode.getPath();
        File filePath = new File(path);
        // 如果文件夹不存在则创建
        if (!filePath.exists()) {
            boolean mkdirs = filePath.mkdirs();
            if (!mkdirs) {
                // 提示文件组异常
                log.error("文件组异常: {}", groupCode);
                throw BaseException.of("fileGroupError", "文件组异常");
            }
        }

        // 保存文件
        file.transferTo(new File(path + "/" + result.getName()));
        // 判断是否需要缩略
        if (byCode.getIsHasThumbnail()) {
            uploadThumbnail(result);
        }

        return result;
    }

    @Async
    @Modifying
    @Override
    @SneakyThrows
    public void uploadThumbnail(FunctionalFile file) {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        List<String> md5List = new ArrayList<>();
        List<FunctionalFileThumbnail> thumbnails = new ArrayList<>();
        // 获取文件的分组
        FunctionalFileGroup group = functionalFileGroupRepository.findByCode(file.getGroupCode());

        // 获取文件的路径
        String path = group.getPath() + "/" + file.getName();
        // 获取缩略图的路径
        String thumbnailPath = group.getThumbnailPath();
        // 如果文件夹不存在则创建
        File thumbnailPathFile = new File(thumbnailPath);
        if (!thumbnailPathFile.exists()) {
            boolean mkdirs = thumbnailPathFile.mkdirs();
            if (!mkdirs) {
                throw BaseException.of("thumbnailPathError", "缩略图路径异常");
            }
        }

        for (int i = 1; i < 10; i++) {
            // 计算缩略图质量
            BigDecimal divide = new BigDecimal(i).divide(new BigDecimal(20));
            Float quality = divide.floatValue();
            MultipartFile compressImage = FileUtil.compressImage(path, divide.doubleValue());
            String md5 = DigestUtils.md5DigestAsHex(compressImage.getBytes());
            boolean isAdd = true;
            for (String oldMd5 : md5List) {
                if (oldMd5.equals(md5)) {
                    isAdd = false;
                    break;
                }
            }
            if (isAdd) {
                multipartFileList.add(compressImage);
                md5List.add(md5);
                FunctionalFileThumbnail functionalFileThumbnailIn = new FunctionalFileThumbnail()
                        .setFileId(file.getId())
                        .setQuality(quality)
                        .setGroupCode(file.getGroupCode())
                        .setMd5(md5)
                        .setName(md5 + "." + file.getType())
                        .setSize(compressImage.getSize())
                        .setType(file.getType())
                        .setContentType(file.getContentType());
                thumbnails.add(functionalFileThumbnailIn);
                compressImage.transferTo(new File(thumbnailPath + "/" + functionalFileThumbnailIn.getName()));
            }
        }

        thumbnailRepository.saveAll(thumbnails);
    }

    @Override
    public List<FunctionalFileThumbnail> getThumbnailListByFileId(Long fileId) {
        String redisKey = "fileId:" + fileId + ":fileThumbnailList";
        if (cacheUtil.hasKey(redisKey)) {
            return JSON.parseArray(cacheUtil.get(redisKey), FunctionalFileThumbnail.class);
        }

        List<FunctionalFileThumbnail> byFileIdOrderByQualityAsc = thumbnailRepository.findByFileIdOrderByQualityAsc(fileId);

        if (byFileIdOrderByQualityAsc != null && !byFileIdOrderByQualityAsc.isEmpty()) {
            cacheUtil.setEx(redisKey, JSON.toJSONString(byFileIdOrderByQualityAsc), 7 * 24 * 60 * 1000);
        }
        return byFileIdOrderByQualityAsc;
    }

    @Override
    public List<FunctionalFileThumbnail> getThumbnailListByGroupCodeAndCode(String groupCode, String code) {
        String redisKey = "file::groupCode:" + groupCode + ":code:" + code + ":fileThumbnailList";
        if (cacheUtil.hasKey(redisKey)) {
            return JSON.parseArray(cacheUtil.get(redisKey), FunctionalFileThumbnail.class);
        }

        FunctionalFile byGroupCodeAndCode = repository.findByGroupCodeAndCode(groupCode, code);
        log.info("byGroupCodeAndCode: {}", JSON.toJSONString(byGroupCodeAndCode));
        if (byGroupCodeAndCode != null) {
            List<FunctionalFileThumbnail> thumbnailListByFileId = getThumbnailListByFileId(byGroupCodeAndCode.getId());
            cacheUtil.setEx(redisKey, JSON.toJSONString(thumbnailListByFileId), 7 * 24 * 60 * 1000);
            return thumbnailListByFileId;
        }


        return new ArrayList<>();
    }

    @Override
    public List<FunctionalFile> getListByGroupCode(String groupCode) {
        return repository.findByGroupCode(groupCode);
    }

    @Override
    public ResponseEntity<FileSystemResource> visit(Long id) {
        FunctionalFile byId = getById(id);
        String path = byId.getGroup().getPath() + "/" + byId.getName();

        // 根據路径、文件名、文件类型、文件大小返回ResponseEntity
        return functionalFileUtil.getResponseEntityByPath(path,
                byId.getName(),
                byId.getContentType(),
                byId.getSize());
    }

    @Override
    public ResponseEntity<FileSystemResource> visitThumbnail(Long thumbnailId) {
        FunctionalFileThumbnail byId = thumbnailRepository.findById(thumbnailId).get();
        String path = byId.getGroup().getThumbnailPath() + "/" + byId.getName();

        // 根据路径、文件名、文件类型、文件大小返回ResponseEntity
        return functionalFileUtil.getResponseEntityByPath(path,
                byId.getName(),
                byId.getContentType(),
                byId.getSize());
    }

    @Override
    public ResponseEntity<FileSystemResource> visitByGroupCodeAndCode(String groupCode, String code) {
        FunctionalFile byGroupCodeAndCode = repository.findByGroupCodeAndCode(groupCode, code);
        FileSystemResource resource = new FileSystemResource(byGroupCodeAndCode.getGroup().getPath() + "/" + byGroupCodeAndCode.getName());

        // 根据路径、文件名、文件类型、文件大小返回ResponseEntity
        return functionalFileUtil.getResponseEntityByPath(resource.getFile().getPath(),
                byGroupCodeAndCode.getName(),
                byGroupCodeAndCode.getContentType(),
                byGroupCodeAndCode.getSize());

    }

}
