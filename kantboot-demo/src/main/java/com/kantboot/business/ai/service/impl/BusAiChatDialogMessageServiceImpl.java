package com.kantboot.business.ai.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.business.ai.dao.repository.BusAiChatDialogMessageRepository;
import com.kantboot.business.ai.domain.dto.DialogMessageSearchDTO;
import com.kantboot.business.ai.domain.entity.BusAiChatDialogMessage;
import com.kantboot.business.ai.service.IBusAiChatDialogMessageService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusAiChatDialogMessageServiceImpl implements IBusAiChatDialogMessageService {

    @Resource
    private BusAiChatDialogMessageRepository repository;

    @Override
    public List<BusAiChatDialogMessage> getList(DialogMessageSearchDTO param) {
        Long bodyDataCount = repository.getBodyDataCount(param);
        // 每页10条数据
        int pageSize = 10;
        // 计算总页数
        int totalPages = (int) Math.ceil((double) bodyDataCount / pageSize);
        if (totalPages < 1) {
            return List.of();
        }
        // 获取最后一页
        Pageable pageable = PageRequest.of(totalPages-1, pageSize);
        Page<BusAiChatDialogMessage> bodyData = repository.getBodyData(param, pageable);
        List<BusAiChatDialogMessage> list = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialogMessage.class);
        // 查看有几条数据，如果小于10条，就再加上上一页
        if (list.size() < pageSize && totalPages > 1
                && param.getMaxId() == null
                && param.getMinId() == null
        ) {
            pageable = PageRequest.of(totalPages-2, pageSize);
            bodyData = repository.getBodyData(param, pageable);
            List<BusAiChatDialogMessage> list2 = BeanUtil.copyToList(bodyData.getContent(), BusAiChatDialogMessage.class);
            list2.addAll(list);
            return list2;
        }
        return list;
    }
}
