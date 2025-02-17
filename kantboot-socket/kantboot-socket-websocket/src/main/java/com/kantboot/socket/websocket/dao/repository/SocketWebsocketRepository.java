package com.kantboot.socket.websocket.dao.repository;

import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocketWebsocketRepository
    extends JpaRepository<SocketWebsocket, Long> {

    /**
     * 根据memory查询
     */
    SocketWebsocket getByMemory(String memory);

    /**
     * 根据userAccountId查询
     */
    List<SocketWebsocket> getByUserAccountIdAndStatusCode(Long userAccountId, String statusCode);

    /**
     * 根据statusCode查询
     */
    List<SocketWebsocket> getByStatusCode(String statusCode);

}
