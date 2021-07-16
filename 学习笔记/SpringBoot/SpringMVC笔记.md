# SpringMVC笔记

---

## 概述

①  Spring 为展现层提供的基于 MVC 设计理念的优秀的 Web 框架，是目前最主流的 MVC 框架之一。

②  Spring3.0 后全面超越 Struts2，成为最优秀的 MVC 框架。

③  Spring MVC 通过一套 MVC 注解，让 POJO 成为处理请求的控制器，而无须实现任何接口。

④  支持 REST 风格的 URL 请求。

⑤ 采用了松散耦合可插拔组件结构，比其他 MVC 框架更具扩展性和灵活性。



## SpringMVC依赖的jar包

```xml
<!--        Spring web依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${org.springframework.version}</version>
        </dependency>
<!--        Spring webmvc依赖 引入spring mvc-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${org.springframework.version}</version>
        </dependency>
```

## 配置SpringMVC

SpringMVC思想是有一个前端控制器拦截所有请求，并智能派发请求；

这个前端控制器是一个servlet，应该在web.xml中配置这个给servlet，让其拦截所有请求

```xml
<!--使用Spring MVC,配置DispatcherServlet是第一步。DispatcherServlet是一个Servlet,,所以可以配置多个DispatcherServlet-->
<!--DispatcherServlet是前置控制器，配置在web.xml文件中的。拦截匹配的请求，Servlet拦截匹配规则要自已定义，把拦截下来的请求，依据某某规则分发到目标Controller(我们写的Action)来处理。-->
<servlet>
    <servlet-name>DispatcherServlet</servlet-name>
    <!--在DispatcherServlet的初始化过程中，框架会在web应用的 WEB-INF文件夹下寻找名为[servlet-name]-servlet.xml 的配置文件，生成文件中定义的bean。
	比如此时叫做DispatcherServlet，那么在WEB-INF文件夹下则必须有一个DispatcherServlet-servlet.xml文件用于配置SpringMVC，但是可以在<init-param></init-param>里修改配置文件的位置和名字
	-->
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--指明了配置文件的文件名，不使用默认配置文件名，而使用dispatcher-servlet.xml配置文件。-->
    <init-param>
        <!--指定配置文件位置 -->
        <param-name>contextConfigLocation</param-name>
        <!--其中<param-value>**.xml</param-value> 这里可以使用多种写法-->
        <!--1、不写,使用默认值:/WEB-INF/<servlet-name>-servlet.xml-->
        <!--2、<param-value>/WEB-INF/classes/dispatcher-servlet.xml</param-value>-->
        <!--3、<param-value>classpath:dispatcher-servlet.xml</param-value>-->
        <!--4、多个值用逗号分隔-->
        <param-value>classpath:spring/dispatcher-servlet.xml</param-value>
    </init-param>
    <!--是启动顺序，让这个Servlet随Servletp容器一起启动。
        servlet原本是第一次访问时创建对象，设置load-on-startup可以在服务器启动时创建对象，值越小优先级越高，越先创建
    -->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
    <!--这个Servlet的名字是dispatcher，可以有多个DispatcherServlet，是通过名字来区分的。每一个DispatcherServlet有自己的WebApplicationContext上下文对象。同时保存的ServletContext中和Request对象中.-->
    <!--ApplicationContext是Spring的核心，Context我们通常解释为上下文环境，我想用“容器”来表述它更容易理解一些，ApplicationContext则是“应用的容器”了:P，Spring把Bean放在这个容器中，在需要的时候，用getBean方法取出-->
    <servlet-name>DispatcherServlet</servlet-name>
    <!-- /*和/ 都是拦截所有请求，区别在于/*的范围更大，会拦截到*.jsp这些展示页面，一旦拦截就无法显示jsp页面了
        而/ 不会拦截*.jsp,可以保证正常访问jsp页面
    -->
    <!--Servlet拦截匹配规则可以自已定义，当映射为@RequestMapping("/user/add")时，为例,拦截哪种URL合适？-->
    <!--1、拦截*.do、*.html， 例如：/user/add.do,这是最传统的方式，最简单也最实用。不会导致静态文件（jpg,js,css）被拦截。-->
    <!--2、拦截/，例如：/user/add,可以实现现在很流行的REST风格。很多互联网类型的应用很喜欢这种风格的URL。弊端：会导致静态文件（jpg,js,css）被拦截后不能正常显示。 -->
    <url-pattern>/</url-pattern> <!--会拦截URL中带“/”的请求。-->
</servlet-mapping>
```

<!--小知识：有关url-pattern>/</url-pattern>-->

```xml
<!--        
		/* 和 / 都是拦截所有请求；但是有区别
        /:会拦截所有请求，但是不会拦截*.jsp,能保证jsp访问正常
        /*: 拦截范围更大，会拦截*.jsp这些请求，一旦拦截，jsp页面就无法展示
-->
```

在springmvc的配置文件中配置项目启动扫描所有bean（按web.xml中配置的配置文件位置创建相应文件）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.xin"/>

    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

## 第一个helloworld（暂时略）

安装web项目结构，写一个controller包下写一个类

![image-20210617202022738](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210617202022738.png)





## 项目运行流程

1. 客户端在项目首页点击某个链接发送请求，比如：http://localhost:8080/springdemo/hello
2. 请求来到Tomcat服务器；
3. 我们配置的SpringMVC前端控制器org.springframework.web.servlet.DispatcherServlet会拦截所有发过来的请求；
4. 根据请求地址匹配@RequestMapping标注的类和方法，来找到使用哪个类的哪个方法来处理这个请求
5. 前端控制器找到了目标处理器类和处理方法，直接利用反射执行处理方法；
6. 方法执行完后有返回值，SpringMVC将返回值看做要去往的页面地址
7. 拿到返回值后，用视图解析器进行字符串拼接得到完整的页面地址
8. 得到页面地址，前端控制器通过请求转发转发到相应页面

