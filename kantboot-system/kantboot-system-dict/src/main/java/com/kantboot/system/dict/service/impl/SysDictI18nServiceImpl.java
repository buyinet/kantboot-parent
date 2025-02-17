package com.kantboot.system.dict.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.kantboot.system.dict.dao.repository.SysDictGroupRepository;
import com.kantboot.system.dict.dao.repository.SysDictI18nRepository;
import com.kantboot.system.dict.dao.repository.SysDictRepository;
import com.kantboot.system.dict.domain.dto.ToGobleBatchDTO;
import com.kantboot.system.dict.domain.dto.ToGobleBatchTextDTO;
import com.kantboot.system.dict.domain.dto.ToGobleDTO;
import com.kantboot.system.dict.domain.entity.SysDict;
import com.kantboot.system.dict.domain.entity.SysDictGroup;
import com.kantboot.system.dict.domain.entity.SysDictI18n;
import com.kantboot.system.dict.domain.entity.SysLanguage;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.system.dict.service.ISysLanguageService;
import com.kantboot.system.dict.slot.SysDictI18nSlot;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysDictI18nServiceImpl implements ISysDictI18nService {

    @Resource
    private SysDictI18nRepository dictI18nRepository;

    @Resource
    private SysDictI18nSlot slot;

    @Resource
    private ISysLanguageService languageService;

    @Resource
    private SysDictRepository dictRepository;

    @Resource
    private SysDictGroupRepository dictGroupRepository;

    @Override
    public List<SysDictI18n> getDictList(String languageCode, String dictGroupCode) {
        // 从数据库中获取
        return dictI18nRepository.findByDictGroupCodeAndLanguageCode(dictGroupCode, languageCode);
    }

    @Override
    public String getDictText(String languageCode, String dictGroupCode, String dictCode) {
        List<String> value = dictI18nRepository.findValue(dictCode, dictGroupCode, languageCode);
        if (0==value.size()) {
            return null;
        }
        return value.get(0);
    }

    @Override
    public String translateText(String sourceLanguageCode, String targetLanguageCode, String text) {
        log.info("sourceLanguageCode = {}, targetLanguageCode = {}, text = {}", sourceLanguageCode, targetLanguageCode, text);
        return slot.translateText(sourceLanguageCode, targetLanguageCode, text);
    }

    @Override
    public List<SysDictI18n> toGoble(String text, String sourceLanguageCode, String dictGroupCode, String dictCode) {

        List<SysDictI18n> i18nList = new ArrayList<>();

        // 去除首尾空格
        text = text.trim();
        // 查询数量
        Long l = dictI18nRepository.countByLanguageCodeAndDictGroupCodeAndValue(sourceLanguageCode, dictGroupCode, text);
        if(l>0){
            // 如果存在，就不再翻译
            log.info("Has internationalized words: {}", text);
            return null;
        }

        // 获取翻译的文本
        String translateTextToEn = translateText(
                sourceLanguageCode,
                "en",
                text
        );

        // 如果dictCode为空，就用翻译的文本，并取出特殊符号、空格改驼峰式
        if(dictCode == null || dictCode.isEmpty()){
            // 去除特殊符号，除空格以外
            dictCode = translateTextToEn.replaceAll("[^a-zA-Z0-9 ]", "");
            String[] dictCodeSplit = dictCode.split(" ");
            StringBuilder sb = new StringBuilder();
            for (String s : dictCodeSplit) {
                sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            }
            dictCode = sb.toString();
            // 首字母改成小写
            dictCode = dictCode.substring(0, 1).toLowerCase() + dictCode.substring(1);
        }

        // 保存到国际化字典
        SysDictI18n dictI18nEn = new SysDictI18n();
        dictI18nEn.setLanguageCode("en");
        dictI18nEn.setDictCode(dictCode);
        dictI18nEn.setDictGroupCode(dictGroupCode);
        dictI18nEn.setValue(translateTextToEn);
        if(!sourceLanguageCode.equals("en")){
            dictI18nRepository.insert(dictI18nEn);
        }
        i18nList.add(dictI18nEn);
        log.info("dictI18nEn = {}", JSON.toJSONString(dictI18nEn));

        // 将源语言的文本保存到国际化字典
        SysDictI18n dictI18nSource = new SysDictI18n();
        dictI18nSource.setLanguageCode(sourceLanguageCode);
        dictI18nSource.setDictCode(dictCode);
        dictI18nSource.setDictGroupCode(dictGroupCode);
        dictI18nSource.setValue(text);
        dictI18nRepository.insert(dictI18nSource);
        i18nList.add(dictI18nSource);
        log.info("dictI18nSource = {}", JSON.toJSONString(dictI18nSource));

        // 查看字典中是否存在
        SysDict byGroupCodeAndCode = dictRepository.findByGroupCodeAndCode(dictGroupCode, dictCode);
        if(byGroupCodeAndCode == null){
            SysDict sysDict = new SysDict();
            sysDict.setGroupCode(dictGroupCode);
            sysDict.setCode(dictCode);
            sysDict.setValue(text);
            sysDict.setValueEn(translateTextToEn);
            dictRepository.save(sysDict);
            // 添加到字典中
            log.info("Added to the dictionary: {}", JSON.toJSONString(sysDict));
        }

        // 查看字典组中是否存在
        // Check if the dictionary group exists
        SysDictGroup byCode = dictGroupRepository.findByCode(dictGroupCode);
        if(byCode == null){
            SysDictGroup sysDictGroup = new SysDictGroup();
            sysDictGroup.setCode(dictGroupCode);
            sysDictGroup.setName(dictGroupCode);
            dictGroupRepository.save(sysDictGroup);
            log.info("Added to the dictionary group: {}", JSON.toJSONString(sysDictGroup));
        }


        List<SysLanguage> bySupport = languageService.getBySupport();

        for (SysLanguage sysLanguage : bySupport) {
            // 目标语言编码
            String targetLanguageCode = sysLanguage.getCode();

            // 如果目标语言不是英文，且不是源语言
            if (!targetLanguageCode.equals("en") && !targetLanguageCode.equals(sourceLanguageCode)) {

                String targetLanguageCode2 = targetLanguageCode;
                if(targetLanguageCode.equals("zh_MO")){
                    targetLanguageCode2 = "zh_HK";
                }
                // 获取翻译的文本
                String translateText = translateText(
                        sourceLanguageCode,
                        targetLanguageCode2,
                        text
                );

                // 保存到国际化字典
                SysDictI18n dictI18n = new SysDictI18n();
                dictI18n.setLanguageCode(targetLanguageCode);
                dictI18n.setDictCode(dictCode);
                dictI18n.setDictGroupCode(dictGroupCode);
                dictI18n.setValue(translateText);
                dictI18nRepository.insert(dictI18n);
                i18nList.add(dictI18n);
                System.out.println("dictI18n = " + JSON.toJSONString(dictI18n));
            }

        }

        return i18nList;
    }

    @Override
    public void toGobleBatch(ToGobleBatchDTO toGobleBatchDTO) {
        List<ToGobleDTO> params = toGobleBatchDTO.getParams();
        for (ToGobleDTO param : params) {
            toGoble(param.getText(), toGobleBatchDTO.getSourceLanguageCode(), param.getDictGroupCode(), param.getDictCode());
        }
    }

    @SneakyThrows
    @Override
    public void toGlobalBatchText(ToGobleBatchTextDTO toGobleBatchTextDTO) {
        for (String text : toGobleBatchTextDTO.getTexts()) {
            Thread.sleep(100);
            ThreadUtil.execute(()->{
                toGoble(text, toGobleBatchTextDTO.getSourceLanguageCode(), toGobleBatchTextDTO.getDictGroupCode(), null);
            });
        }
    }

    @Override
    public String getTargetLanguageText(ToGobleDTO dto) {
        String value = getDictText(dto.getDictCode(), dto.getDictGroupCode(), dto.getTargetLanguageCode());
        if(value != null){
            return dto.getText();
        }
        value = translateText(dto.getSourceLanguageCode(), dto.getTargetLanguageCode(), dto.getText());
        // 保存到国际化字典
        SysDictI18n dictI18n = new SysDictI18n();
        dictI18n.setLanguageCode(dto.getTargetLanguageCode());
        dictI18n.setDictCode(dto.getDictCode());
        dictI18n.setDictGroupCode(dto.getDictGroupCode());
        dictI18n.setValue(value);
        dictI18nRepository.insert(dictI18n);
        return value;
    }
}
