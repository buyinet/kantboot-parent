package com.kantboot.socket.websocket.web.controller;

import com.kantboot.socket.websocket.service.ISocketWebsocketService;
import com.kantboot.util.rest.common.CommonSuccessStateCodeAndMsg;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/socket-websocket-web/websocket")
public class SocketWebsocketController {

    @Resource
    private ISocketWebsocketService socketWebsocketService;

    @RequestMapping("/createMemory")
    public RestResult<?> createMemory() {
        return RestResult.success(socketWebsocketService.createMemory(), CommonSuccessStateCodeAndMsg.GET_SUCCESS);
    }

}