---

## @RequestMapping

例：

```java
package com.xin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.controller
 * @Author: LI Renxin
 * @CreateTime: 2021-06-15 21:45
 * @ModificationHistory Who    When    What
 * @Description: 首页欢迎
 */
@Controller 
public class HelloController {

    @RequestMapping("/hello")
    public String helloRequest(){
        System.out.println("请求接收，正在处理。。。");
        return "/WEB-INF/success.jsp";
    }

    @RequestMapping("/error")
    public String errorRequest(){
        System.out.println("请求接收，正在处理。。。");
        return "/WEB-INF/error.jsp";
    }

}
```

@RequestMapping：告诉SpringMVC，这个方法用来处理什么请求，最开头的 / 可以省略

1. 标注在方法上：指定方法访问路径，即告诉SpringMVC这个方法是处理哪个请求

2. 标注在类上：

   为所有方法指定一个基准路径，即在方法上指定的路径之前拼接基准路径，类似公共前缀

### 可配置属性

#### value

访问路径，即默认配置的字符串路径

#### method

指定方法的请求类型，指定GET、POST等方式，默认所有类型都可以访问，如果不是指定的请求类型则报错405（请求方式不对）

```java
@RequestMapping(value = "/hello",method = RequestMethod.POST)
public String helloRequest(){
    System.out.println("请求接收，正在处理。。。");
    return "success";
}
```

#### params

规定请求参数

示例：

```java
/**
params指定请求需要的参数，传递一个字符串数组
"username":必须要传递名为username的参数，否则报400
"!name"：不能带有名为name的参数，否则报400
"num=123"：必须传递参数num，且num必须等于123
"num2!=123"：如果带有名为num2的参数，则一定不能等于123
*/
@RequestMapping(value = "/hello2",method = RequestMethod.GET,params = {"username","!name","num=123","num2!=123"})
public String helloRequest2(){
    System.out.println("请求接收，正在处理。。。");
    return "success";
}
```

#### headers

规定请求头

例：指定只有火狐浏览器可以访问，而谷歌不能访问

```java
//火狐的 User-Agent：Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0
//谷歌的 User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36
@RequestMapping(value = "/hello3",method = RequestMethod.GET,
        headers = "User-Agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:89.0) Gecko/20100101 Firefox/89.0")
//指定只有火狐浏览器可以访问
public String helloRequest3(){
    System.out.println("请求接收，正在处理。。。");
    return "success";
}
```

headers可以指定请求头信息，如果不符合规定则不允许访问

#### consumes

只接受内容类型是哪种请求，规定请求头中的Content-Type

#### provides

告诉浏览器返回的内容类型是什么，给响应头中加上Content-Type

---

### ant风格的url

URL地址可以写一些模糊的通配符

? : 替代任意一个字符

\* : 替代任意多个字符，和一层路径

** ： 替代多层路径

```java
/**
 * ?只匹配一个字符，0个或多个都不行
 *        当访问路径对应多个方法时，选择最精确的进行访问
 * @return
 */
@RequestMapping(value = "/hello1?",method = RequestMethod.GET)
public String helloRequest4(){
    System.out.println("请求接收    public String helloRequest4()，正在处理。。。");
    return "success";
}
/**
 *  * 匹配多个字符或一层路径
 * @return
 */
@RequestMapping(value = "/hello1*",method = RequestMethod.GET)
public String helloRequest5(){
    System.out.println("请求接收    public String helloRequest5()，正在处理。。。");
    return "success";
}
@RequestMapping(value = "/hello6/*/haha",method = RequestMethod.GET)
public String helloRequest6(){
    System.out.println("请求接收    public String helloRequest6()，正在处理。。。");
    return "success";
}

/**
 *       ** 匹配多层路径
 * @return
 */
@RequestMapping(value = "/hello1/**/haha",method = RequestMethod.GET)
public String helloRequest7(){
    System.out.println("请求接收    public String helloRequest7()，正在处理。。。");
    return "success";
}
```

---

## @PathVariable

与@RequestMapping结合使用，@RequestMapping在路径中使用占位符，@PathVariable可以获取占位符的值

```java
/**
 * 占位符的使用，在路径的某一层使用   {变量名}
 * 占位符只能占用一层路径
 * @param user
 * @return
 */
@RequestMapping(value = "/hello8/{username}")
public String helloRequest8(@PathVariable("username") String user){
    System.out.println("请求接受到的username："+user);
    return "success";
}
```

---

## REST风格的URL

### 概述

REST即表述性状态传递（英文：Representational State Transfer，简称REST）是Roy Fielding博士在2000年他的博士论文中提出来的一种软件架构风格。

它是一种针对网络应用的设计和开发方式，可以降低开发的复杂性，提高系统的可伸缩性。

目前在三种主流的Web服务实现方案中，因为REST模式的Web服务与复杂的SOAP和XML-RPC对比来讲明显的更加简洁，越来越多的web服务开始采用REST风格设计和实现。

**例如，Amazon.com提供接近REST风格的Web服务进行图书查找；雅虎提供的Web服务也是REST风格的。**

