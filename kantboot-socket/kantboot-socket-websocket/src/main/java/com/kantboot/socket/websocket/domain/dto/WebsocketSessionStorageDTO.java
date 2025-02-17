package com.kantboot.socket.websocket.domain.dto;

import jakarta.websocket.Session;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * websocket存储对象
 */
@Data
@Accessors(chain = true)
public class WebsocketSessionStorageDTO implements Serializable {

    /**
     * Websocket ID
     */
    private Long webSocketId;

    private String sessionId;

    private String memory;

    private Session session;

    /**
     * 用户账号ID
     */
    private Long userAccountId;

}
