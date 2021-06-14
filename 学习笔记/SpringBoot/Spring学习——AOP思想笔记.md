# Spring学习——AOP学习笔记

## AOP思想

OOP：Object Oriented Programming 译为面向对象编程

AOP:：Aspect Oriented Programming  译为面向切面编程



## AOP使用场景

1. AOP加日志保存到数据库
2. AOP做权限认证
3. AOP做安全检查
4. AOP做事务控制

##  java动态代理案例

1. 项目目录结构如下：

   [![2UiNKx.png](https://z3.ax1x.com/2021/06/06/2UiNKx.png)](https://imgtu.com/i/2UiNKx)

2. 我们现在有一个Calculator接口实现了此接口的类MyMathCalculator，如下：

```java
package com.xin.inter;

/**
 * @Description: 计算器接口
 */
public interface Calculator {

    public int add(int i,int j);
    public int sub(int i,int j);
    public int mul(int i,int j);
    public int div(int i,int j);
}
```

```java
package com.xin.impl;

import com.xin.inter.Calculator;

/**
 * @Description: 计算器实现类
 */
public class MyMathCalculator implements Calculator {
    public int add(int i, int j) {
        int result = i + j;
        return result;
    }

    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    public int mul(int i, int j) {
        int result = i * j;
        return result;
    }

    public int div(int i, int j) {
        int result = i / j;
        return result;
    }
}
```

此时MyMathCalculator可以进行简单的加减乘除运算，但是，此时我们想要新增一个与计算器作用无关的功能，比如记录日志的功能，实现的方式有几种：

+ 直接在MyMathCalculator的方法某个位置添加代码，如：

  ```java
  public int add(int i, int j) {
          System.out.println("add方法开始,参数为：{ "+i+" "+j+" }");
          int result = i + j;
          System.out.println("add方法结束，结果result为："+result);
          return result;
  }
  ```

  这种方法直观，但是显然不够理想，因为在原有计算器类中添加了很多与计算器功能本身无关的代码

+ 写一个记录日志的类，在计算器类的方法中调用该类的方法，如

  ```java
  package com.xin.util;
  
  import java.lang.reflect.Method;
  import java.util.Arrays;
  
  /**
   * @Description: 日志工具类
   */
  public class LogUtil {
  
      public static void logStart() {
          System.out.println("方法xxx"+"执行开始，用到的参数列表为：  [ xxx ]");
      }
  
      public static void logExpection() {
          System.out.println("方法xxx"+"发生异常;"+"异常原因：xxx");
      }
  
      public static void logReturn() {
          System.out.println("方法xxx 执行完成，计算结果为： xxx  ");
      }
  
      public static void logFinish() {
          System.out.println("方法最终执行完毕。。。");
      }
  }
  
  ```

  此时只要在计算器类MyMathCalculator的方法中调用日志类的方法就可以了，如：

  ```java
  public int add(int i, int j) {
      LogUtil.logStart();
      int result = i + j;
      LogUtil.logFinish();
      return result;
  }
  ```

  此时我们简化了代码书写，做到了日志功能与计算器功能的，部分分离；

  但是，此种做法依然会添加许多不必要的代码，修改和维护都需要做额外的工作

+ 使用动态代理

  1. 把我们的计算器类MyMathCalculator还原成最初的样子，即只有纯粹的计算功能

  2. 修改我们的日志类LogUtil，改成如下：

     ```java
     package com.xin.util;
     
     import java.lang.reflect.Method;
     import java.util.Arrays;
     
     /**
      * @Description: 日志工具类
      */
     public class LogUtil {
     //method用于获取方法名称，args用于获取方法的参数列表
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
     
     ```

     

  3. 创建类CalculatorProxy，作用是获取计算器类MyCalculator的代理对象

     ```java
     package com.xin.proxy;
     
     import com.xin.impl.MyMathCalculator;
     import com.xin.inter.Calculator;
     import com.xin.util.LogUtil;
     
     import java.lang.reflect.InvocationHandler;
     import java.lang.reflect.Method;
     import java.lang.reflect.Proxy;
     /**
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
     
     ```

     

  4. 此时可以写一个测试类测试我们的代理对象

     在test包下创建一个测试类AOPTest

     [![2UiwVO.png](https://z3.ax1x.com/2021/06/06/2UiwVO.png)](https://imgtu.com/i/2UiwVO)

     ```java
     import com.xin.impl.MyMathCalculator;
     import com.xin.inter.Calculator;
     import com.xin.proxy.CalculatorProxy;
     import org.junit.Test;
     
     /**
      * @BelongsProject: spring-study
      * @BelongsPackage: PACKAGE_NAME
      * @Author: LI Renxin
      * @CreateTime: 2021-05-26 17:31
      * @ModificationHistory Who    When    What
      * @Description: aop测试类
      */
     public class AOPTest {
     
         @Test
         public void test01(){
             //使用我们写的代理类的方法获取一个代理对象
             Calculator proxy = CalculatorProxy.getProxy(new MyMathCalculator());
             proxy.add(1,2);
     
     //        proxy.div(3,2);
     //
     //        proxy.mul(3,2);
     //
     //        proxy.sub(3,2);
         }
     }
     
     ```

     结果：

     [![2UisGd.png](https://z3.ax1x.com/2021/06/06/2UisGd.png)](https://imgtu.com/i/2UisGd)

  **这样就做到了对原先目标类不做任何修改，也能够在其基础之上添加功能**

  



## Spring中使用动态代理

### 使用AOP：

1. 导包：

   [![2UigMt.png](https://z3.ax1x.com/2021/06/06/2UigMt.png)](https://imgtu.com/i/2UigMt)

   对应依赖：

   ```xml
   <!--        Spring aop依赖-->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aop</artifactId>
               <version>5.2.15.RELEASE</version>
           </dependency>
   <!--        Spring aspects依赖-->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aspects</artifactId>
               <version>5.2.15.RELEASE</version>
           </dependency>
   <!--        aspectjweaver依赖-->
           <dependency>
               <groupId>org.aspectj</groupId>
               <artifactId>aspectjweaver</artifactId>
               <version>1.9.6</version>
           </dependency>
   <!--        cglib依赖，但是Spring4.x是集成了cglib的-->
           <dependency>
               <groupId>cglib</groupId>
               <artifactId>cglib</artifactId>
               <version>3.2.4</version>
           </dependency>
   <!--        aopalliance依赖-->
           <dependency>
               <groupId>aopalliance</groupId>
               <artifactId>aopalliance</artifactId>
               <version>1.0</version>
           </dependency>
   ```

2. 编写配置信信息

   在spring配置文件中配置IOC

   目录结构：

   [![2UiTRs.png](https://z3.ax1x.com/2021/06/06/2UiTRs.png)](https://imgtu.com/i/2UiTRs)

   (1) 将目标类和切面类（封装了需要在目标类某个位置执行的方法）加入ioc容器：使用@Compent、@Service等注解以及xml配置文件

   (2) 告诉Spring那个是切面类：使用@Aspect注解

   (3) 告诉Spring，切面类中的方法，各自在何时生效：使用注解  @Before、@AfterReturning、@After、@AfterThrowing等

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
       <!-- 导入context名称空间后，配置Spring扫描的位置，此处为com.xin下的所有-->
       <context:component-scan base-package="com.xin"/>
       <aop:aspectj-autoproxy></aop:aspectj-autoproxy>
   
   </beans>
   ```

   

   ```java
   package com.xin.impl;
   
   import com.xin.inter.Calculator;
   import org.springframework.stereotype.Service;
   
   /** 目标类
    * @BelongsProject: spring-study
    * @BelongsPackage: com.xin.impl
    * @Author: LI Renxin
    * @CreateTime: 2021-05-26 17:29
    * @ModificationHistory Who    When    What
    * @Description: 计算器实现类
    */
   @Service//加入IOC容器
   public class MyMathCalculator implements Calculator {
       public int add(int i, int j) {
           int result = i + j;
           return result;
       }
   
       public int sub(int i, int j) {
           int result = i - j;
           return result;
       }
   
       public int mul(int i, int j) {
           int result = i * j;
           return result;
       }
   
       public int div(int i, int j) {
           int result = i / j;
           return result;
       }
   }
   
   ```

   

   ```java
   package com.xin.inter;
   
   /** MyCalculator实现的接口
    * @BelongsProject: spring-study
    * @BelongsPackage: com.xin.inter
    * @Author: LI Renxin
    * @CreateTime: 2021-05-26 17:26
    * @ModificationHistory Who    When    What
    * @Description: 计算器接口
    */
   public interface Calculator {
   
       public int add(int i, int j);
       public int sub(int i, int j);
       public int mul(int i, int j);
       public int div(int i, int j);
   }
   
   ```

   ```java
   package com.xin.util;
   
   import org.aspectj.lang.JoinPoint;
   import org.aspectj.lang.annotation.*;
   import org.springframework.stereotype.Component;
   
   
   /** 切面类
    * @BelongsProject: spring-study
    * @BelongsPackage: com.xin.util
    * @Author: LI Renxin
    * @CreateTime: 2021-05-26 17:57
    * @ModificationHistory Who    When    What
    * @Description: 日志工具类
    */
   @Aspect //让Spring感知到这是一个切面类
   @Component
   public class LogUtil {
   	//在目标方法执行之前执行
       @Before(value = "execution(public int com.xin.impl.MyMathCalculator.*(int, int))")
       public static void logStart(JoinPoint joinPoint) {
           System.out.println("["+"xxx"+"]执行开始，用到的参数列表为：  [ "+ "xxx"+"]");
       }
   
       //在目标方法运行出现异常时执行
       @AfterThrowing(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int))")
       public static void logExpection() {
           System.out.println("方法"+"xxx"+"发生异常;"+"异常原因："+"xxx");
       }
   	
       //在目标方法正常返回之后执行
       @AfterReturning(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))")
       public static void logReturn() {
           System.out.println("["+"xxx"+"]执行完成，计算结果为：   "+"xxx");
       }
   
       //在目标方法执行完成之后执行
       @After(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))")
       public static void logFinish() {
           System.out.println("方法最终执行完毕。。。");
       }
   }
   
   ```

   3. 编写测试类（需要导入junit单元测试的包）

   ```java
   import com.xin.inter.Calculator;
   
   import org.junit.Test;
   import org.springframework.context.ApplicationContext;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   /**
    * @BelongsProject: spring-study
    * @BelongsPackage: PACKAGE_NAME
    * @Author: LI Renxin
    * @CreateTime: 2021-05-26 17:31
    * @ModificationHistory Who    When    What
    * @Description: aop测试类
    */
   public class AOPTest {
   
       ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
   
   
       @Test
       public void test02(){
           Calculator c = ioc.getBean(Calculator.class);
           c.add(1,2);
       }
   }
   ```

   或者使用Spring的test包进行测试

   ```xml
   <!--        Spring test依赖-->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-test</artifactId>
               <version>5.2.15.RELEASE</version>
           </dependency>
   <!--        junit 单元测试依赖-->
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.12</version>
               <scope>test</scope>
           </dependency>
   ```

   测试类代码如下：

   ```java
   @RunWith(SpringJUnit4ClassRunner.class)
   @ContextConfiguration(locations = "classpath:applicationContext.xml")
   public class AOPTest {
   
       @Autowired
       Calculator calculator;
   
   
       @Test
       public void test02(){
           calculator.add(1,2);
       }
   }
   ```

   结果都是：

   ![image-20210527195754564](https://z3.ax1x.com/2021/06/06/2UijdU.png)

---



### 注意：IOC容器中保存的组件为目标组件的代理对象

创建切面类获取IOC容器对象时，如果目标类有实现了接口，类型指定为目标类所继承的接口，否则会报NoSuchBeanDefinitionException异常。

异常信息如下：

` org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'xxxx' available`



原因是：**当目标类指定了切面类时，IOC容器中保存的对象不是目标类的类型，而是一个代理对象，类型为*class com.sun.proxy.$Proxy18*，目标对象与代理对象的共同点就是实现了同一个接口，所以需要用接口类型去获取和接收容器对象**

​		当然，如果目标类没有实现任何接口，就可以通过目标类自己的类型获取对象，这个功能也需要使用加强版的aop依赖包（cglib包可以支持）

------

### 5个通知注解

#### 1. 前置通知

​	@Before：在方法开始之前

#### 2. 后置通知

​	@After：在方法结束之后

#### 3.返回通知

​	@AfterReturning：在方法正常返回之后

#### 4.异常通知

​	@AfterThrowing：在目标方法抛出异常之后

#### 5.环绕通知（重点理解）

​	@Around：环绕

​	环绕通知，顾名思义，就是围绕目标方法执行的通知，但使用方式不是简单的在方法各个切入点执行。

##### 使用环绕通知的小案例

1. 切面类中写一个标记了@Around的方法，并且写一个测试方法：

```java
//其他4种类型的通知方法如上，省略；
@Around(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))")
public static void logAround(){
    System.out.println("环绕通知。。。");
}
```

​		

```java
@Test
public void test02(){
    Calculator c = ioc.getBean(Calculator.class);
    int result = c.add(1, 2);
    System.out.println("test02 结果："+result);
}
```

2. 先运行（使用add(1,2);），查看效果

![image-20210529163622494](https://z3.ax1x.com/2021/06/06/2UFFL6.png)

​		明显报错，原因是没有返回值，那先加上返回值

```java
@Around(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))")
public static Object logAround(){
    System.out.println("环绕通知。。。");
    return 1;
}
```

​		再次测试，效果为：

[![2UFmJH.png](https://z3.ax1x.com/2021/06/06/2UFmJH.png)](https://imgtu.com/i/2UFmJH)

​	除了环绕通知里的方法执行了以外，其他通知方法都没有执行，而且计算返回的结果也不是正确结果，而是环绕通知方法里返回的结果。

**所以，环绕通知不同其他4种通知，它使用起来更复杂一点**

3. 正确的使用方法：

   修改环绕通知方法

   ```java
       @Around(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))")
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
               //执行目标方法
               result = point.proceed(args);
               System.out.println("【环绕】 返回通知 方法"+ name +"执行，结果为："+result);
           }catch (Exception e){
               System.out.println("【环绕】 方法"+ name +"出现异常 原因："+e.getCause());
           }finally {
               System.out.println("【环绕】 后置通知 方法"+ name +"执行，方法最终结束");
           }
   
           return result;
       }
   ```

此时书写格式便非常像使用java原生动态代理的书写格式，执行结果为：

![image-20210529171220545](https://z3.ax1x.com/2021/06/06/2UFQyt.png)

没有【环绕】开头打印的输出都是使用其他通知注解的方法执行结果，

从结果可以看出，此时只有在执行`result = point.proceed(args);`时，其他通知方法才会生效，执行流程如下：

> try{
>
> ​	环绕通知：前置
>
> ​	执行目标方法过程：
>
> ​	{
>
> ​				try{
>
> ​						普通前置通知
>
> ​						方法执行
>
> ​						普通返回通知
>
> ​				}catch(Exception e){
>
> ​						普通异常通知
>
> ​				}finally{
>
> ​						普通后置通知
>
> ​				}
>
> ​	}
>
> ​	环绕返回通知
>
> } catch(Exception e){
>
> ​		环绕异常通知
>
> }finally{
>
> ​		环绕后置通知
>
> }

### 切入点表达式的书写

 1. 固定格式：**execution(访问权限符 返回类型 方法全类名(参数列表))**

    示例:

    ```java
        @Before(value = "execution(public int com.xin.impl.MyMathCalculator.add(int, int))")
    ```

2. 通配符  * ..

   1) 匹配一个或者多个字符

   2) 匹配任意一个参数

   3) 匹配多个任意类型的参数

   4)匹配多层路径

   5) 不能匹配权限public等，返回值可以

   ```java
   @Before(value = "execution(public int com.xin.impl.MyMath*r.add(int, int))")//匹配类名前缀为MyMath，以r结尾的方法
   @Before(value = "execution(public int com.xin.impl.MyMathCalculator.*(int, int))")//匹配类下任意的方法
   @Before(value = "execution(public int com.xin.impl.MyMathCalculator.add(int, *))")//匹配一定形式的方法重载
   @Before(value = "execution(public int com.xin.impl.MyMathCalculator.add(..))")//匹配所有add方法重载
   @Before(value = "execution(public int com.xin.*.MyMathCalculator.add(int))")//匹配一层路径
   @Before(value = "execution(public int com..MyMathCalculator.add(int))")//匹配多层路径
   ```

---

### 抽取可重用的切入点表达式

对于以上通知注解中的切入点表达式，如果有许多重复的表达式，可以采取以下方法统一抽取出来管理，方便修改和维护

1. 在切面类中写一个空方法：

   ```java
   public void myExpression(){}
   ```

   

2. 在所写的空方法中使用@PointCut注解，注解中双引号里面的就是需要抽取的切入点表达式

   ```java
    @Pointcut("execution(public int com.xin.impl.MyMathCalculator.*(int, int))")//在双引号中写入切入点表达式
       public void myExpression(){}
   ```

   

3. 将其他通知方法value值中的切入点表达式修改为@PointCut修饰的方法名称

   ```java
    @Pointcut("execution(public int com.xin.impl.MyMathCalculator.*(int, int))")
       public void myExpression(){}
   
   @Before(value = "myExpression()")//修改后
       public static void logStart(JoinPoint joinPoint) {
           Object[] args = joinPoint.getArgs();//获取到方法传入的参数
           Signature signature = joinPoint.getSignature();//获得方法签名
           String name = signature.getName();//根据方法签名获得方法名
           System.out.println("["+name+"]执行开始，用到的参数列表为：  [ "+ Arrays.asList(args)+"]");
       }
   
       @AfterThrowing(value = "myExpression()",throwing = "e")
       public static void logExpection(JoinPoint joinPoint,Exception e) {
           Object[] args = joinPoint.getArgs();//获取到方法传入的参数
           Signature signature = joinPoint.getSignature();//获得方法签名
           String name = signature.getName();//根据方法签名获得方法名
   
           System.out.println("方法"+name+"发生异常;"+"异常原因："+e.getCause());
       }
   
       @AfterReturning(value = "myExpression())",returning = "result")
       public static void logReturn(JoinPoint joinPoint,Object result) {
           Object[] args = joinPoint.getArgs();//获取到方法传入的参数
           Signature signature = joinPoint.getSignature();//获得方法签名
           String name = signature.getName();//根据方法签名获得方法名
           System.out.println("["+name+"]执行完成，计算结果为：   "+result);
       }
   
       @After(value = "myExpression()")
       public static void logFinish() {
           System.out.println("方法最终执行完毕。。。");
       }
   ```

   



---

### JoinPoint获取方法的详细信息

1. 在通知方法的参数列表上写一个参数JoinPoint，获取方法的名字、参数等信息

   ```java
       @Before(value = "execution(public int com.xin.impl.MyMathCalculator.*(int, int))")
       public static void logStart(JoinPoint joinPoint) {
           Object[] args = joinPoint.getArgs();//获取到方法传入的参数
           Signature signature = joinPoint.getSignature();//获得方法签名
           String name = signature.getName();//根据方法签名获得方法名
   
           System.out.println("["+name+"]执行开始，用到的参数列表为：  [ "+ Arrays.asList(args)+"]");
       }
   ```

   再次测试，打印结果：

   ![image-20210527202823793](https://z3.ax1x.com/2021/06/06/2UFaSs.png)

2. 获取方法返回结果，在@AfterReturning中指定返回结果的属性名称，并且在通知方法的参数列表中加上返回结果的传参

   ```java
   @AfterReturning(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))",returning = "result")
   public static void logReturn(JoinPoint joinPoint,Object result) {
       Object[] args = joinPoint.getArgs();//获取到方法传入的参数
       Signature signature = joinPoint.getSignature();//获得方法签名
       String name = signature.getName();//根据方法签名获得方法名
       System.out.println("["+name+"]执行完成，计算结果为：   "+result);
   }
   ```

3. 获取方法异常信息，在@AfterThrowing指定异常的属性名称

   ```java
   @AfterThrowing(value = "execution(public int com.xin.impl.MyMathCalculator.*(int ,int))",throwing = "e")
   public static void logExpection(JoinPoint joinPoint,Exception e) {
       Object[] args = joinPoint.getArgs();//获取到方法传入的参数
       Signature signature = joinPoint.getSignature();//获得方法签名
       String name = signature.getName();//根据方法签名获得方法名
   
       System.out.println("方法"+name+"发生异常;"+"异常原因："+e.getCause());
   }
   ```

## 基于XML配置的AOP

基于配置的AOP同样要完成以下几个步骤，不同的是以下几个步骤都需要在xml配置文件中完成：

(1) 将目标类和切面类（封装了需要在目标类某个位置执行的方法）加入ioc容器

(2) 告诉Spring那个是切面类：

(3) 告诉Spring，切面类中的方法，各自在何时生效：

1. 将目标类和切面类（封装了需要在目标类某个位置执行的方法）加入ioc容器，注册bean

   ​	首先需要导入aop的名称空间

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
   
   <!-- 将目标类和切面类都注册到IOC容器中-->
           <bean id="myMathCalculator" class="com.xin.impl.MyMathCalculator"/>
           <bean id="logUtil" class="com.xin.util.LogUtil"/>
   </beans>
   ```

2. 告诉Spring那个是切面类：

   ```xml
       <aop:config>
           <!-- 告诉Spring那个是切面类-->
           <aop:aspect id="logUtil" ref="logUtil" order="1">
              
           </aop:aspect>
       </aop:config>
   ```

3. 告诉Spring，切面类中的方法，各自在何时生效

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
   
   
           <bean id="myMathCalculator" class="com.xin.impl.MyMathCalculator"/>
           <bean id="logUtil" class="com.xin.util.LogUtil"/>
   
       <aop:config>
           <!-- pointcut类似注解@Pointcut，提取可重用的切入点表达式 -->
           <aop:pointcut id="myPointcut" expression="execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))"/>
           <!-- id表示切面id，ref是切面类，order定义多切面时的生效顺序-->
           <aop:aspect id="logUtil" ref="logUtil" order="1">
           <!-- before/after分别对应相应的通知方法，method接收通知方法，pointcut就是切入点-->
               <aop:before method="logStart" pointcut="execution(public int com.xin.impl.MyMathCalculator.*(int ,int ))"/>
               <aop:after method="logFinish" pointcut-ref="myPointcut"/>
               <aop:after-returning method="logReturn" pointcut-ref="myPointcut" returning="result"/>
               <aop:after-throwing method="logExpection" throwing="e" pointcut-ref="myPointcut"/>
           </aop:aspect>
       </aop:config>
   
   </beans>
   ```

测试类书写是一样的，测试结果与基于注解的写法相同

---

## 声明式事务

​		声明式事务其实是编程式事务结合AOP的一种运用，使得运用事务更为简便。

### 首先，看看之前实现事务的流程

```java
try {
            //1. 关闭自动提交功能 setAutoCommit(false)
            //2. 执行若干次数据库操作 query、update、delete、add。。。。
            //3. 操作正常，则自动提交  commit
}catch (Exception e){
            //4. 出现异常，则回滚  rollback
}finally {
            //5. 无论如何关闭连接，节省资源 close....
}
```

这些就完全可以使用面向切面编程的思想，而Spring就帮我们用AOP简化了事务的操作

---

### 使用Spring事务的步骤

#### 首先，需要在项目的XML文件中进行一些配置

Spring为我们提供了事务管理器（即切面类）为目标方法运行前后进行事务控制，如果是使用原生的jdbc就使用DataSourceTransactionManager，如果使用其他数据源就找到对于的事务管理器类。

首先要配置好数据库的连接再进行下面的操作

1. 配置事务管理器

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                              http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd
                              http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
   
           <context:property-placeholder location="classpath:dbconfig.properties"></context:property-placeholder>
       <context:component-scan base-package="com.xin.*"/>
       <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
           <!-- 以下数据源配置${}引用了properties配置文件的信息，
   		可以自行配置用户名user、密码password、数据库url、驱动类全类名driverClass-->
           <property name="user" value="${jdbc.user}"></property>
           <property name="password" value="${jdbc.password}"></property>
           <property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
           <property name="driverClass" value="${jdbc.driverClass}"></property>
       </bean>
       <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
           <constructor-arg name="dataSource" ref="dataSource"/>
       </bean>
       <!-- ===================================================================================-->
       <!-- 配置事务管理器-->
       <bean id="transaction" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <!-- 配置事务管理器的数据源属性-->
           <property name="dataSource" ref="dataSource"/>
       </bean>
   </beans>
   ```

2. 开启基于注解的事务控制模式

   要使用注解的事务控制，首先需要在XML中导入tx命名空间

   ```xml
    
   xmlns:aop="http://www.springframework.org/schema/aop"
   
   <!-- 在xsi:schemaLocation=中加上以下-->
   http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
   
   ```

   然后在xml中配置：

   ```xml
   <!-- 配置的数据源的bean的id是什么，transaction-manager就填什么-->
   <tx:annotation-driven transaction-manager="transaction"/>
   ```

3. 然后，就可以在方法上使用@Transcational注解了

---

### 事务的细节（一些注解配置）

在注解@Transactional中可以配置一些属性

#### 超时timeout

超时属性timeout，int型，以秒为单位；

事务超出指定时常后自动终止并且回滚

---

#### readOnly

如果若干个sql都是查询操作，可以设置`readOnly=true`,可以加快查询速度，不需要考虑事务的操作；

但是如果sql里面有其他操作，readOnly为true的话，则会抛出异常；

---

#### noRollbackFor

运行时异常默认都回滚，但编译时异常默认不回滚

noRollbackFor 指定哪些异常不回滚，可以使一些运行时异常不回滚；

使用方式为如下：

```java
    @Transactional(timeout = 3,noRollbackFor = {ArrayIndexOutOfBoundsException.class, FileNotFoundException.class})
```

指定发生那些异常，用异常类名数组表示；

---

#### noRollbakcForClassName

与noRollbackFor使用类似，不过指定的是一个字符串数组，每个字符串是异常类的全类名，使用比较麻烦，所以通常不使用；

---

#### rollbackFor

可以指定原本不回滚的异常回滚（编译时异常）

使用方式参考noRollbackFor

---

#### isolation事务隔离级别

1. 读未提交

   READ UNCOMITTED

2. 读已提交

   READ COMMITTED

3. 可重复读

   REPEATABLE READ

4. 串行化

   SERIALIZABLE

---

### 事务的传播行为propagation

事务传播行为：当一个事务方法被另一个事务方法调用时，事务之间应如何执行。

例：事务方法A在方法中调用事务方法B时，B可以在A的事务中运行，即当A出错时B中的操作也回滚；B也可以自己另外开启一个事务自己独立运行，即在执行完B之后，如果A出错，A回滚，但B已经执行的操作不会回滚。

由此可以设置事务的传播行为：

1. **REQUIRED**（重点）

   表示当前方法必须运行在事务中，如果当前已有事务存在，则就在此事务中运行（实现方式就是与当前事务共用同一个Connection对象），否则，就开启一个新的事务（自己建立一个Connection对象）。

2. **REQURED_NEW**（重点）

   表示当前方法必须运行在自己建立的事务当中，如果当前已经有事务存在，则将当前事务挂起，自己另建一个事务，当自己的事务运行完毕后，再继续当前事务。

   ![image-20210609214819514](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20210609214819514.png)

3. SUPPORTS

   如果有事务在运行，则方法就在这个事务中运行，如果没有，就可以不在事务中运行。

4. NOT_SUPPORTED

   当前方法不应该在事务中运行，如果有运行的事务，则将事务挂起。

5. MANDATORY

   当前方法必须在事务中运行，如果没有正在运行的事务，则抛出异常。

6. NEVER

   当前方法不应该在事务中运行，如果有运行的事务，则抛出异常

7. NESTED

   如果有事务在运行，当前方法就应该在这个事务的嵌套事务中运行，否则，就启动一个新的事务，并在它自己的事务内运行。

*注意*：**REQUIRED**属性设置之后，其他的属性（如timeout）设置都失效，这些都由大事务，即上层的事务决定。