1. REST：即 Representational State Transfer。***\*（资源）表现层状态转化。是目前最流行的一种互联网软件架构\****。它结构清晰、符合标准、易于理解、扩展方便，所以正得到越来越多网站的采用

   · **资源（Resources）**：网络上的一个实体，或者说是网络上的一个具体信息。

   它可以是一段文本、一张图片、一首歌曲、一种服务，总之就是一个具体的存在。

   可以用一个URI（统一资源定位符）指向它，每种资源对应一个特定的 URI 。

   获取这个资源，访问它的URI就可以，因此 URI 即为每一个资源的独一无二的识别符。

   · **表现层（Representation）**：把资源具体呈现出来的形式，叫做它的表现层（Representation）。比如，文本可以用 txt 格式表现，也可以用 HTML 格式、XML 格式、JSON 格式表现，甚至可以采用二进制格式。

   · **状态转化（State Transfer）**：每发出一个请求，就代表了客户端和服务器的一次交互过程。HTTP协议，是一个无状态协议，即所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生“状态转化”（State Transfer）。

   而这种转化是建立在表现层之上的，所以就是 “表现层状态转化”。

   · **具体说，就是 HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE。**

   **它们分别对应四种基本操作：GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源。**

2. URL风格

   示例：

   /order/1 HTTP ***\*GET\**** ：得到 id = 1 的 order  

   /order/1 HTTP ***\*DELETE：\****删除 id = 1的 order  

   /order/1 HTTP ***\*PUT\****：更新id = 1的 order  

   /order   HTTP ***\*POST\****：新增 order 

3. HiddenHttpMethodFilter

   浏览器 form 表单只支持 GET 与 POST 请求，而DELETE、PUT 等 method 并不支持，

   Spring3.0 添加了一个过滤器，可以将这些请求转换为标准的 http 方法，使得支持 GET、POST、PUT 与 DELETE 请求。

   ##  ***\*参考资料：\**** 

   1. 理解本真的REST架构风格: http://kb.cnblogs.com/page/186516/ 

   2. 深入浅出REST: http://www.infoq.com/cn/articles/rest-introduction





---

## @RequestParam

指定获取请求参数

当外界传参时，可以用两种方法接收；

1. 方法参数名与传过来的参数名称相同，比如外界传递一个叫username的参数接收，方法的参数列表可以定义一个叫做username的参数接收。

2. 当方法定义参数与实际参数不同时，可以用@RequestParam指定接收的参数

   ```java
   /**
    * @RequestParam可以指定参数名称，必然指定传入参数名称为username，并以自己定义的变量name接收
    * @RequesesParam默认必须传入，但可以设置required属性设置是否必须
    * 当没有传入指定参数时，则使用defaultvalue设置的默认值
    * @param name
    * @return
    */
   @RequestMapping(value = "/hello9")
   public String helloRequest9(@RequestParam(value = "username",required = false,defaultValue = "小明") String name){
       System.out.println("获取到的名字name："+name);
       return "success";
   }
   ```

---

## @RequestHeader

@RequestHeader用于获取请求头中的值

```java
/**
 * @RequestHeader用于获取请求头中的值，比如获取请求头中的浏览器信息User-Agent，接受的编码方式accept-encoding
 * @RequesesHeaderr默认必须传入，但可以设置required属性设置是否必须
 * 当没有传入指定参数时，则使用defaultvalue设置的默认值
 *
 * @param useragent
 * @return
 */
@RequestMapping(value = "/bye01")
public String byeRequest01(@RequestHeader(value = "User-Agent",required = false,defaultValue = "hahah")String useragent,
                           @RequestHeader(value = "accept-encoding",required = false,defaultValue = "hahah")String encoding){
    System.out.println("获取到的名字name："+useragent);
    System.out.println("获取到的名字name："+encoding);
    return "success";
}
```

访问之后：

![image-20210619175242893](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210619175242893.png)

---

## @CookieValue

@CookieValue用于获取请求头中的Cookie信息，通过名称获取指定cookie

```java
/**
 * @CookieValue用于获取Cookie信息
 * @CookieValue默认必须传入，但可以设置required属性设置是否必须
 * 当没有传入指定参数时，则使用defaultvalue设置的默认值
 *
 * @param
 * @return
 */
@RequestMapping(value = "/bye02")
public String byeRequest02(@CookieValue(value = "JSESSIONID",required = false,defaultValue = "123456789")String ss){
    System.out.println("获取到的JESSIONID："+ss);
    return "success";
}
```

---

## SpringMVC自动封装POJO

SpringMVC可以接受一个对象，当指定方法中接收参数为一个对象时，会自动在请求中寻找与对象属性名称相同的参数进行匹配赋值，没有匹配的参数则设置默认值。

```java
package com.xin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @BelongsProject: spring-study
 * @BelongsPackage: com.xin.bean
 * @Author: LI Renxin
 * @CreateTime: 2021-06-19 19:59
 * @ModificationHistory Who    When    What
 * @Description: 用户类
 */
@Data  //自动设置getter、setter、equals、hashCode、canEqual、toString方法
@NoArgsConstructor  //无参构造方法
@AllArgsConstructor  //包含所有属性的有参构造
public class User {
    private String name;
    private String username;
    private String password;
    private int age;
    private float record;
}
```

```java
/**
     * User类为接受对象，自动匹配请求传过来的参数
     * 比如用URL：http://localhost:8080/springdemo/good01?name=%E5%B0%8F%E7%8E%8B&username=myheader&password=123456ad&age=18&record=19.9 访问
     * 则相关参数会自动匹配User中的属性
     * @param user
     * @return
     */
@RequestMapping("/good01")
public String helloRequest01(User user){
    System.out.println(user.toString());
    return "success";
}
```

结果：

![img](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210619201753339.png)

SpringMVC还可以为级联属性赋值，即为属性的属性赋值

增加一个Car类，并在User类中增加一个类型为Car的属性

```java
package com.xin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * @Description: 车
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String type;
    private String carName;
    private String name;
}
```

