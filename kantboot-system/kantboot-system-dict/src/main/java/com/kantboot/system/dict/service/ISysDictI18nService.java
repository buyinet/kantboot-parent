package com.kantboot.system.dict.service;

import com.kantboot.system.dict.domain.dto.ToGobleBatchDTO;
import com.kantboot.system.dict.domain.dto.ToGobleBatchTextDTO;
import com.kantboot.system.dict.domain.dto.ToGobleDTO;
import com.kantboot.system.dict.domain.entity.SysDictI18n;

import java.util.List;

/**
 * 字典国际化服务接口
 */
public interface ISysDictI18nService {

    /**
     * 根据语言和分组编码查询国际化字典
     */
    List<SysDictI18n> getDict(String languageCode, String dictGroupCode);

}
