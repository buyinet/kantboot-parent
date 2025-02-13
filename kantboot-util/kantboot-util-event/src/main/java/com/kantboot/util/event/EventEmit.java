package com.kantboot.util.event;

import com.kantboot.util.event.emit.EmitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 全局事件发射器
 */
@Slf4j
@Component
public class EventEmit implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 发送事件
     * Send event
     *
     * @param code 事件编码，用于区分不同的事件
     *             Event code, used to distinguish different events
     */
    public void to(String code, Object... value) {
        log.info("GlobalEventEmit.emit: code={}", code);
        // 获取监听事件所在的方法
        // Get the method where the listener event is located
        List<Method> method = EmitUtil.getMethod(code);
        if (method == null) {
            // 如果方法为空，则直接返回
            // If the method is empty, return directly
            log.info("GlobalEventEmit.emit: Method is null");
            return;
        }
        for (Method m : method) {
            // 获取监听事件所在的类
            // Get the class where the listener event is located
            Class<?> clazz = m.getDeclaringClass();
            // 获取监听事件所在的类的实例
            // Get an instance of the class where the listener event is located
            Object bean = applicationContext.getBean(clazz);
            // 执行监听事件
            // Execute the listener event
            try {
                // 调用方法
                // Call method
                m.setAccessible(true);
                m.invoke(bean, value);
            } catch (InvocationTargetException e) {
                // 如果发生InvocationTargetException，则记录其原因
                // If an InvocationTargetException occurs, log its cause
                log.error("GlobalEventEmit.emit: InvocationTargetException cause", e.getCause());
                throw new RuntimeException(e.getCause());
            } catch (Exception e) {
                // 如果发生异常，则记录日志
                // If an exception occurs, log it
                log.error("GlobalEventEmit.emit: Error", e);
                try {
                    throw e;
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

    }
}
