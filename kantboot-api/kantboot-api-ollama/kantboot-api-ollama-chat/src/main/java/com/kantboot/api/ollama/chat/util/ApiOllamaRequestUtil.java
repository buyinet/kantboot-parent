package com.kantboot.api.ollama.chat.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.ollama.chat.method.ApiOllamaMethod;
import com.kantboot.api.ollama.chat.domain.dto.ApiOllamaBase;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ApiOllamaRequestUtil {

    private final static MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final static OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(60, TimeUnit.MINUTES).build();

    public static void requestChatHasStream(ApiOllamaBase dto, String url, ApiOllamaMethod ollamaAbstract) {
        // 强制设置为流式
        dto.setStream(true);
        InputStreamReader reader = getInputStreamReader(dto, url);
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
            if (!line.toString().endsWith("\n")) {
                continue;
            }
            System.err.println(line.toString());
            String jsonStr = line.toString().strip();
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            if (jsonObject == null) {
                continue;
            }
            String responseStr = jsonObject.getJSONObject("message").getString("content");
            content.append(responseStr);
//            if (responseStr == null || responseStr.isEmpty()) {
//                break;
//            }
            // 清空line
            line = new StringWriter();
            // 代表结束
            Boolean done = jsonObject.getBoolean("done");
            ollamaAbstract.run(responseStr, content.toString(), done);
            if (done) {
                String doneReason = jsonObject.getString("don_reason");
                if ("stop".equals(doneReason)) {
                    log.error("done_reason:{}", doneReason);
                    if(dto.getIsConnect()!=null&&dto.getIsConnect()){
                        log.error("done_reason_in_connect:{}", doneReason);
                        // 调用run方法
                        ollamaAbstract.finish(content.toString());
                        return;
                    }
                    dto.setIsConnect(true);
                    requestChatHasStream(dto, url, ollamaAbstract);
                    return;
                }
                // 调用finish方法
                ollamaAbstract.finish(content.toString());
                break;
            }
        }
    }

    private static InputStreamReader getInputStreamReader(ApiOllamaBase dto, String url) {
        RequestBody reqBody = RequestBody.create(mediaType, ApiOllamaJsonUtil.getChatJson(dto).toString());
        Request request = new Request.Builder()
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
