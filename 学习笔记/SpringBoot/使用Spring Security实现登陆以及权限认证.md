# 使用Spring Security实现登陆以及权限认证 

## 使用之前的准备

1、使用SpringSecurity之前需要先导入security相关依赖

```java
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
   <version>2.3.4.RELEASE</version>
</dependency>
```

​	本次用到的其他依赖还有：

```xml
<!--        web依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

<!--        测试启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--导入配置文件处理器，配置文件进行绑定就会有提示-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

<!--        lombok插件，方便写getter和setter-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

<!--        整合mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.4</version>
        </dependency>
<!--		mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
<!--        druid数据源-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.5</version>
        </dependency>
<!--        springsecurity-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
            <version>2.3.4.RELEASE</version>
        </dependency>

```





2、编写一个基础的配置类（用于配置SpringSecurity相关功能）

```java
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 开启WebSecurity模式
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       
  }
}
```

此时就可以在configure方法中配置认证与授权



## 设置页面的登陆认证和访问权限

3、在configure方法中设置各页面的访问权限

```java
  		//定制请求的授权规则，规定需要哪些权限才有权访问
		//写路径时，
        http.authorizeRequests().antMatchers("/").permitAll()//首页所有人都可以访问
                 http
                .authorizeRequests()                   		.antMatchers("/login/**","/loginfailure.html","/","/index","/user/**").permitAll()
            		//开发静态资源，否则页面的css等效果无法显现
                    .antMatchers("/css/**","/images/**","/js/**").permitAll()
                    .antMatchers("/demo01/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

```

运行测试结果，可以进入首页，但其他页面无法进入，因为还未登陆

4、开启自动配置的登陆功能

SpringSecurity有默认的登陆界面，只需在configure()方法中使用http.formLogin()

```java
 protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则，规定需要哪些权限才有权访问
        http.authorizeRequests().antMatchers("/").permitAll()//首页所有人都可以访问
                .antMatchers("/level1/**").hasRole("vip1")//设置vip不同等级的访问权限
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");


        // 开启自动配置的登录功能
        // /login 请求来到登录页
        // /login?error 重定向到这里表示登录失败
        http.formLogin()
}
```

此时如果访问没有权限的页面就会跳转至登陆页面

如果想用自己写的登录页面则需在调用一些相应方法：

```java
 http.formLogin()
                .usernameParameter("username")
                //从自定义页面中获取用户名，用户名的name属性为username，意味着"username"可以改变
                .passwordParameter("password") //获取密码
                .loginPage("/login.html")
                //登陆页面设置为我们自定义的login.html页面此时我们定义的resource/views/login.html
                .loginProcessingUrl("/login");//登陆请求拦截的url，也就是指定action里的值
```

此时就设置登陆页面为我们自定义的页面，提交请求时用户名和密码就是name属性为"username"、"password"的值了

5、设置认证规则

我们可以定义认证规则，在配置类中重写configure(AuthenticationManagerBuilder auth)方法

```java
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    //用户、权限可以在内存中定义，也可以从数据库中取，这里使用在内存中定义的方法
    //但是要注意从前端传回来的密码需要加密，否则会报错，spring security 官方推荐的是使用bcrypt加密方式。
    //不设置加密方式时的代码
    //=================================================================
       auth.inMemoryAuthentication()
          .withUser("xin").password("123456").roles("vip2","vip3")
           //此时我们设置了一个账号为xin，密码为123456，角色权限为vip2，vip3的用户
          .and()//用and连接可以设置多个用户
          .withUser("root").password("123456").roles("vip1","vip2","vip3")
          .and()
          .withUser("guest").password("123456").roles("vip1","vip2");
	}
  // ======================================================================


    //设置加密方式时的代码
    auth.inMemoryAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())//设置加密方式
            .withUser("xin").password(
                    new BCryptPasswordEncoder().encode("123456")//密码加密
    ).roles("vip2","vip3")
            .and()
            .withUser("root").password(
            new BCryptPasswordEncoder().encode("123456")
    ).roles("vip1","vip2","vip3")
            .and()
            .withUser("guest").password(
                    new BCryptPasswordEncoder().encode("123456")
    ).roles("vip1","vip2");

}
```

运行测试之后就可以使用自己定义的账号登陆了，同时每个账号只能访问我们给予权限的页面

## 权限控制与登陆

6、我们也可以在configure()方法中开启注销功能，只需要加入http.logout()

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    //定制请求的授权规则，规定需要哪些权限才有权访问
    http.authorizeRequests().antMatchers("/").permitAll()//首页所有人都可以访问
            .antMatchers("/level1/**").hasRole("vip1")//设置vip不同等级的访问权限
            .antMatchers("/level2/**").hasRole("vip2")
            .antMatchers("/level3/**").hasRole("vip3");


    // 开启自动配置的登录功能
    // /login 请求来到登录页
    // /login?error 重定向到这里表示登录失败
    http.formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .loginPage("/login.html")
            .loginProcessingUrl("/login");

    //开启自动配置的注销功能
    //注销请求 http.logout();
   http.logout()//开启注销功能
   .logoutSuccessUrl("/"); // .logoutSuccessUrl("/"); 注销成功来到首页
｝
```

开启注销功能之后，需要在前端的页面中加入一个注销按钮

``` html
<a class="item" th:href="@{/logout}">//这里用的是Thymeleaf模版引擎，这里注销的请求url为"/logout"
   <i class="address card icon"></i> 注销
</a>
```

这时测试，点击注销，就会返回首页，如果不设置.logoutSuccessUrl("/")，就会返回登陆页面



7、开启”记住我“功能

与开启注销功能一样，在configure()方法中写入http.remember()

```java
//开启记住我功能
http.rememberMe()
.rememberMeParameter("remember");//定制记住我功能的参数,在使用自定义的登陆页面时需要传入参数
```

这时默认的登陆界面就会出现记住我（remember me）的选项，勾选之后登陆就会保留Cookie，关闭浏览器后再打开就会自动登陆了，在点击注销按钮时就会删除Cookie信息，就不会记住用户了

ps:以上是本人在观看B站UP[遇见狂神说](https://space.bilibili.com/95256449?spm_id_from=333.788.b_765f7570696e666f.1)发布的SpringBoot教程之后的部分实践笔记，使用的静态资源也是从交流群中获取，所以没有展示使用的静态资源代码

