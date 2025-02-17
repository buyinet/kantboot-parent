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

    private String sessionId;

    private String memory;

    private Session session;

}
