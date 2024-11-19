package com.kantboot.functional.file.web.admin.controller;

import com.kantboot.functional.file.domain.entity.FunctionalFile;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-file-web/admin/functionalFile")
public class FunctionalFileControllerOfAdmin
        extends BaseAdminController<FunctionalFile,Long> {

}
