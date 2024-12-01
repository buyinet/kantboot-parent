package com.kantboot.global.auth;

import com.alibaba.fastjson2.JSON;
import com.kantboot.system.auth.exception.SystemAuthException;
import com.kantboot.util.rest.exception.BaseException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

     @Resource
     private GlobalInterceptorUtil globalInterceptorUtil;

     @Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         String uri = request.getRequestURI();
         if (globalInterceptorUtil.checkPass(uri)) {
             return true;
         }
         response.setStatus(200);
         response.setContentType("application/json;charset=UTF-8");
         response.getWriter().println(JSON.toJSONString(new BaseException().exceptionHandler(SystemAuthException.NO_PERMISSION)));
         return false;
     }


}