```java
package com.xin.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: 用户类
 */
@Data  //自动设置getter、setter、equals、hashCode、canEqual、toString方法
@NoArgsConstructor  //无参构造方法
@AllArgsConstructor  //包含所有属性的有参构造
public class User {
    private String name;
    private String username;
    private String password;
    private int age;
    private float record;
}
```

使用URL访问，传递响应参数

```java
/**
 * User类为接受对象，自动匹配请求传过来的参数
 * 比如用URL访问
 * http://localhost:8080/springdemo/good01?name=%E5%B0%8F%E7%8E%8B&username=myheader&password=123456ad&age=18&record=19.9&car.type=qq&car.carName=小马&car.name=赫赫
 * 则相关参数会自动匹配User中的属性,car.xxxx则会匹配user属性car内部自己的属性，即级联属性
 * @param user
 * @return
 */
@RequestMapping("/good01")
public String helloRequest01(User user){
    System.out.println(user.toString());
    return "success";
}
```

结果：

![img](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210619203858697.png)

---

## SpringMVC使用原生API

SpringMVC支持使用部分原生API，即支持使用原生javaWeb中使用的HttpServletRequest等，前提是导入servlet-api的jar包

支持的API：

| 可以使用的原生API       |                                                              |
| ----------------------- | ------------------------------------------------------------ |
| HttpServletRequest      |                                                              |
| HttpServletResponse     |                                                              |
| HttpSession             |                                                              |
| java.security.Principal | 与http安全协议有关的API                                      |
| Locale                  | 与国际化有关的区域信息                                       |
| InputStream             | ServletInputStream inputStream = request.getInputStream();   |
| OutputStream            | ServletOutputStream outputStream = response.getOutputStream(); |
| Reader                  | BufferedReader reader = request.getReader();                 |
| Writer                  | PrintWriter writer = response.getWriter();                   |

```java
/**
 * 使用servlet原生API不需要传参，会自动传递
 * 可以直接使用
 * @param request
 * @param response
 * @param session
 * @return
 * @throws IOException
 */
@RequestMapping("/good02")
public String helloRequest02(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    ServletOutputStream outputStream = response.getOutputStream();
    BufferedReader reader = request.getReader();
    PrintWriter writer = response.getWriter();
    Locale locale = response.getLocale();
    System.out.println();
    return "success";
}
```

---

## SpringMVC提供了CharacterEncodingFilter过滤器解决乱码

为了解决乱码问题，SpringMVC有一个过滤器叫做CharacterEncodingFilter，可以在web.xml中配置

```xml
<!-- 
 这个过滤器就是针对于每次浏览器请求进行过滤的，然后再其之上添加了父类没有的功能即处理字符编码。
它有两个参数：
encoding:指定解决POST请求乱码
forceEncoding:顺手解决响应乱码 
-->
<filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
        <param-name>forceEncoding</param-name>
        <!--设置为true，则允许解决响应乱码-->
        <param-value>true</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

注意：要解决get请求乱码，需要在Tomcat配置文件server.xml的8080处添加URIEncoding="UTF-8"

![image-20210619211527932](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210619211527932.png)

另外，字符编码的filter需要在最先配置，即在其他过滤器之前配置，因为字符编码需要在第一次获取参数之前过滤，否则没有意义。

---

 ## SpringMVC向页面输出数据

### 使用Model、Map、ModelMap

在方法接受的参数列表中指定Model、Map、ModelMap中类型的任意一个，同向对象中添加数据，就可以向request中写入数据了；

```java
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import java.util.Map;
//注意导包
//=========================================
/**
* 在参数列表中指定Model、Map、ModelMap，再放入数据就可以向页面输出数据了
*/
@RequestMapping("/good03")
public String helloRequest03(Model model){
    model.addAttribute("username","星星");
    System.out.println(model);
    return "success";
}

@RequestMapping("/good04")
public String helloRequest04(Map model){
    model.put("username","月亮");
    System.out.println(model);
    return "success";
}

@RequestMapping("/good05")
public String helloRequest05(ModelMap model){
    model.addAttribute("username","太阳");
    System.out.println(model);
    return "success";
}
```

```jsp
<%--success.jsp页面展示数据--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
    <h1>登录成功！！！</h1>
    pageContext: ${pageScope.username}<br>
    request: ${requestScope.username}<br>
    session: ${sessionScope.username}<br>
    application: ${applicationScope.username}<br>
</body>
</html>
```

结果都成功向request输出了数据：

![image-20210620104153024](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210620104153024.png)

无论指定Model、Map、ModelMap中哪一个，SpringMVC最终都会装配同一个对象`class org.springframework.validation.support.BindingAwareModelMap`，BindingAwareModelMap 中保存的东西都会被保存的请求域中。

它们三者之间的关系如下:

![image-20210620104807223](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210620104807223.png)

### 返回ModelAndView对象

可以指定方法返回类型ModelAndView，ModelAndView对象中保存的数据会放在request域中：

```java
 @RequestMapping("/good06")
    public ModelAndView helloRequest06(){
        //可以在构造方法中指定viewname：跳转到的页面，也可以使用setViewName()方法指定，此次跳转到success.jsp页面
//        ModelAndView modelAndView = new ModelAndView("success");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        //使用addObject()方法添加数据，返回后会将数据保存到request域中
        modelAndView.addObject("username","大海sea");
        System.out.println(modelAndView);
        return modelAndView;
    }
```

### @SessionAttributes向session域中添加数据

```java
package com.xin.controller;

import com.xin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.Map;


/**
 * @SessionAttributes可以指定哪些数据需要复制一份到session中
 */
