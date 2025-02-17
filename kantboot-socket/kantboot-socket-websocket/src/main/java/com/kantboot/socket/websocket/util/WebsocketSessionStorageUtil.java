package com.kantboot.socket.websocket.util;

import com.kantboot.socket.websocket.domain.dto.WebsocketSessionStorageDTO;
import lombok.SneakyThrows;

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
                try{
                    dto.getSession().close();
                }catch (Exception e){
                }
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

    /**
     * 根据用户账号ID查询
     */
    public static List<WebsocketSessionStorageDTO> getByUserAccountId(Long userAccountId) {
        List<WebsocketSessionStorageDTO> list = new ArrayList<>();
        for (WebsocketSessionStorageDTO dto : sessionList) {
            if (dto.getUserAccountId().equals(userAccountId)) {
                list.add(dto);
            }
        }
        return list;
    }

    public static void sendMessage(String memory,String message) {
        WebsocketSessionStorageDTO dto = getByMemory(memory);
        if (dto == null) {
            return;
        }
        dto.getSession().getAsyncRemote().sendText(message);
    }

    /**
     * 根据用户账号ID发送消息
     */
    @SneakyThrows
    public static void sendMessageByUserAccountId(Long userAccountId, String message) {
        List<WebsocketSessionStorageDTO> list = getByUserAccountId(userAccountId);
        for (WebsocketSessionStorageDTO dto : list) {
            try{
                dto.getSession().getAsyncRemote().sendText(message);
            }catch (Exception e) {
                dto.getSession().close();
            }
        }
    }

}
