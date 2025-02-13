package com.kantboot.business.ovo.util;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class OvoAIRequestChat {

    private final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(60, TimeUnit.MINUTES).build();

    private final String url = "http://localhost:11434/api/chat"; // Ollama的API端点，请根据实际情况修改URL和端口

    public abstract void run(String responseStr, String str);

    public abstract void finish();

    public OvoAIRequestChat(String json) {
        inChatStream(json);
    }

    public void inChatStream(String json) {
        RequestBody reqBody = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


            ResponseBody repBody = response.body();


            // 缓冲区

            if (repBody != null) {
                try (InputStream inputStream = repBody.byteStream()) {
                    InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    {
                        final StringWriter[] line = {new StringWriter()};
                        StringWriter str = new StringWriter();
                        final int[] data = {1};
                        final AtomicBoolean[] found = {new AtomicBoolean(true)};
                        AtomicBoolean threading = new AtomicBoolean(true);
                        ThreadUtil.execute(() -> {

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
                                }catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (!found[0].get()) {
                                    threading.set(false);
                                    break;
                                }
                                char dataChar = (char) data[0];
                                line[0].append(dataChar);
                                if (line[0].toString().endsWith("\n")) {
                                    String jsonStr = line[0].toString().strip();
                                    JSONObject jsonObject = JSON.parseObject(jsonStr);
                                    if (jsonObject == null) {
                                        continue;
                                    }
                                    String responseStr = jsonObject.getJSONObject("message").getString("content");
                                    str.append(responseStr);
                                    if (responseStr == null || responseStr.isEmpty()) {
                                        threading.set(false);
                                        break;
                                    }
                                    System.err.print(responseStr);
                                    line[0] = new StringWriter();
                                    if (jsonObject.getBoolean("done")) {
                                        threading.set(false);
                                        break;
                                    }
                                    try{
                                        this.run(responseStr, str.toString());
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }

                        });

                        int i = 0;
                        while (threading.get()) {
                            i++;
                            if (i > 60*60) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }


                    }
                }
            }
        } catch (IOException e) {
            this.finish();
            e.printStackTrace();
        } finally {
            this.finish();
        }
    }

}
