package com.xin.component.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.component.interceptors
 * @Author: LI Renxin
 * @CreateTime: 2021-07-14 14:59
 * @ModificationHistory Who    When    What
 * @Description: 自定义拦截器1
 */
public class FirstHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器1号执行preHandle、、、");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器1号执行postHandle、、、");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器1号执行afterCompletion、、、");
    }
}
