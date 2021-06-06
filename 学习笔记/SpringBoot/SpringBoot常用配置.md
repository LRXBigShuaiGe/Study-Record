# SpringBoot常用配置

## 静态资源访问路径配置

1. SpringBoot中的**src/main/resources/**资源文件夹对应**classpath:**

<img src="https://z3.ax1x.com/2021/06/06/2UZyRg.png" style="zoom:75%;" />

2. 资源的默认访问路径，从左到右优先级依次降低，当我们访问**IP地址:端口号/**时，就相当于访问到了这四个文件夹。

   如：访问:localhost:8080/123.jpg时，就会先去看src/main/resources资源文件夹下的META-INF下的resoures文件夹
           中是否有123.jpg，没  有的话再看src/main/resources资源文件夹下的resoures文件夹中是否有123.jpg，没有的
           话再看src/main/resources资源文件夹下的static文件夹中是否有123.jpg，没有的话再看src/main/resources资源
            文件夹下的public文件夹中是否有123.jpg。
   原文链接：https://blog.csdn.net/justry_deng/article/details/814067523

3. 手动配置访问路径

- 通过spring boot配置文件配置：

  ```yaml
  # 设置当URI为/fileData/**时,才进过静态资源
  # 注:如果设置为/**，那么表示任何访问都会经过静态资源路径
  spring.mvc.static-path-pattern=/fileData/**
   
  # 自定义路径
  # 注:自定义路径时,默认的四个文件夹下中的“META-INF下的resoures文件夹”仍然有效,其他三个文件夹失效
  # (此时:访问ip:端口号/fileData/时，相当于访问到了 “自定义的文件夹”和 “META-INF下的resoures文件夹”);
  # 注:搜索文件时，自定义的文件夹的优先级要高于默认的四个文件夹
   spring.resources.static-locations=file:D:/myFile/
   
  # 提示:如果我们显式地把自定义文件夹  和 默认的四个文件夹都写出来，那么都会有效(此时，优先级 从左至右 依次降低)
  #spring.resources.static-locations = classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/,file:D:/myFile/
  ```

  - 通过配置类

  ```java
  import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
  import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
   
  /**
   * 主动设置URL匹配路径
   *
   * @author JustryDeng
   * @date 2018年8月4日 上午1:36:38
   */
  @Configuration
  public class MyURLPatternConfiguration extends WebMvcConfigurationSupport {
  	@Override
  	public void addResourceHandlers(ResourceHandlerRegistry registry) {
  		registry.addResourceHandler("/fileData/**").addResourceLocations("classpath:/myFile/");
  		super.addResourceHandlers(registry);
  	}
  }
  
  ```

  此时：访问*IP地址:端口号/fileData/*时，就相当于到了*src/main/resources资源文件夹下的myFile文件夹*中了