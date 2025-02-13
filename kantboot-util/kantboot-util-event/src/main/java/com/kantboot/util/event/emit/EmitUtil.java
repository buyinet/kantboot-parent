package com.kantboot.util.event.emit;

import com.kantboot.util.event.annotation.EventOn;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EmitUtil {

    private static final Map<String, List<Method>> map = new HashMap<>();

    static {
        // 获取com.kantboot包下的所有的类
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("com.kantboot"))
                .setScanners(new SubTypesScanner(false)));
        // 获取所有类中的方法，记录包含@GlobalEventOn注解的方法
        reflections.getSubTypesOf(Object.class).forEach(clazz -> {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(EventOn.class)) {
                    EventOn annotation = method.getAnnotation(EventOn.class);
                    String code = annotation.value();
                    // 如果map中已经存在code，则添加到list中
                    if (map.containsKey(code)) {
                        List<Method> methods = map.get(code);
                        methods.add(method);
                        return;
                    }
                    ArrayList<Method> methods = new ArrayList<>();
                    methods.add(method);
                    map.put(code, methods);
                }
            }
        });
    }

    /**
     * springboot开启时就启动
     */
    @PostConstruct
    public void init() {
        log.info("EmitUtil.init");
        initMap();
    }

    public static void initMap() {
        map.forEach((k, v) -> {
            log.info("EmitUtil.init: code={}, methods={}", k, v);
        });
    }


    /**
     * 根据事件码获取事件监听方法
     */
    public static List<Method> getMethod(String code) {
        return map.get(code);
    }

}
