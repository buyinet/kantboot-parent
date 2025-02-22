package com.kantboot.api.volcengine.ai.chat.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.volcengine.ai.chat.domain.dto.ApiVolcengineBase;
import com.kantboot.api.volcengine.ai.chat.method.ApiVolcengineMethod;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ApiVolcengineRequestUtil {

    private final static MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(60, TimeUnit.MINUTES).build();

    public static void requestChatHasStream(ApiVolcengineBase dto, String url, ApiVolcengineMethod method, String appKey) {
        // 强制设置为流式
        dto.setStream(true);
        InputStreamReader reader = getInputStreamReader(dto, url, appKey);
        // 获取所有数据
        StringWriter content = new StringWriter();
        // 获取所有数据
        StringWriter line = new StringWriter();
        String lastStr = "";
        int couNumber = 0;
        int data = 0;
        while (true) {
            try {
                data = reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (data == -1) {
                break;
            }
            char dataChar = (char) data;
            line.append(dataChar);
            if (!line.toString().endsWith("\n\n")) {
                continue;
            }
            String jsonStr = line.toString().strip();
            jsonStr = jsonStr.substring("data: ".length());
            if (jsonStr.endsWith("[DONE]")) {
                method.run("", content.toString(), true);
                method.finish(content.toString());
                return;
            }
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = JSON.parseObject(jsonStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String responseStr = jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").getString("content");
            content.append(responseStr);
            line = new StringWriter();

            try {
                method.run(responseStr, content.toString(), false);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        try {
            method.finish(content.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStreamReader getInputStreamReader(ApiVolcengineBase dto, String url, String appKey) {
        RequestBody reqBody = RequestBody.create(mediaType, ApiVolcengineJsonUtil.getChatJson(dto).toString());
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + appKey)
                .url(url)
                .post(reqBody)
                .build();
        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!response.isSuccessful()) {
            throw new RuntimeException("Unexpected code " + response);
        }
        // 获得响应体
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new RuntimeException("Response body is null");
        }
        InputStream inputStream = responseBody.byteStream();
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

}