//@SessionAttributes("username")      指定保存key为username的数据
@SessionAttributes(value = {"username","password"}) //保存多个数据
//@SessionAttributes(types = String.class)  指定类型为String的数据进行保存
//@SessionAttributes(types = {String.class,Integer.class})  指定多种类型的数据进行保存
@Controller
public class Hello2Controller {
    @RequestMapping("/good03")
    public String helloRequest03(Model model){
        model.addAttribute("username","星星");
        System.out.println(model);
        return "success";
    }

    @RequestMapping("/good04")
    public String helloRequest04(Map model){
        model.put("username","月亮");
        System.out.println(model);
        return "success";
    }

    @RequestMapping("/good05")
    public String helloRequest05(ModelMap model){
        model.addAttribute("username","太阳");
        System.out.println(model.getClass());
        return "success";
    }

    @RequestMapping("/good06")
    public ModelAndView helloRequest06(){
        //可以在构造方法中指定viewname：跳转到的页面，也可以使用setViewName()方法指定，此次跳转到success.jsp页面
//        ModelAndView modelAndView = new ModelAndView("success");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        //使用addObject()方法添加数据，返回后会将数据保存到request域中
        modelAndView.addObject("username","大海sea");
        System.out.println(modelAndView);
        return modelAndView;
    }

}
```

访问 /good06 的结果：

![image-20210620110511038](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210620110511038.png)

==注意：不推荐使用该注解，有可能引发异常，还是推荐使用原生的API：HttpSession等==

---

### @ModelAttribute

略，现在基本不用

---

## 原理分析

### 前端控制器DispatcherServlet的结构

1. 继承关系：

![image-20210622092915343](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210622092915343.png)

可以看出DispatcherServlet的确是一个Servlet，因为最终也继承了HttpServlet

2. 既然继承了HttpServlet，就要寻找到实现了doGet、doPost方法的地方

先看直接继承了HttpServlet的HttpServletBean，它是一个抽象类，发现里面并没有实现doGet、doPost方法

![image-20210622093742306](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210622093742306.png)

再看继承了HttpServletBean的FrameworkServlet，发现里面实现了doGet、doPost等方法：

```java
/**
 * Delegate GET requests to processRequest/doService.
 * <p>Will also be invoked by HttpServlet's default implementation of {@code doHead},
 * with a {@code NoBodyResponse} that just captures the content length.
 * @see #doService
 * @see #doHead
 */
@Override
protected final void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
	//发现所有doxxx都是调用了processRequest(request, response);
   processRequest(request, response);
}

/**
 * Delegate POST requests to {@link #processRequest}.
 * @see #doService
 */
@Override
protected final void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

   processRequest(request, response);
}

//其他方法略......................
```

发现所有的doXxxx方法都是调用了processRequest(request, response);这个方法来处理，processRequest(request, response)就在FrameworkServlet，直接查看源码

再看看

```java
/**
 * Process this request, publishing an event regardless of the outcome.
 * <p>The actual event handling is performed by the abstract
 * {@link #doService} template method.
 * 译：处理此请求，无论结果如何都发布事件。
 * <p>实际的事件处理由抽象的 
 * {@link #doService} 模板方法执行。
 */
protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

   long startTime = System.currentTimeMillis();
   Throwable failureCause = null;
	//与国际化有关的代码，不是重点
   LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
   LocaleContext localeContext = buildLocaleContext(request);
	
   RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
   ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);

   WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
   asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());

   initContextHolders(request, localeContext, requestAttributes);
	//前面的代码都不是核心，在try代码块中的doService(request, response)才是核心
   try {
       //调用了doService()方法，但是FrameworkServlet中的doService()方法是一个抽象方法
      doService(request, response);
   }
   catch (ServletException | IOException ex) {
      failureCause = ex;
      throw ex;
   }
   catch (Throwable ex) {
      failureCause = ex;
      throw new NestedServletException("Request processing failed", ex);
   }
   finally {
      resetContextHolders(request, previousLocaleContext, previousAttributes);
      if (requestAttributes != null) {
         requestAttributes.requestCompleted();
      }
      logResult(request, response, failureCause, asyncManager);
      publishRequestHandledEvent(request, response, startTime, failureCause);
   }
}
```

继续发现processRequest()中核心是调用了 doService(request, response)，但是在FrameworkServlet中doService是一个抽象方法，所以需要去继承了FrameworkServlet的DispatcherServlet中寻找doService

```java
/**
DispatcherServlet中的doService方法
 * Exposes the DispatcherServlet-specific request attributes and delegates to {@link #doDispatch}
 * for the actual dispatching.
 */
