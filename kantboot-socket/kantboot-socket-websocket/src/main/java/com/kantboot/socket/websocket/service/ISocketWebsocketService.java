package com.kantboot.socket.websocket.service;

import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;

import java.util.List;

public interface ISocketWebsocketService {

    /**
     * 创建记忆键
     */
    String createMemory();

    SocketWebsocket getByMemory(String memory);

    /**
     * 根据用户账号ID获取
     */
    List<SocketWebsocket> getByUserAccountId(Long userAccountId);

    /**
     * 添加
     */
    SocketWebsocket update(String memory,String statusCode);

    /**
     * 心跳
     */
    void heartbeat(String memory);


}
