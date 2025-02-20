package com.kantboot.api.ollama.chat.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaBase;

/**
 * ApiOllamaUtil
 */
public class ApiOllamaJsonUtil {

    /**
     * 获取流式数据
     */
    public static JSONObject getRootJson(ApiOllamaBase dto) {
        Boolean stream = dto.getStream();
        if(stream==null){
            stream=true;
        }
        JSONObject json = new JSONObject();
        json.put("model", "llama3.2-vision:latest");
        json.put("stream", stream);
        return json;
    }

    /**
     * 获取消息数据
     */
    public static JSONArray getMessages(ApiOllamaBase dto) {
        JSONArray messages = new JSONArray();
        for (Object message : dto.getMessages()) {
            JSONObject jsonObject = new JSONObject();
            JSONObject messageJson = (JSONObject) message;
            jsonObject.put("role", messageJson.get("role"));
            jsonObject.put("content", messageJson.get("content"));
            messages.add(jsonObject);
        }
        return messages;
    }

    /**
     * 获取聊天数据
     */
    public static JSONObject getChatJson(ApiOllamaBase dto) {
        JSONObject json = getRootJson(dto);
        json.put("messages", getMessages(dto));
        return json;
    }

    /**
     * 获取生成数据
     */
    public static JSONObject getGenerateJson(ApiOllamaBase dto) {
        JSONObject json = getChatJson(dto);
        json.put("prompt", dto.getPrompt());
        return json;
    }



}