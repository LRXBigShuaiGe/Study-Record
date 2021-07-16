package com.xin.proxy;

import com.xin.impl.MyMathCalculator;
import com.xin.inter.Calculator;
import com.xin.util.LogUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.proxy
 * @Author: LI Renxin
 * @CreateTime: 2021-05-26 17:35
 * @ModificationHistory Who    When    What
 * @Description: 计算器代理对象
 */
public class CalculatorProxy {
    public static Calculator getProxy(final MyMathCalculator myCalculator){//之所以设定final关键字，是因为下面代码的匿名内部类中引用了该对象，没有final会报错
        //获取目标类（此处为计算器类）的类加载器
        ClassLoader loader = myCalculator.getClass().getClassLoader();
        //获取目标类实现的接口
        Class<?>[] interfaces = myCalculator.getClass().getInterfaces();
        //使用匿名内部类创建执行器，即方法执行器，它可以在目标方法执行的不同位置执行我们自定义的操作
        InvocationHandler h = new InvocationHandler() {
            /**
             * Object proxy, 代理对象，给jdk实验，任何时候都不要动这个对象
             * Method method, 当前将要执行的目标对象的方法
             * Object[] args 方法需要的参数
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //利用反射执行目标方法
                //目标方法执行后的返回值用result接收
                Object result = null;
                System.out.println("动态代理服务中。。。。");
                try {
                    //在方法开始之前调用logStart方法，同时传递目标方法对象method（作用是获取方法的名字）、方法传递的参数（作用是获取参数列表）
                    LogUtil.logStart(method,args);
                    //通过反射调用目标方法，获取返回信息
                    result = method.invoke(myCalculator, args);
                    //方法正常执行完成之后调用方法logReturn
                    LogUtil.logReturn(method,result);
                }catch (Exception e){
                    //方法出现异常时调用
                    LogUtil.logExpection(method,e);
                }finally {
                    //方法最终完成后调用，不管是否出现异常
                    LogUtil.logFinish();
                }

                return result;
            }
        };
        //创建代理对象，Proxy.newProxyInstance方法需要传递3个参数，目标类加载器，实现的接口，以及方法执行器
        Object proxy = Proxy.newProxyInstance(loader,interfaces,h);
        //返回代理对象
        return (Calculator) proxy;
    }
}
