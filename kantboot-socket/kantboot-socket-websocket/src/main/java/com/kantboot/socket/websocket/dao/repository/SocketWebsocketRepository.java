package com.kantboot.socket.websocket.dao.repository;

import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketWebsocketRepository
    extends JpaRepository<SocketWebsocket, Long> {

    /**
     * 根据memory查询
     */
    SocketWebsocket getByMemory(String memory);

}
