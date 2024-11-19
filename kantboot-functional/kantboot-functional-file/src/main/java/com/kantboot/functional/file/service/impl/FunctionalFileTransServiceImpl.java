package com.kantboot.functional.file.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.file.dao.repository.FunctionalFileRepository;
import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.functional.file.service.IFunctionalFileTransService;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FunctionalFileTransServiceImpl implements IFunctionalFileTransService {

    @Resource
    private FunctionalFileRepository functionalFileRepository;


    @SneakyThrows
    @Override
    public ResponseEntity<FileSystemResource> exportAll() {
        // TODO 还有缩略图没有考虑进去，之后补充

        List<FunctionalFile> all = functionalFileRepository.findAll();
        // 获取当前类的保护域
        ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();
        // 获取当前类的代码源
        CodeSource codeSource = protectionDomain.getCodeSource();
        // 获取代码源的路径
        URL location = codeSource.getLocation();
        // jar包的路径
        String jarPath = location.getPath();
        // 文件夹名称
        String uuid = IdUtil.simpleUUID();
        // 路径
        String path = jarPath + uuid;
        // 判断是否时widows系统
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            // 如果开头是 / 开头，则去掉
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        }
        for (FunctionalFile functionalFile : all){
            String groupPath = path + "/file/" + functionalFile.getGroupCode();


            // 每个分组创建一个文件夹
            File filePath = new File(groupPath);
            // 判断文件夹是否存在
            if (!filePath.exists()) {
                // 创建文件夹
                boolean mkdirs = filePath.mkdirs();
                if (!mkdirs) {
                    // 提示文件组异常
                    throw new RuntimeException("文件组异常");
                }
            }
            String filePath1 = functionalFile.getGroup().getPath() + "/" + functionalFile.getName();
            // 将对应文件保存进对应文件夹
            FileUtil.copyFile(filePath1, groupPath + "/" + functionalFile.getName());
        }

        // 将所有文件信息转换为JSON字符串
        String allJson = JSON.toJSONString(all);
        // 存入文件
        File file = new File(path+"/file.json");
        // 创建文件
        FileUtil.touch(file);
        try {
            FileUtil.writeString(allJson, file, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//         将文件夹存成压缩包
        FileOutputStream fos = new FileOutputStream(jarPath+"/"+"file_getAll_"+uuid+".kantar");
        ZipOutputStream zos = new ZipOutputStream(fos);
        Path srcDir = Paths.get(path);

        Files.walk(srcDir)
                .filter(path1 -> !Files.isDirectory(path1))
                .forEach(filePath -> {
                    ZipEntry zipEntry = new ZipEntry(srcDir.relativize(filePath).toString());
                    try {
                        zos.putNextEntry(zipEntry);
                        Files.copy(filePath, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        zos.close();
        fos.close();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + "file_getAll_"+uuid+".kantar")
                .body(new FileSystemResource(new File(jarPath+"/"+"file_getAll_"+uuid+".kantar")));

    }


}
