package com.kantboot.functional.template.web.admin.controller;

import com.kantboot.functional.template.domain.entity.FunctionalTemplate;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/functional-template-web/admin/functionalTemplate")
public class FunctionalTemplateControllerOfAdmin extends BaseAdminController<FunctionalTemplate,Long> {
}
