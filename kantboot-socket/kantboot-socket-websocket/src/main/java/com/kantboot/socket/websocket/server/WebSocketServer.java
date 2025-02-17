package com.kantboot.socket.websocket.server;

import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.message.domain.dto.MessageDTO;
import com.kantboot.socket.websocket.constants.WebsocketStatusCodeConstants;
import com.kantboot.socket.websocket.domain.dto.WebsocketSessionStorageDTO;
import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;
import com.kantboot.socket.websocket.service.ISocketWebsocketService;
import com.kantboot.socket.websocket.util.WebsocketSessionStorageUtil;
import jakarta.annotation.Resource;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ServerEndpoint("/socket-websocket-web/socket/{memory}")
public class WebSocketServer {

    private static ISocketWebsocketService socketWebsocketService;

    @Resource
    public void setSocketWebsocketService(ISocketWebsocketService socketWebsocketService) {
        WebSocketServer.socketWebsocketService = socketWebsocketService;
    }

    @SneakyThrows
    @OnOpen
    public void onOpen(@PathParam("memory") String memory, Session session) {
        SocketWebsocket byMemory = socketWebsocketService.getByMemory(memory);
        if(byMemory==null||!WebsocketStatusCodeConstants.WAITING.equals(byMemory.getStatusCode())){
            // 提示连接失败
            session.getAsyncRemote().sendText(
                    JSON.toJSONString(new MessageDTO().setEmit("webSocketConnectFail"))
            );
            session.close();
            return;
        }

        log.info("{}连接成功:{}",memory,session.getId());
        WebsocketSessionStorageUtil.add(new WebsocketSessionStorageDTO()
                .setSessionId(session.getId())
                .setMemory(memory)
                .setSession(session));
    }

    @OnClose
    public void onClose(@PathParam("memory") String memory, Session session) {
        WebsocketSessionStorageDTO byMemory = WebsocketSessionStorageUtil.getByMemory(memory);
        log.info("断开连接:{}",JSON.toJSONString(byMemory));
        WebsocketSessionStorageUtil.removeByMemory(memory);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        WebsocketSessionStorageDTO bySessionId = WebsocketSessionStorageUtil.getBySessionId(session.getId());
        log.info("收到消息：{},{}", message,JSON.toJSONString(bySessionId));
    }

    @OnError
    public void onError(Session session, Throwable error) {
        WebsocketSessionStorageDTO bySessionId = WebsocketSessionStorageUtil.getBySessionId(session.getId());
        log.info("发生错误：{},{}", JSON.toJSONString(bySessionId),error.getMessage());
    }

}