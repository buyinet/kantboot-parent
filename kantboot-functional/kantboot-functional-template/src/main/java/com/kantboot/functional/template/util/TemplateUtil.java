package com.kantboot.functional.template.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.SneakyThrows;

import java.io.StringWriter;

public class TemplateUtil {

    /**
     * 将模板内容中的变量替换为参数
     */
    @SneakyThrows
    public static String replaceParams(String content, Object params) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // 使用 freemarker
        Template template = new Template("content",content, configuration);
        // 生成模板
        StringWriter stringWriter = new StringWriter();
        template.process(params, stringWriter);
        return stringWriter.toString();
    }

}
