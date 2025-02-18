package com.kantboot.util.data.change.aspect;

import com.kantboot.util.data.change.annotaion.DataChange;
import com.kantboot.util.data.change.service.IDataChangeService;
import jakarta.annotation.Resource;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataChangeAspect {

    @Resource
    private IDataChangeService dataChangeService;

    @Pointcut("@annotation(com.kantboot.util.data.change.annotaion.DataChange)")
    public void dataChange() {
    }

    @After("dataChange() && @annotation(annotation)")
    public void after(DataChange annotation) throws Throwable {
        dataChangeService.dataChange(annotation.key());
        dataChangeService.dataChanges(annotation.keys());
    }

}