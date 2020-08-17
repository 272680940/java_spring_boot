package com.hqyj.java_spring_boot.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class RequestViewInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestViewInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.debug("-----拦截器----preHandle()-----------");
        return HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.debug("----拦截器-----postHandle()-----------");

        if (modelAndView == null || modelAndView.getViewName().startsWith("redirect")){
            return;
        }

        // (1) 获取PATH路径
        String path = request.getServletPath();

        // (2) 获取ModelMap中是否已经获取了【template】属性
        String template = (String) modelAndView.getModelMap().get("template");

        // (3) 判断是否已经手动设置
        if (StringUtils.isBlank(template)){
            // 判断路径前是否有 /
            if(path.startsWith("/")){
                path = path.substring(1);
            }
            modelAndView.getModelMap().addAttribute(
                    "template",path.toLowerCase());
        }

        HandlerInterceptor.super.preHandle(request,response,handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.debug("----拦截器-----afterCompletion()-----------");

        HandlerInterceptor.super.preHandle(request,response,handler);
    }
}
