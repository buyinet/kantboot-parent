package com.kantboot.business.ai.slot;

import com.kantboot.business.ai.domain.dto.BusAiChatDTO;
import com.kantboot.business.ai.method.BusAIChatMethod;

public interface ISendMessageSlot {

    void sendMessageHasStream(BusAiChatDTO dto, BusAIChatMethod method);

}
