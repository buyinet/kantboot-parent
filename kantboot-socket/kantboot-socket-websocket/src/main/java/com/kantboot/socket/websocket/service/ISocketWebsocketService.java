package com.kantboot.socket.websocket.service;

import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;

public interface ISocketWebsocketService {

    /**
     * 创建记忆键
     */
    String createMemory();

    SocketWebsocket getByMemory(String memory);


}
