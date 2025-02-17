package com.kantboot.socket.websocket.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.socket.websocket.constants.WebsocketStatusCodeConstants;
import com.kantboot.socket.websocket.dao.repository.SocketWebsocketRecordRepository;
import com.kantboot.socket.websocket.dao.repository.SocketWebsocketRepository;
import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;
import com.kantboot.socket.websocket.domain.entity.SocketWebsocketRecord;
import com.kantboot.socket.websocket.service.ISocketWebsocketService;
import com.kantboot.user.account.service.IUserAccountService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SocketWebsocketServiceImpl implements ISocketWebsocketService {

    @Resource
    private SocketWebsocketRepository repository;

    @Resource
    private SocketWebsocketRecordRepository recordRepository;

    @Resource
    private IUserAccountService userAccountService;

    @Transactional
    @Override
    public String createMemory() {
        Long selfId = userAccountService.getSelfId();
        // 生成UUID
        String memory = UUID.randomUUID().toString().replaceAll("-", "")
                +"."+selfId+"."+System.currentTimeMillis();

        SocketWebsocket socketWebsocket = new SocketWebsocket();
        socketWebsocket.setUserAccountId(selfId);
        socketWebsocket.setStatusCode(WebsocketStatusCodeConstants.WAITING);
        socketWebsocket.setMemory(memory);
        SocketWebsocketRecord record = BeanUtil.copyProperties(socketWebsocket, SocketWebsocketRecord.class);
        record.setId(null);
        record.setWebsocketId(socketWebsocket.getId());
        repository.save(socketWebsocket);
        recordRepository.save(record);
        return memory;
    }

    @Override
    public SocketWebsocket getByMemory(String memory) {
        return repository.getByMemory(memory);
    }

    @Override
    public List<SocketWebsocket> getByUserAccountId(Long userAccountId) {
        return repository.getByUserAccountIdAndStatusCode(userAccountId,WebsocketStatusCodeConstants.CONNECTING);
    }

    @Override
    public SocketWebsocket update(String memory, String statusCode) {
        SocketWebsocket byMemory = repository.getByMemory(memory);
        byMemory.setStatusCode(statusCode);
        SocketWebsocketRecord record = BeanUtil.copyProperties(byMemory, SocketWebsocketRecord.class);
        record.setId(null);
        record.setWebsocketId(byMemory.getId());
        recordRepository.save(record);
        return repository.save(byMemory);
    }

    @Override
    public void heartbeat(String memory) {
        SocketWebsocket byMemory = repository.getByMemory(memory);
        byMemory.setGmtLastHeartbeat(new Date());
        repository.save(byMemory);
    }

}
