package com.xin.util;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.util
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:57
 * @ModificationHistory Who    When    What
 * @Description: 日志工具类
 */
public class LogUtil {

    public static void logStart(Method method, Object[] args) {
        System.out.println("["+method.getName()+"]执行开始，用到的参数列表为：  [ "+ Arrays.asList(args)+"]");
    }

    public static void logExpection(Method method, Exception e) {
        System.out.println("方法"+method.getName()+"发生异常;"+"异常原因："+e.getCause());
    }

    public static void logReturn(Method method, Object result) {
        System.out.println("["+method.getName()+"]执行完成，计算结果为：   "+result);
    }

    public static void logFinish() {
        System.out.println("方法最终执行完毕。。。");
    }
}
