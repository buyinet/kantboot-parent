package com.kantboot.functional.file.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

public interface IFunctionalFileTransService {

    /**
     * 导出所有
     */
    ResponseEntity<FileSystemResource> exportAll();


}
