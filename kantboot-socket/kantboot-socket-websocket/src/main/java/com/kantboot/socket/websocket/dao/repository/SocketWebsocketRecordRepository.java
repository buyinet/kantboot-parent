package com.kantboot.socket.websocket.dao.repository;

import com.kantboot.socket.websocket.domain.entity.SocketWebsocketRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocketWebsocketRecordRepository
    extends JpaRepository<SocketWebsocketRecord, Long> {
}
