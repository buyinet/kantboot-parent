package com.kantboot.socket.websocket.util;

import com.kantboot.socket.websocket.domain.dto.WebsocketSessionStorageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class WebsocketSessionStorageUtil {

    private static List<WebsocketSessionStorageDTO> sessionList = new ArrayList<>();

    /**
     * 数量
     */
    private static AtomicLong count = new AtomicLong(0);

    /**
     * 添加Session
     */
    public static void add(WebsocketSessionStorageDTO dto) {
        for (WebsocketSessionStorageDTO sessionStorageDTO : sessionList) {
            if (sessionStorageDTO.getMemory().equals(dto.getMemory())) {
                sessionList.remove(sessionStorageDTO);
                break;
            }
        }
        sessionList.add(dto);
        // 总数加1
        count.incrementAndGet();
    }

    /**
     * 移除Session
     */
    public static void removeByMemory(String memory) {
        for (WebsocketSessionStorageDTO dto : sessionList) {
            if (dto.getMemory().equals(memory)) {
                sessionList.remove(dto);
                // 总数减1
                count.decrementAndGet();
                break;
            }
        }
    }

    /**
     * 根据memory获取
     */
    public static WebsocketSessionStorageDTO getByMemory(String memory) {
        for (WebsocketSessionStorageDTO dto : sessionList) {
            if (dto.getMemory().equals(memory)) {
                return dto;
            }
        }
        return null;
    }

    /**
     * 根据sessionId获取
     */
    public static WebsocketSessionStorageDTO getBySessionId(String sessionId) {
        for (WebsocketSessionStorageDTO dto : sessionList) {
            if (dto.getSessionId().equals(sessionId)) {
                return dto;
            }
        }
        return null;
    }

    public static void sendMessage(String memory,String message) {
        WebsocketSessionStorageDTO dto = getByMemory(memory);
        if (dto == null) {
            return;
        }
        dto.getSession().getAsyncRemote().sendText(message);
    }

}
