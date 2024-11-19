package com.kantboot.functional.file.web.controller;

import com.kantboot.functional.file.service.IFunctionalFileTransService;
import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-file-web/trans")
public class FunctionalFileTransController {

    @Resource
    private IFunctionalFileTransService service;

    @RequestMapping("exportAll")
    public ResponseEntity<FileSystemResource> exportAll() {
        return service.exportAll();
    }


}
