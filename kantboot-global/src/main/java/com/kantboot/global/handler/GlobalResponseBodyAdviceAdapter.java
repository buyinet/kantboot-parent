package com.kantboot.global.handler;

import com.kantboot.system.dict.domain.dto.ToGobleDTO;
import com.kantboot.system.dict.service.ISysDictI18nService;
import com.kantboot.util.http.HttpRequestHeaderUtil;
import com.kantboot.util.rest.result.RestResult;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class GlobalResponseBodyAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Resource
    private HttpRequestHeaderUtil httpRequestHeaderUtil;

    @Resource
    private ISysDictI18nService sysDictI18nService;

    /**
     * 是否自动翻译
     */
    @Value("${kantboot.isAutoTranslate}")
    private boolean isAutoTranslate;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String languageCode = httpRequestHeaderUtil.getLanguageCode();

        if (!(body instanceof RestResult<?>)) {
            return body;
        }

        RestResult<?> result = (RestResult<?>) body;

        if (languageCode == null || languageCode.equals("en")) {
            return result;
        }


        if (result.isSuccess()) {
            String text =
                    sysDictI18nService.getDictText(languageCode, "requestSuccess", result.getMsgDictCode());
            if (text != null) {
                result.setMsg(text);
                return result;
            }
        } else {
            String text =
                    sysDictI18nService.getDictText(languageCode, "requestException", result.getMsgDictCode());
            if (text != null) {
                result.setErrMsg(text);
                return result;
            }
        }

        if (!isAutoTranslate) {
            return result;
        }
        try {
            if (result.isSuccess()) {
                ToGobleDTO toGobleDTO = new ToGobleDTO();
                toGobleDTO.setDictGroupCode("requestSuccess");
                toGobleDTO.setDictCode(result.getMsgDictCode());
                toGobleDTO.setSourceLanguageCode("en");
                toGobleDTO.setTargetLanguageCode(languageCode);
                toGobleDTO.setText(result.getMsg());
                result.setMsg(sysDictI18nService.getTargetLanguageText(toGobleDTO));
            } else {
                ToGobleDTO toGobleDTO = new ToGobleDTO();
                toGobleDTO.setDictGroupCode("requestException");
                toGobleDTO.setDictCode(result.getMsgDictCode());
                toGobleDTO.setSourceLanguageCode("en");
                toGobleDTO.setTargetLanguageCode(languageCode);
                toGobleDTO.setText(result.getErrMsg());
                String text = sysDictI18nService.getTargetLanguageText(toGobleDTO);
                result.setErrMsg(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }


        return result;
    }

}