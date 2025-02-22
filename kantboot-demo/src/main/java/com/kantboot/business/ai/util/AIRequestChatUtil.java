package com.kantboot.business.ai.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public abstract class AIRequestChatUtil {

    private final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(60, TimeUnit.MINUTES).build();

    private final String url = "https://ark.cn-beijing.volces.com/api/v3/chat/completions"; // Ollama的API端点，请根据实际情况修改URL和端口

    public abstract void run(String responseStr, String str, Boolean done);

    public abstract void finish(String str);

    public AIRequestChatUtil(String json) {
        inChatStream(json);
    }

    public void inChatStream(String json) {
        System.err.println(json);
        RequestBody reqBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer f203011a-ef18-4d4f-9acb-72d4a9d5d0e4")
                .post(reqBody)
                .build();
        Call call = client.newCall(request);
        StringWriter str = new StringWriter();

        try (Response response = call.execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            log.info("回复中...");
            ResponseBody repBody = response.body();

            // 缓冲区
            if (repBody != null) {
                try (InputStream inputStream = repBody.byteStream()) {
                    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    final StringWriter[] line = {new StringWriter()};
                    final int[] data = {1};
                    final AtomicBoolean[] found = {new AtomicBoolean(true)};

                    while (true) {
                        try {
                            // 执行 I/O 操作
//                                    int result = inputStream.read();
                            found[0].set((data[0] = reader.read()) != -1);
                        } catch (InterruptedIOException e) {
                            if (Thread.currentThread().isInterrupted()) {
                                throw new RuntimeException("I/O operation was interrupted", e);
                            }
                            throw new RuntimeException("I/O operation was interrupted", e);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!found[0].get()) {
                            break;
                        }
                        char dataChar = (char) data[0];
                        line[0].append(dataChar);
                        if (line[0].toString().endsWith("\n\n")) {
                            String jsonStr = line[0].toString().strip();
                            jsonStr = jsonStr.substring("data: ".length());
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject = JSON.parseObject(jsonStr);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            String responseStr = jsonObject.getJSONArray("choices").getJSONObject(0).getJSONObject("delta").getString("content");
                            str.append(responseStr);
                            line[0] = new StringWriter();

                            try {
                                this.run(responseStr, str.toString(), false);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }


                }
            }
        } catch (IOException e) {
            this.finish(str.toString());
            e.printStackTrace();
        } finally {
            this.finish(str.toString());
        }
    }

}
