package com.kantboot.api.volcengine.ai.chat.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineBase;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineMessage;

/**
 * ApiVolcengineUtil
 */
public class ApiVolcengineJsonUtil {

    /**
     * 获取流式数据
     */
    public static JSONObject getRootJson(ApiVolcengineBase dto) {
        Boolean stream = dto.getStream();
        if(stream==null){
            stream=true;
        }
        JSONObject json = new JSONObject();
        json.put("model", dto.getModel());
        json.put("stream", stream);
        return json;
    }

    /**
     * 获取消息数据
     */
    public static JSONArray getMessages(ApiVolcengineBase dto) {
        JSONArray messages = new JSONArray();
        for (ApiVolcengineMessage message : dto.getMessages()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", message.getRole());
            jsonObject.put("content", message.getContent());
            messages.add(jsonObject);
        }
        return messages;
    }

    /**
     * 获取聊天数据
     */
    public static JSONObject getChatJson(ApiVolcengineBase dto) {
        JSONObject json = getRootJson(dto);
        json.put("messages", getMessages(dto));
        return json;
    }

    /**
     * 获取生成数据
     */
    public static JSONObject getGenerateJson(ApiVolcengineBase dto) {
        JSONObject json = getChatJson(dto);
        json.put("prompt", dto.getPrompt());
        return json;
    }



}