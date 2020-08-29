package com.yanzhen.utils;

import com.yanzhen.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//拦截器：拦截所有请求，并转发到登录页面
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Admin admin = SessionUtils.getAdmin(request);
        if(admin == null){
            response.setContentType("text/html;charset=utf-8");
            String path = request.getContextPath() + "/login";
            response.getWriter().print("您没有权限，请<a href='"+path+"'>登录</a>");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
