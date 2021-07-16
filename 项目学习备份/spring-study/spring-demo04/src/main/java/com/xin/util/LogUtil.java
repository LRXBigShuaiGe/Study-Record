package com.xin.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

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

    public void myExpression(){}

    public static void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();//获取到方法传入的参数
        Signature signature = joinPoint.getSignature();//获得方法签名
        String name = signature.getName();//根据方法签名获得方法名

        System.out.println("["+name+"]执行开始，用到的参数列表为：  [ "+ Arrays.asList(args)+"]");
    }

    public static void logExpection(JoinPoint joinPoint,Exception e) {
        Object[] args = joinPoint.getArgs();//获取到方法传入的参数
        Signature signature = joinPoint.getSignature();//获得方法签名
        String name = signature.getName();//根据方法签名获得方法名

        System.out.println("方法"+name+"发生异常;"+"异常原因："+e);
        throw new RuntimeException(e);
    }

    public static void logReturn(JoinPoint joinPoint,Object result) {
        Object[] args = joinPoint.getArgs();//获取到方法传入的参数
        Signature signature = joinPoint.getSignature();//获得方法签名
        String name = signature.getName();//根据方法签名获得方法名
        System.out.println("["+name+"]执行完成，计算结果为：   "+result);
    }

    public static void logFinish() {
        System.out.println("方法最终执行完毕。。。");
    }

    //ProceedingJoinPoint继承了JpinPoint接口，也可以用于获取方法的各种详细信息
    public static Object logAround(ProceedingJoinPoint point) throws Throwable {
        //获取目标参数列表
        Object[] args = point.getArgs();
        //获取方法名字
        Signature signature = point.getSignature();
        String name = signature.getName();
        //用于接收方法执行结果
        Object result = null;
        try {
            System.out.println("【环绕】 前置通知 方法"+ name +" 执行，参数列表为：{"+Arrays.asList(args)+"}");
            result = point.proceed(args);
            System.out.println("【环绕】 返回通知 方法"+ name +"执行，结果为："+result);
        }catch (Exception e){
            System.out.println("【环绕】 方法"+ name +"出现异常 原因："+e.getCause());

        }finally {
            System.out.println("【环绕】 后置通知 方法"+ name +"执行，方法最终结束");
        }

        return result;
    }

}
