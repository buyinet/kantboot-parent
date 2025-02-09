package com.kantboot;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import reactor.core.publisher.Flux;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/ollamaV2")
public class OllamaClientV2 {


    private static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.MINUTES) // 设置连接超时时间
            .writeTimeout(30, TimeUnit.MINUTES)   // 设置写入超时时间
            .readTimeout(30, TimeUnit.MINUTES).build();
    @RequestMapping("/a")
    public ResponseBodyEmitter handleRequest() {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    emitter.send(("{"+i+"}"));
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.completeWithError(e);
                    return;
                }
            }
            emitter.complete();
        });
        return emitter;
    }
    @GetMapping(value = "/flux")
    public Flux<String> flux() {
        Flux<String> result = Flux
                .fromStream(IntStream.range(1,5).mapToObj(i->{
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException ignored){
                    }
                    return "flux data--"+ i;
                }));
        return result;
    }


}