@Override
protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
   logRequest(request);

   // Keep a snapshot of the request attributes in case of an include,
   // to be able to restore the original attributes after the include.
    //一些域初始化操作，也不是核心
   Map<String, Object> attributesSnapshot = null;
   if (WebUtils.isIncludeRequest(request)) {
      attributesSnapshot = new HashMap<>();
      Enumeration<?> attrNames = request.getAttributeNames();
      while (attrNames.hasMoreElements()) {
         String attrName = (String) attrNames.nextElement();
         if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
            attributesSnapshot.put(attrName, request.getAttribute(attrName));
         }
      }
   }

   // Make framework objects available to handlers and view objects.
    //往请求域中写入了一些数据，不是核心
   request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
   request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
   request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
   request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());

   if (this.flashMapManager != null) {
      FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
      if (inputFlashMap != null) {
         request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
      }
      request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
      request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
   }
	
   try {
       //最终又调用了一个doDispatch()方法
      doDispatch(request, response);
   }
   finally {
      if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
         // Restore the original attribute snapshot, in case of an include.
         if (attributesSnapshot != null) {
            restoreAttributesAfterInclude(request, attributesSnapshot);
         }
      }
   }
}
```

发现核心是调用了另一个doDispatch()方法，这就是理解原理的入口，最终的结构关系如下：

![image-20210622100914163](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210622100914163.png)

---

### doDispatch方法详细内容

```java
/**
 * Process the actual dispatching to the handler.
 * <p>The handler will be obtained by applying the servlet's HandlerMappings in order.
 * The HandlerAdapter will be obtained by querying the servlet's installed HandlerAdapters
 * to find the first that supports the handler class.
 * <p>All HTTP methods are handled by this method. It's up to HandlerAdapters or handlers
 * themselves to decide which methods are acceptable.
 * @param request current HTTP request
 * @param response current HTTP response
 * @throws Exception in case of any kind of processing failure
 */
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
   HttpServletRequest processedRequest = request;
   HandlerExecutionChain mappedHandler = null;
   boolean multipartRequestParsed = false;
	//异步管理器，如果是异步请求就需要使用
   WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);

   try {
      ModelAndView mv = null;
      Exception dispatchException = null;

      try {
          //1.检查是否是文件上传请求
         processedRequest = checkMultipart(request);
         multipartRequestParsed = (processedRequest != request);

         // Determine handler for the current request.译：确定当前请求的处理程序。
          //2. 根据当前的请求地址找到哪个类能来处理这个请求
         mappedHandler = getHandler(processedRequest);
          //3. 如果没有找到处理器类能够处理这个请求，就404抛出异常
         if (mappedHandler == null) {
            noHandlerFound(processedRequest, response);
            return;
         }

         // Determine handler adapter for the current request.译：确定当前请求的处理程序适配器。
          //4. 拿到能够执行这个类的所有方法的适配器（一个反射工具）
         HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());

         // Process last-modified header, if supported by the handler.
         String method = request.getMethod();
         boolean isGet = "GET".equals(method);
         if (isGet || "HEAD".equals(method)) {
            long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
            if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
               return;
            }
         }

         if (!mappedHandler.applyPreHandle(processedRequest, response)) {
            return;
         }

         // Actually invoke the handler.实际调用处理程序
          //mv是开头定义的ModelAndView
          //5. 适配器ha执行目标方法；并将目标方法执行后的返回值作为视图名，设置保存到ModelAndView中
          //目标方法无论怎么写，最终适配器执行完成以后都会将执行后的信息封装成一个ModelAndView对象
         mv = ha.handle(processedRequest, response, mappedHandler.getHandler());

         if (asyncManager.isConcurrentHandlingStarted()) {
            return;
         }

         applyDefaultViewName(processedRequest, mv);//如果定义的方法没有返回值，即没有设置视图，则使用默认的
         mappedHandler.applyPostHandle(processedRequest, response, mv);
      }
      catch (Exception ex) {
         dispatchException = ex;
      }
      catch (Throwable err) {
         // As of 4.3, we're processing Errors thrown from handler methods as well,
         // making them available for @ExceptionHandler methods and other scenarios.
         dispatchException = new NestedServletException("Handler dispatch failed", err);
      }
       //通过其传入的参数response、mv等可以看出，
       // processDispatchResult就是转发到目标页面,
       //5.根据目标方法的最终执行完成后封装返回的ModelAndView，转发到相应的页面，同时保存在ModelAndView中的数据也可以从request域中获取
      processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
   }
   catch (Exception ex) {
      triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
   }
   catch (Throwable err) {
      triggerAfterCompletion(processedRequest, response, mappedHandler,
            new NestedServletException("Handler processing failed", err));
   }
   finally {
      if (asyncManager.isConcurrentHandlingStarted()) {
         // Instead of postHandle and afterCompletion
         if (mappedHandler != null) {
            mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
         }
      }
      else {
         // Clean up any resources used by a multipart request.
         if (multipartRequestParsed) {
            cleanupMultipart(processedRequest);
         }
      }
   }
}
```

所以可以总结执行流程如下：

1. 请求通过浏览器发送到服务器，然后被DispatcherServlet接收

2. DispatcherServlet通过调用doDispatch()对请求进行处理：

   在doDispatch中做了以下一些主要步骤：

   1. getHandler()：根据当前的请求地址找到能处理这个请求的目标处理器类` HandlerExecutionChain mappedHandler`（处理器）
   
      ​		根据当前请求在HandlerMapping中找到这个请求的映射信息，获取到目标处理器类
   
   2. getHandlerAdapter():根据当前处理器类获取到能够执行这个处理器方法的适配器`HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());`
   
      ​		根据当前处理器类，找到当前类的HandlerMappingAdapter适配器；
   
   3. 使用适配器 ha 执行目标方法，执行完后会返回一个ModelAndView对象
   
   4. 根据ModelAndView的信息转发到具体的页面，并可以在在请求域中取出ModleAndView中的数据

---

### getHandler()细节

getHandler()会返回目标处理器类的执行链HandlerExecutionChain

```java
/**
 * Return the HandlerExecutionChain for this request.
 * <p>Tries all handler mappings in order.
 译：返回此请求的 HandlerExecutionChain。 
 * <p>按顺序尝试所有处理程序映射。 

 * @param request 当前的request请求里面存有请求的地址信息
 * @return the HandlerExecutionChain, or {@code null} if no handler could be found
 */
