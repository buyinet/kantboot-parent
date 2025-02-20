package com.kantboot.socket.websocket.schedule;

import cn.hutool.core.bean.BeanUtil;
import com.kantboot.socket.websocket.constants.WebsocketStatusCodeConstants;
import com.kantboot.socket.websocket.dao.repository.SocketWebsocketRecordRepository;
import com.kantboot.socket.websocket.dao.repository.SocketWebsocketRepository;
import com.kantboot.socket.websocket.domain.entity.SocketWebsocket;
import com.kantboot.socket.websocket.domain.entity.SocketWebsocketRecord;
import com.kantboot.socket.websocket.util.WebsocketSessionStorageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class WebsocketSchedule {

    @Resource
    private SocketWebsocketRepository repository;

    @Resource
    private SocketWebsocketRecordRepository recordRepository;

    @Scheduled(fixedRate = 1000 * 10)
    public void websocketScheduleHandle() {
        int count = 0;
        List<SocketWebsocket> byStatusCode = repository.getByStatusCode(WebsocketStatusCodeConstants.WAITING);
        // 如果有1分钟以上的全部都改为关闭状态
        for (SocketWebsocket socketWebsocket : byStatusCode) {
            if (System.currentTimeMillis() - socketWebsocket.getGmtCreate().getTime() > 1000 * 60) {
                socketWebsocket.setStatusCode(WebsocketStatusCodeConstants.END);
                SocketWebsocket save = repository.save(socketWebsocket);
                SocketWebsocketRecord record = BeanUtil.copyProperties(save, SocketWebsocketRecord.class);
                record.setId(null);
                recordRepository.save(record);
                WebsocketSessionStorageUtil.removeByMemory(socketWebsocket.getMemory());
                count++;
            }
        }
        byStatusCode = repository.getByStatusCode(WebsocketStatusCodeConstants.CONNECTING);
        // 分出没有心跳的和有心跳的
        for (SocketWebsocket socketWebsocket : byStatusCode) {
            if (System.currentTimeMillis() - socketWebsocket.getGmtCreate().getTime() > 1000 * 60&&socketWebsocket.getGmtLastHeartbeat()==null) {
                socketWebsocket.setStatusCode(WebsocketStatusCodeConstants.END);
                SocketWebsocket save = repository.save(socketWebsocket);
                SocketWebsocketRecord record = BeanUtil.copyProperties(save, SocketWebsocketRecord.class);
                record.setId(null);
                recordRepository.save(record);
                WebsocketSessionStorageUtil.removeByMemory(socketWebsocket.getMemory());
                count++;
            }else if(socketWebsocket.getGmtLastHeartbeat()!=null&&System.currentTimeMillis() - socketWebsocket.getGmtLastHeartbeat().getTime() > 1000 * 60){
                socketWebsocket.setStatusCode(WebsocketStatusCodeConstants.END);
                SocketWebsocket save = repository.save(socketWebsocket);
                SocketWebsocketRecord record = BeanUtil.copyProperties(save, SocketWebsocketRecord.class);
                record.setId(null);
                recordRepository.save(record);
                WebsocketSessionStorageUtil.removeByMemory(socketWebsocket.getMemory());
                count++;
            }
        }
        log.info("websocketScheduleHandle:cleanCount:{}", count);

    }

}
