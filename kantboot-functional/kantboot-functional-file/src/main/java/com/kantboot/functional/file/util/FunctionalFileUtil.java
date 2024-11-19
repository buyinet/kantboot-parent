package com.kantboot.functional.file.util;

import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.util.cache.CacheUtil;
import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class FunctionalFileUtil {

    @Resource
    private CacheUtil cacheUtil;


    public String REDIS_KEY_BY_ID = "FunctionalFile:byId:";

    /**
     * 从redis中根据ID获取文件数据
     */
    public FunctionalFile getFileByIdFromRedis(Long id) {
        String jsonStr = cacheUtil.get(REDIS_KEY_BY_ID + id);
        if (jsonStr == null) {
            return null;
        }
        return JSON.parseObject(jsonStr, FunctionalFile.class);
    }

    /**
     * 根据ID存储文件数据到redis
     */
    public void setFileByIdToRedis(FunctionalFile file) {
        cacheUtil.set(REDIS_KEY_BY_ID + file.getId(), JSON.toJSONString(file));
    }

    /**
     * 根据路径、文件名、文件类型、文件大小获取ResponseEntity
     * @param path 路径
     * @param fileName 文件名
     * @param contentType 文件类型
     * @param size 文件大小
     */
    public ResponseEntity<FileSystemResource> getResponseEntityByPath(String path,
                                                                      String fileName,
                                                                      String contentType,
                                                                      Long size) {
        try{
            FileSystemResource resource = new FileSystemResource(path);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", CacheControl.maxAge(7, TimeUnit.HOURS).getHeaderValue());
            headers.add("Content-Disposition", "inline;filename=" + fileName);
            headers.add("Content-Type", contentType);
            headers.add("Content-Length", String.valueOf(size));
            headers.add("Access-Control-Allow-Origin", "*");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);
        }catch (Exception e){
            // 返回404
            return ResponseEntity.notFound().build();
        }
    }

}