@Nullable
protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
    //handlerMapping中保存了每一个处理器能够处理哪些请求的映射信息
   if (this.handlerMappings != null) {
       //循环遍历handlerMappings，直到找到合适的执行链返回
      for (HandlerMapping mapping : this.handlerMappings) {
         HandlerExecutionChain handler = mapping.getHandler(request);
         if (handler != null) {
            return handler;
         }
      }
   }
   return null;
}
```

handlerMappings中的信息

![image-20210622115806090](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210622115806090.png)

其中RequestMappingHandlerMapping才是我们需要使用的执行链，

每次启动项目时，IOC容器会扫描标有@Controller的类和@RequestMapping的方法，就会将这些映射信息记录到handlerMappings中

---

### getHandlerAdapter()细节

```java
/**
 * Return the HandlerAdapter for this handler object.
 * @param handler the handler object to find an adapter for
 * @throws ServletException if no HandlerAdapter can be found for the handler. This is a fatal error.
 */
protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
   if (this.handlerAdapters != null) {
      for (HandlerAdapter adapter : this.handlerAdapters) {
         if (adapter.supports(handler)) {
            return adapter;
         }
      }
   }
   throw new ServletException("No adapter for handler [" + handler +
         "]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
}
```



---

### 九大组件

DispatcherServlet中有几个引用类型的属性，称为SpringMVC的九大组件；

SpringMVC在工作的时候，关键位置都是由这些组件完成的；

共同点：九大组件都是接口，而接口就是规范，提供了非常强大的扩展性

```java
/** 文件上传解析器 */
@Nullable
private MultipartResolver multipartResolver;

/** 区域信息解析器，和国际化有关 */
@Nullable
private LocaleResolver localeResolver;

/** 主题解析器，提供强大的主题效果更换功能（不常用，因为自己写两套主题系统可能还更方便）*/
@Nullable
private ThemeResolver themeResolver;

/** Handler映射信息 */
@Nullable
private List<HandlerMapping> handlerMappings;

/** Hanlder的适配器*/
@Nullable
private List<HandlerAdapter> handlerAdapters;

/** 异常处理解析器，提供了强大的异常解析功能 */
@Nullable
private List<HandlerExceptionResolver> handlerExceptionResolvers;

/** 如果方法没有返回值，就将请求地址转换成视图名 */
@Nullable
private RequestToViewNameTranslator viewNameTranslator;

/** SpringMVC中允许重定向携带数据的功能*/
@Nullable
private FlashMapManager flashMapManager;

/** 视图解析器 */
@Nullable
private List<ViewResolver> viewResolvers;
```

九大组件初始化的地方是在DispatcherServlet的initStrategies方法中：

```java
/**
 * Initialize the strategy objects that this servlet uses.
 * <p>May be overridden in subclasses in order to initialize further strategy objects.
 */
protected void initStrategies(ApplicationContext context) {
   initMultipartResolver(context);
   initLocaleResolver(context);
   initThemeResolver(context);
   initHandlerMappings(context);
   initHandlerAdapters(context);
   initHandlerExceptionResolvers(context);
   initRequestToViewNameTranslator(context);
   initViewResolvers(context);
   initFlashMapManager(context);
}
```

看其中初始化HandlerMappings的方法initHandlerMappings(context) ：

```java
private void initHandlerMappings(ApplicationContext context) {
   this.handlerMappings = null;

   if (this.detectAllHandlerMappings) {
      // 如果设置了detectAllHandlerMappings属性为true（默认为true），可以在web.xml中设置这个属性
       //就先在bean工厂找到容器中所有的HandlerMapping
      Map<String, HandlerMapping> matchingBeans =
            BeanFactoryUtils.beansOfTypeIncludingAncestors(context, HandlerMapping.class, true, false);
      if (!matchingBeans.isEmpty()) {
         this.handlerMappings = new ArrayList<>(matchingBeans.values());
         // We keep HandlerMappings in sorted order.
         AnnotationAwareOrderComparator.sort(this.handlerMappings);
      }
   }
   else {
       //如果detectAllHandlerMappings为false，就根据HANDLER_MAPPING_BEAN_NAME这个id来寻找这个HandlerMapping
      try {
         HandlerMapping hm = context.getBean(HANDLER_MAPPING_BEAN_NAME, HandlerMapping.class);
         this.handlerMappings = Collections.singletonList(hm);
      }
      catch (NoSuchBeanDefinitionException ex) {
         // Ignore, we'll add a default HandlerMapping later.
      }
   }

   // Ensure we have at least one HandlerMapping, by registering
   // a default HandlerMapping if no other mappings are found.
   if (this.handlerMappings == null) {
       //如果最终都没有找到HandlerMapping，就通过 getDefaultStrategies(context, HandlerMapping.class)方法使用默认的
      this.handlerMappings = getDefaultStrategies(context, HandlerMapping.class);
      if (logger.isTraceEnabled()) {
         logger.trace("No HandlerMappings declared for servlet '" + getServletName() +
               "': using default strategies from DispatcherServlet.properties");
      }
   }
}
```

​	再看看使用默认的配置，getDefaultStrategies(context, HandlerMapping.class);的细节：

​		![getDefaultStrategies()](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210710162746968.png)

​	看defaultStrategies初始化的位置：

​			![image-20210710163126139](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210710163126139.png)

![配置文件名称](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210710163219721.png)

找到这个配置文件：

![image-20210710163317219](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210710163317219.png)

DispatcherServlet.properties:

```properties
# Default implementation classes for DispatcherServlet's strategy interfaces.
# Used as fallback when no matching beans are found in the DispatcherServlet context.
# Not meant to be customized by application developers.

org.springframework.web.servlet.LocaleResolver=org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver

org.springframework.web.servlet.ThemeResolver=org.springframework.web.servlet.theme.FixedThemeResolver
#这些就加载的默认配置
org.springframework.web.servlet.HandlerMapping=org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,\
   org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,\
   org.springframework.web.servlet.function.support.RouterFunctionMapping

org.springframework.web.servlet.HandlerAdapter=org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,\
   org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,\
   org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,\
   org.springframework.web.servlet.function.support.HandlerFunctionAdapter


