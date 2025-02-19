package com.kantboot.socket.websocket.event;

import com.alibaba.fastjson2.JSON;
import com.kantboot.functional.message.domain.entity.FunctionalMessage;
import com.kantboot.socket.websocket.util.WebsocketSessionStorageUtil;
import com.kantboot.util.event.annotation.EventOn;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 关于主体的保存事件
 */
@Component
@Slf4j
public class WebsocketMessageEvent {

    @Async
    @Transactional
    @EventOn("functionalMessage:message:websocket")
    public void onSendMessage(FunctionalMessage message) {
        log.info("收到Websocket消息:{}", JSON.toJSONString(message));
        WebsocketSessionStorageUtil.sendMessageByUserAccountId(message.getUserAccountId(),message.getDataStr());
    }

}
