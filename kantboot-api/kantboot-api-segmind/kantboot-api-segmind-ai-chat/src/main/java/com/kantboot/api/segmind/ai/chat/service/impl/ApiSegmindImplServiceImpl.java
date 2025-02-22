package com.kantboot.api.segmind.ai.chat.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.segmind.ai.chat.domain.dto.ApiSegmindBase;
import com.kantboot.api.segmind.ai.chat.domain.dto.ApiSegmindMessage;
import com.kantboot.api.segmind.ai.chat.method.ApiSegmindMethod;
import com.kantboot.api.segmind.ai.chat.service.IApiSegmindService;
import com.kantboot.api.segmind.setting.ApiSegmindSetting;
import jakarta.annotation.Resource;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ApiSegmindImplServiceImpl implements IApiSegmindService {

    private final static MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(60, TimeUnit.MINUTES).build();


    @Resource
    private ApiSegmindSetting setting;

    @Override
    public void chatHasStream(ApiSegmindBase dto, ApiSegmindMethod method) {
        if (dto.getModel() == null) {
            dto.setModel(setting.getDefaultModel());
        }

        JSONObject jsonObject = new JSONObject();
        JSONArray messages = new JSONArray();
        for (ApiSegmindMessage message : dto.getMessages()) {
            JSONObject jsonObjectInMessage = new JSONObject();
            jsonObjectInMessage.put("role", message.getRole());
            jsonObjectInMessage.put("content", message.getContent());
            messages.add(jsonObjectInMessage);
        }
        jsonObject.put("messages", messages);
        toToRequest(dto, method,jsonObject,0);
    }

    private String toToRequest(ApiSegmindBase dto, ApiSegmindMethod method,JSONObject jsonObject,int number) {
        JSONObject json = toRequest(jsonObject, dto.getModel());

        String content = "";
        String string = json.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        String finishReason = json.getJSONArray("choices").getJSONObject(0).getString("finish_reason");
        content += string;

        if (number > 10) {
            method.run(string, content, true);
            method.finish(content);
            return content;
        }

        boolean equals = "stop".equals(finishReason);
        method.run(string, content, equals);
        if (equals) {
            method.finish(content);
            return content;
        }

        JSONObject jsonObjectNew = JSON.parseObject(jsonObject.toJSONString());
        JSONObject jsonObjectInMessage = new JSONObject();
        jsonObjectInMessage.put("role", "assistant");
        jsonObjectInMessage.put("content", content);
        JSONArray messagesNew = JSON.parseArray(jsonObject.getJSONArray("messages").toJSONString());
        messagesNew.add(jsonObjectInMessage);
        jsonObjectNew.put("messages", messagesNew);
        return toToRequest(dto, method,jsonObjectNew,number+1);
    }

    private JSONObject toRequest(JSONObject jsonObject, String model) {
        RequestBody body = RequestBody.create(mediaType, jsonObject.toJSONString());
        Request request = new Request.Builder()
                .url(setting.getBaseUrl() + "/v1/" + model)
                .post(body)
                .addHeader("x-api-key", setting.getAppKey())
                .addHeader("Accept", "*/*")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("User-Agent", "PostmanRuntime-ApipostRuntime/1.1.0")
                .addHeader("Connection", "keep-alive")
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful()) {
            throw new RuntimeException("Unexpected code " + response);
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new RuntimeException("Response body is null");
        }

        try {
            String string = responseBody.string();
            return JSONObject.parseObject(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
