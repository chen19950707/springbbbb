package com.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class myInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        String[] suf = {".data",".jpg",".png",".ico",".js",".css",".html",".ttf"};
        for (String s : suf) {
            if (request.getRequestURI().endsWith(s)) {
                return true;
            }
        }

        return true;
    }
}