org.springframework.web.servlet.HandlerExceptionResolver=org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver,\
   org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver,\
   org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver

org.springframework.web.servlet.RequestToViewNameTranslator=org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator

org.springframework.web.servlet.ViewResolver=org.springframework.web.servlet.view.InternalResourceViewResolver

org.springframework.web.servlet.FlashMapManager=org.springframework.web.servlet.support.SessionFlashMapManager
```

组件的初始化基本都是这个流程：

​	去容器中找这个组件，如果没有找到就使用默认配置

​	但是有些组件是根据id找，有些则使用类型来找

---

### 锁定目标方法的执行

---

```java
// Actually invoke the handler.
mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
```

这段代码是真正执行目标方法的地方，需要深入了解；

debug进入之后发现在`mav = invokeHandlerMethod(request, response, handlerMethod);`这段代码处执行了目标方法

### Spring4和5有差异，以后再补上

---





## forward前缀指定转发页面

当在配置DispatcherServlet时指定了前缀和后缀，如：

```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <!-- 指定前缀/WEB-INF/pages/-->
    <property name="prefix" value="/WEB-INF/pages/"/>
    <!--指定后缀.jsp-->
    <property name="suffix" value=".jsp"/>
</bean>
```

此时如果方法返回字符串如 “hello”，则会自动拼接成/WEB-INF/pages/hellp.jsp，于是spring就会返回/WEB-INF/pages/下的页面，但是如果此时我们想返回自定义位置下的页面，比如直接返回类路径下的hello.jsp，可以使用两种方式

![image-20210711155415855](https://gitee.com/LiRenxin/study-images/raw/master/img/image-20210711155415855.png)

1. 使用相对路径：

   ```java
   @RequestMapping("/hello01")
   public String hello01(){
       //利用../从/WEB-INF/pages/回退两级目录，指定类路径webapp下的hello.jsp
       return "../../hello";
   }
   ```

2. 使用forward定义绝对路径：

   ```java
   @RequestMapping("/hello02")
   public String hello02(){
       //使用forward:/   从类路径开始寻找指定页面hello.jsp
       return "forward:/hello.jsp";
   }
   ```

   forward还可以使用请求转发：

   ```java
   @RequestMapping("/hello03")
   public String hello03(){
       System.out.println("/h3/hello03");
       //当/h3/hello01  没有.jsp后缀时，就会当作方法路径转发到路径指定的方法
       return "forward:/h3/hello01";
   }
   ```

   ​	forward不会让配置的视图解析器进行拼接字符串，但是../../还是使用了拼串

## redirect重定向

1.带后缀.jsp等等则是重定向到页面

```java
@RequestMapping("/hello04")
public String hello04(){
    //转发到类路径下的hello.jsp
    return "redirect:/hello.jsp";
}
```

2.不带后缀则是重定向到新的方法进行处理

```java
@RequestMapping("/hello05")
public String hello05(){
    //转发到/hello请求
    return "redirect:/hello";
}
```

---

## 数据绑定和数据校验

### 概念

​	请求中传过来的参数都是一行字符串，如name=12344&pass=123456&age=13;

​	就需要有数据类型转换机制将字符串转换成我们需要的类型，如int、float、自定义的类等等；

​	而且如果传过来的数据是2021/3/21而需要的格式是2021-3-21，就需要进行数据格式化；

​	如果要接收邮箱地址，则还需要进行数据校验，前端虽然用js进行校验，但是浏览器可以关闭js的功能，因此后端依旧需要进行校验。

### 自定义类型转换

​	步骤：

ConversionService：是一个接口；它里面有Converter（转换器）进行工作：

1. 实现Converter接口，写一个自定义的类型转换器

   ```java
   package com.xin.component;
   
   import com.xin.bean.Car;
   import org.springframework.core.convert.converter.Converter;
   
   /**
    * @Description: 自定义数据类型转换器
    */
   public class MyStringToCarConverter implements Converter<String, Car> {
   
       /**
        * 自定义类型转换方法
        * @param s
        * @return
        */
       @Override
       public Car convert(String s) {
           System.out.println("需要转换的字符串："+s);
           Car car = new Car();
           if (s.contains("-")){
               String[] split = s.split("-");
               car.setCarName(split[0]);
               car.setType(split[1]);
               car.setName(split[2]);
           }
           return car;
       }
   }
   ```

2. Converter是ConversionService中的组件；

   1. 需要将自己写的Converter放进ConversionService中；
   2. 将WebDataBinder中的ConversionService设置成我们这个加了自定义类型转换器的ConversionService；

```xml
<!--    让SpringMVC用这个conversionService-->
    <mvc:annotation-driven conversion-service="conversionService"/>


<!--    告诉SpringMVC用自定义的ConversionService-->
    <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.xin.component.MyStringToCarConverter"></bean>
            </set>
        </property>
    </bean>
```

但是此时配置的数据格式化器无法发挥作用，所以最好装配FormattingConversionServiceFactoryBean，可以保留格式化器的功能

---

### 数据格式化

1.日期格式转换

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date birthday;
```

@DateTimeFormat可以指定传递参数的格式，如Date类型的默认格式是2019/2/28，如果使用其他格式则会出错，而@DateTimeFormat的pattern属性可以修改格式，此时修改为2019-2-28

2.数字格式转换

```java
@NumberFormat(pattern = "#,###,###.##")
private double salary;
```

使用@NumberFormat指定数据格式

---

### 数据校验

只有前端校验是不安全的，一定需要后端校验

1. 自己写程序将每一个数据拿出来校验，判断是否合法正确

2. JSR303校验

   一个规范，由hibernater Validator第三方框架进行实现