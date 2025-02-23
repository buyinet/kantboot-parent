package com.kantboot.business.philosophy.ai.language.web.admin.controller;

import com.kantboot.business.philosophy.ai.language.domain.entity.BusPhilosophyAiLanguagePreset;
import com.kantboot.util.sc.controller.BaseAdminController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus-philosophy-ai-web/languagePreset")
public class BusPhilosophyAiLanguagePresetControllerOfAdmin extends BaseAdminController<BusPhilosophyAiLanguagePreset,Long> {
}
