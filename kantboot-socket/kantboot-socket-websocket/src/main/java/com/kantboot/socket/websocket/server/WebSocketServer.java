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
                .setUserAccountId(byMemory.getUserAccountId())
                .setSession(session));
        socketWebsocketService.update(memory,WebsocketStatusCodeConstants.CONNECTING);
    }

    @OnClose
    public void onClose(@PathParam("memory") String memory, Session session) {
        WebsocketSessionStorageDTO byMemory = WebsocketSessionStorageUtil.getByMemory(memory);
        if(byMemory==null){
            log.info("断开连接:{}",session.getId());
            return;
        }
        log.info("断开连接:{},{}",byMemory.getMemory(),byMemory.getUserAccountId());
        WebsocketSessionStorageUtil.removeByMemory(memory);
        socketWebsocketService.update(memory,WebsocketStatusCodeConstants.END);
    }

    @OnMessage
    public void onMessage(@PathParam("memory") String memory,String message, Session session) {
        WebsocketSessionStorageDTO byMemory = WebsocketSessionStorageUtil.getByMemory(memory);
        if(byMemory==null){
            log.info("收到消息:{},{}",session.getId(),message);
            return;
        }
        log.info("收到消息:{},{},{}",byMemory.getMemory(),byMemory.getUserAccountId(),message);
        socketWebsocketService.heartbeat(memory);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        WebsocketSessionStorageDTO bySessionId = WebsocketSessionStorageUtil.getBySessionId(session.getId());
        if(bySessionId==null){
            log.info("发生错误：{},{}",session.getId(),error.getMessage());
            return;
        }
        log.info("发生错误：{},{}", JSON.toJSONString(bySessionId),error.getMessage());
    }

}