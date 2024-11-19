package com.kantboot.api.qinucloud.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.kantboot.api.qinucloud.exception.ApiQiniuCloudException;
import com.kantboot.util.rest.exception.BaseException;
import com.qiniu.common.Constants;
import com.qiniu.common.QiniuException;
import com.qiniu.sms.SmsManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * 七牛云工具类
 * Qiniu Cloud Util
 */
@Slf4j
public class ApiQiniuCloudUtil {

    @SneakyThrows
    public static void sendFulltextMessageOne(String phone, String content, Auth auth) {
        String requestUrl = String.format("%s/v1/message/fulltext", "https://sms.qiniuapi.com");
        StringMap bodyMap = new StringMap();
        bodyMap.put("mobiles", new String[]{phone});
        bodyMap.put("content", content);
        // 强行使用smsManager的private方法进行发送短信（为了更好获取异常信息）
        // Use the private method of smsManager to send the message(for better exception information)
        SmsManager smsManager = new SmsManager(auth);
        Method method = SmsManager.class.getDeclaredMethod("post", String.class, byte[].class);
        method.setAccessible(true);
        try{
            method.invoke(smsManager, requestUrl, Json.encode(bodyMap).getBytes(Constants.UTF_8));
        }catch (Exception e){
            // 获取异常信息
            // Get exception information
            QiniuException qiniuException = (QiniuException) e.getCause();
            // 解析异常信息
            // Parse exception information
            JSONObject jsonObject = JSON.parseObject(qiniuException.response.bodyString());
            // 获取异常信息
            // Get exception information
            String error = jsonObject.getString("error");
            // 获取异常信息
            // Get exception information
            String message = jsonObject.getString("message");
            // 日志记录短信发送失败的原因
            // Log the reason for the failure of the message sending
            log.error("Failed to send message, reason: {}", message);
            if (error.equals("InvalidArgument")&&message.endsWith("not match any of your templates")){
                // 返回给前端的错误信息，告知前端 七牛云模板不存在
                // Return the error information to the front end,
                // informing the front end that the Qiniu Cloud template does not exist
                throw ApiQiniuCloudException.QINIU_CLOUD_ERR_CODE_OF_INVALID_ARGUMENT;
            }
            if(message.startsWith("invalid content, get signature failed")){
                // 返回给前端的错误信息，告知前端 七牛云模板不存在
                // Return the error information to the front end,
                // informing the front end that the Qiniu Cloud template does not exist
                throw ApiQiniuCloudException.QINIU_CLOUD_ERR_CODE_OF_INVALID_ARGUMENT;
            }
            // 返回给前端的错误信息，告知前端 七牛云发送短信失败
            // Return the error information to the front end, informing the front end that the Qiniu Cloud failed to send the message
            throw BaseException.of("qiniuCloudSendFulltextMessageOneFail","七牛云发送短信失败");
        }
    }


}
