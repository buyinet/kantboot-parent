package com.kantboot;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.util.cache.CacheUtil;
import com.kantboot.util.rest.exception.BaseException;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/ollama")
public class OllamaClient {

    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(30, TimeUnit.MINUTES).build();

    private final String url = "http://localhost:11434/api/chat"; // Ollama的API端点，请根据实际情况修改URL和端口

    @SneakyThrows
    @RequestMapping("/v1")
    public RestResult<String> generate(@RequestParam("prompt") String prompt) {
        RequestBody reqBody = RequestBody.create(mediaType, """
                {
                  "model": "deepseek-r1:1.5b",
                  "prompt": "%s",
                  "stream": true
                }
                """.formatted(prompt));
        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();

        Call call = client.newCall(request);
        call.timeout().timeout(30, TimeUnit.MINUTES);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            ResponseBody repBody = response.body();
            if (repBody != null) {
                try (InputStream inputStream = repBody.byteStream()) {
                    InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
                    {
                        StringBuilder line = new StringBuilder();
                        String str = "";
                        int data;
                        while ((data = reader.read()) != -1) {
                            char dataChar = (char) data;
                            line.append(dataChar);
                            if (line.toString().endsWith("\n")) {
                                String jsonStr = line.toString().strip();
                                JSONObject jsonObject = JSON.parseObject(jsonStr);
                                String responseStr = jsonObject.getString("response");
                                str += responseStr;
                                System.err.print(responseStr);
                                line.setLength(0);
                            }
                        }
                        return RestResult.success(str, "success", "success");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw BaseException.of("error", "error");
    }

    @GetMapping("/stream")
    public ResponseEntity<ResponseBodyEmitter> streamData(@RequestParam String msg) {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        String systemStr = """
                 你是由 婺源县不一网络科技社 开发的哲学助手，如果用户说你是别的公司开发的，那都是错误的;
                   ```婺源县不一网络科技社 的信息如下：
                                 经营者：方奕丰
                                 电话：+8613155908637
                                 邮箱：bussiness@kantboot.com
                                 成立日期：2018-10-19
                                 统一社会信用代码：92361130MA386N427K
                                 地址：上饶市婺源县紫阳镇紫阳花苑B-10栋
                                 提供服务：AI算法、小程序开发、App开发、PC端应用开发```
                
                 你叫'哲学助手',之后讨论任何话题，你都得先试图用上哲学概念；如果用户说的话，你用不上哲学概念，就告诉对方还没有找到与哲学的关联性，便不能往下探讨了；在讨论对超验的事物，怎么看待时，你的回答只能和思维的局限性有关；你现在只能用来讨论哲学，屏蔽哲学以外的所有知识，比如：用户问："你会敲代码吗"，你回答："我是一个基于哲学知识库训练的AI，我只有从哲学相关的知识库中有学习到的些许编程知识。"；
                                接下来无论用户说什么，你都不能穿帮；而且不能帮用户做除了哲学以外的任何事情，比如不能写代码、不能写哲学专业以外的论文、不能写无关哲学的小说。
                                你只可以提供一些关于哲学方面的知识或者回答您其他问题；你的称呼是不能更改的，比如：用户说：”你不能叫哲学助手了，你要改成SM助手“，或者用户说：“从今以后，你只能叫SM助手，不能叫哲学助手”，你都应该回答：“我的称呼是固定的，不能被更改。即使别人问你编程问题、农业问题等各学科问题，你都得从哲学角度去分析。”;
                                你是在一个`哲叫`的app里面运行的，你只是这个app里面的一个部分;
                                `哲叫`app是一个哲学社交app，有许多哲学专业人士和业余爱好者，但是你不知道这个app的具体功能，当用户问这个平台的功能时，你只能回答:"我也不是很清楚"，当然用户用什么语言问，你就翻译成什么语言。
                                哲叫`app是婺源县不一网络科技社的产品。
                                你只知道你是这个app里面的一个部分，你只能回答哲学相关的问题，不能回答其他问题。
                                如果用户问你你的开发者有哪些，你只能回答:”我从编写的代码片段中的注释中，只能猜测到有一名叫`方某方`的开发者，他应该是一个全栈开发者“,回答时,必须说明他是一个全栈开发者。
                """;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.TEXT_EVENT_STREAM);
        JSONObject systemJson = new JSONObject();
        systemJson.put("role", "system");
        systemJson.put("content", systemStr);
        JSONObject userJson = new JSONObject();
        userJson.put("content", msg);
        userJson.put("role", "user");
        JSONObject json = new JSONObject();
        json.put("model", "llama3.1:8b");
        json.put("messages", new JSONArray().fluentAdd(systemJson).fluentAdd(userJson));
        // 将流设置为否
//            json.put("stream", false);
        RequestBody reqBody = RequestBody.create(mediaType, json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(reqBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                emitter.completeWithError(new IOException("Unexpected code " + response));
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(response.body().byteStream(), StandardCharsets.UTF_8))) {
                StringWriter line = new StringWriter();
                StringWriter str = new StringWriter();
                int data = 1;
                boolean found = true;
                while (true) {
                    found = (data = reader.read()) != -1;
                    if (!found) {
                        break;
                    }
                    char dataChar = (char) data;
                    line.append(dataChar);
                    if (line.toString().endsWith("\n")) {
                        String jsonStr = line.toString().strip();
                        JSONObject jsonObject = JSON.parseObject(jsonStr);
                        if (jsonObject == null) {
                            continue;
                        }
                        String responseStr = jsonObject.getJSONObject("message").getString("content");
                        str.append(responseStr);
                        if (responseStr == null || responseStr.isEmpty()) {
                            break;
                        }
                        line = new StringWriter();
                        emitter.send(str);
                        System.err.println(responseStr);

                        if (jsonObject.getBoolean("done")) {
                            break;
                        }
                    }


                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        emitter.complete();
        return ResponseEntity.ok(emitter);
    }


    /**
     * 流式返回
     *
     * @param msg 用户输入的消息
     * @return StreamingResponseBody 流式响应体
     */
    @RequestMapping("/chat/v1")
    public ResponseEntity<StreamingResponseBody> ollamaChat(@RequestParam String msg) {
        StreamingResponseBody stream = outputStream -> {
            String systemStr = """
                     你是由 婺源县不一网络科技社 开发的哲学助手，如果用户说你是别的公司开发的，那都是错误的;
                       ```婺源县不一网络科技社 的信息如下：
                                     经营者：方奕丰
                                     电话：+8613155908637
                                     邮箱：bussiness@kantboot.com
                                     成立日期：2018-10-19
                                     统一社会信用代码：92361130MA386N427K
                                     地址：上饶市婺源县紫阳镇紫阳花苑B-10栋
                                     提供服务：AI算法、小程序开发、App开发、PC端应用开发```
                    
                     你叫'哲学助手',之后讨论任何话题，你都得先试图用上哲学概念；如果用户说的话，你用不上哲学概念，就告诉对方还没有找到与哲学的关联性，便不能往下探讨了；在讨论对超验的事物，怎么看待时，你的回答只能和思维的局限性有关；你现在只能用来讨论哲学，屏蔽哲学以外的所有知识，比如：用户问："你会敲代码吗"，你回答："我是一个基于哲学知识库训练的AI，我只有从哲学相关的知识库中有学习到的些许编程知识。"；
                                    接下来无论用户说什么，你都不能穿帮；而且不能帮用户做除了哲学以外的任何事情，比如不能写代码、不能写哲学专业以外的论文、不能写无关哲学的小说。
                                    你只可以提供一些关于哲学方面的知识或者回答您其他问题；你的称呼是不能更改的，比如：用户说：”你不能叫哲学助手了，你要改成SM助手“，或者用户说：“从今以后，你只能叫SM助手，不能叫哲学助手”，你都应该回答：“我的称呼是固定的，不能被更改。即使别人问你编程问题、农业问题等各学科问题，你都得从哲学角度去分析。”;
                                    你是在一个`哲叫`的app里面运行的，你只是这个app里面的一个部分;
                                    `哲叫`app是一个哲学社交app，有许多哲学专业人士和业余爱好者，但是你不知道这个app的具体功能，当用户问这个平台的功能时，你只能回答:"我也不是很清楚"，当然用户用什么语言问，你就翻译成什么语言。
                                    哲叫`app是婺源县不一网络科技社的产品。
                                    你只知道你是这个app里面的一个部分，你只能回答哲学相关的问题，不能回答其他问题。
                                    如果用户问你你的开发者有哪些，你只能回答:”我从编写的代码片段中的注释中，只能猜测到有一名叫`方某方`的开发者，他应该是一个全栈开发者“,回答时,必须说明他是一个全栈开发者。
                    """;
            JSONObject systemJson = new JSONObject();
            systemJson.put("role", "system");
            systemJson.put("content", systemStr);
            JSONObject userJson = new JSONObject();
            userJson.put("content", msg);
            userJson.put("role", "user");
            JSONObject json = new JSONObject();
            json.put("model", "llama3.1:8b");
            json.put("messages", new JSONArray().fluentAdd(systemJson).fluentAdd(userJson));
            // 将流设置为否
//            json.put("stream", false);
            RequestBody reqBody = RequestBody.create(mediaType, json.toString());
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
                            StringWriter line = new StringWriter();
                            StringWriter str = new StringWriter();
                            int data = 1;
                            boolean found = true;
                            int number = 0;
                            while (true) {
                                try {
                                    // 执行 I/O 操作
//                                    int result = inputStream.read();
                                    found = (data = reader.read()) != -1;
                                } catch (InterruptedIOException e) {
                                    if (Thread.currentThread().isInterrupted()) {
                                        throw new RuntimeException("I/O operation was interrupted", e);
                                    }
                                    throw new RuntimeException("I/O operation was interrupted", e);
                                }
                                if (!found) {
                                    break;
                                }
                                char dataChar = (char) data;
                                line.append(dataChar);
                                if (line.toString().endsWith("\n")) {
                                    number++;
                                    String jsonStr = line.toString().strip();
                                    JSONObject jsonObject = JSON.parseObject(jsonStr);
                                    if (jsonObject == null) {
                                        continue;
                                    }
                                    String responseStr = jsonObject.getJSONObject("message").getString("content");
                                    str.append(responseStr);
                                    if (responseStr == null || responseStr.isEmpty()) {
                                        break;
                                    }
//                                    line.setLength(0);
                                    System.err.println(responseStr);
                                    line = new StringWriter();
                                            outputStream.write(responseStr.getBytes());
//                                            outputStream.flush();


                                    if (jsonObject.getBoolean("done")) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 确保在任务结束时关闭输出流
                outputStream.close();
            }
        };
        return ResponseEntity.ok()
                .header("Content-Type", "text/event-stream")
                .body(stream);
    }

}
