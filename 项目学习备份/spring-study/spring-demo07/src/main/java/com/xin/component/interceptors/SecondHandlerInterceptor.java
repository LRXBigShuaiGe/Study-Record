package com.xin.component.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.component.interceptors
 * @Author: LI Renxin
 * @CreateTime: 2021-07-14 15:07
 * @ModificationHistory Who    When    What
 * @Description: 第二个拦截器
 */
public class SecondHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器2号执行preHandle、、、");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器2号执行postHandle、、、");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(this.getClass().getName()+"拦截器2号执行afterCompletion、、、");
    }
}
