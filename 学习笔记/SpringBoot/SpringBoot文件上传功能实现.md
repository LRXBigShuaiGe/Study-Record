# SpringBoot文件上传功能实现

要实现文件上传功能，需要了解一个关键接口MultipartFile，了解它可以使用的方法：

```java
MultipartFile
--String getName() //返回表单中file文件参数name的名称。
--String getOriginalFilename() // 文件原名称
--String getContentType() //返回文件的内容类型。
--boolean isEmpty() // 返回上传的文件是否为空，即，在多部分表单中没有选择任何文件，或者所选文件没有内容。
--long getSize() // 以字节为单位返回文件的大小。
--byte[] getBytes() //将文件的内容作为字节数组返回。
--InputStream getInputStream() //返回一个InputStream以从中读取文件的内容。
--void transferTo(File dest) //将收到的文件传输到给定的目标文件。
```

1. 案例中会用到ThymeLeaf模板引擎，所以要先导入依赖

```xml
<!--        web依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

<!--        thymeleaf模版引擎-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

```

2. 配置application.properties文件

```properties
#thymeleaf
#引入resources/templates/下的模板文件，当然默认值就是/templates/,可以不用配置
spring.thymeleaf.prefix=classpath:/templates/
#设置thymeleaf模板后缀
spring.thymeleaf.suffix=.html
#设置thymeleaf对html不要严格校验，因为默认配置对html格式非常严格，一点格式不同就可能会报错
spring.thymeleaf.mode=LEGACYHTML5
#设定字符编码为UTF-8，防止中文乱码
spring.thymeleaf.encoding=UTF-8
#Content-Type值
spring.thymeleaf.content-type=text/html
# 禁用 thymeleaf 缓存
spring.thymeleaf.cache=false


# 上传文件总的最大值
spring.servlet.multipart.max-request-size=10MB
# 单个文件的最大值
spring.servlet.multipart.max-file-size=10MB
# 是否支持批量上传   (默认值 true)
spring.servlet.multipart.enabled=true
# 上传文件的临时目录 （一般情况下不用特意修改）
spring.servlet.multipart.location=
# 文件大小阈值，当大于这个阈值时将写入到磁盘，否则存在内存中，（默认值0 一般情况下不用特意修改）
spring.servlet.multipart.file-size-threshold=0
# 判断是否要延迟解析文件（相当于懒加载，一般情况下不用特意修改）
spring.servlet.multipart.resolve-lazily=false
```

3.准备两个模版文件，作为测试用的页面，也可以用第三方工具测试

- fileUpload.html(单个文件上传)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>单文件上传测试</title>
</head>
<body>
<p>单个文件上传</p>
<form method="POST" enctype="multipart/form-data" action="/upload" >
    文件：<input type="file" name="file"/>
    <input type="submit"/>
</form>
<hr/>
</body>
```

[![2UepWD.png](https://z3.ax1x.com/2021/06/06/2UepWD.png)](https://imgtu.com/i/2UepWD)

- fileUpload2.html(多个文件上传)

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>多文件上传测试</title>
</head>
<body>
<p>多个文件上传</p>
<form method="POST" enctype="multipart/form-data" action="/upload2">
    <p>文件1：<input type="file" name="file"/></p>
    <p>文件2：<input type="file" name="file"/></p>
    <p><input type="submit" value="上传"/></p>
</form>
</body>

```

[![2UeFOA.png](https://z3.ax1x.com/2021/06/06/2UeFOA.png)](https://imgtu.com/i/2UeFOA)

4. 新建controller包，编写一个FileUploadController类

- 先写页面跳转的方法

  ```java
   @RequestMapping("/test")
      public String test(){
          return "/fileUpload";
      }
  
      @RequestMapping("/test2")
      public String test2(){
          return "/fileUpload2";
      }
  ```

- 编写单文件上传和多文件上传的方法

  单文件上传

  ```java
  @PostMapping("/upload")
      @ResponseBody
      public String upload(@RequestParam("file") MultipartFile file){
          if (file.isEmpty()) {
              return "上传的文件不能为空";
          }
          try {
              System.out.println("文件类型ContentType:"+file.getContentType());
              System.out.println("文件组件名称Name:"+file.getName());
              System.out.println("文件大小:"+file.getSize());
              System.out.println("文件原名称OriginalFileName:"+file.getOriginalFilename());
              String path = "C:\\Users\\lenovo\\Desktop\\ideal-project";
  
              File f = new File(path);
              if (!f.exists()){//如果文件路径不存在，就创建相关目录
                  f.mkdir();
              }
              // 文件写入
              File dir = new File(path+"\\" +file.getOriginalFilename());
              file.transferTo(dir);
              return "上传单个文件成功";
          }catch (Exception e) {
              e.printStackTrace();
              return "上传单个文件失败";
          }
      }
  ```

  多文件上传

  ```java
   @PostMapping("/upload2")
      @ResponseBody//MultipartFile[] files会将所有name为file的集合在一起
      public String uploadBatch(@RequestParam("file") MultipartFile[] files) {
          System.out.println("文件名称："+ files);
          if(files!=null&&files.length>0){//依次遍历文件集合
              String filePath = "C:\\Users\\lenovo\\Desktop\\ideal-project\\";
              for (MultipartFile mf : files) {
                  // 获取文件名称
                  String fileName = mf.getOriginalFilename();
  //                // 获取文件后缀
  //                String suffixName = fileName.substring(fileName.lastIndexOf("."));
  //                // 重新生成文件名
  //                fileName = UUID.randomUUID()+suffixName;
  
                  if (mf.isEmpty()) {
                      return "文件名称："+ fileName +"上传失败，原因是文件为空!";
                  }
                  File dir = new File(filePath + fileName);
                  try {
                      // 写入文件
                      mf.transferTo(dir);
                      System.out.println("文件名称："+ fileName +"上传成功");
                  } catch (IOException e) {
                      return "文件名称："+ fileName +"上传失败";
                  }
              }
              return "多文件上传成功";
          }
          return "上传文件不能为空";
      }
  ```

  测试文件上传成功。

  controller完整代码

  ```java
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.*;
  import org.springframework.web.multipart.MultipartFile;
  
  import java.io.File;
  import java.io.IOException;
  
  @Controller
  public class FileUploadController {
  
      @PostMapping("/upload")
      @ResponseBody
      public String upload(@RequestParam("file") MultipartFile file){
          if (file.isEmpty()) {
              return "上传的文件不能为空";
          }
          try {
              System.out.println("文件类型ContentType:"+file.getContentType());
              System.out.println("文件组件名称Name:"+file.getName());
              System.out.println("文件大小:"+file.getSize());
              System.out.println("文件原名称OriginalFileName:"+file.getOriginalFilename());
              String path = "C:\\Users\\lenovo\\Desktop\\test5";
  
              File f = new File(path);
              if (!f.exists()){
                  f.mkdir();
              }
              // 文件写入
              File dir = new File(path+"\\" +file.getOriginalFilename());
              file.transferTo(dir);
              return "上传单个文件成功";
          }catch (Exception e) {
              e.printStackTrace();
              return "上传单个文件失败";
          }
      }
  
      @PostMapping("/upload2")
      @ResponseBody
      public String uploadBatch(@RequestParam("file") MultipartFile[] files) {
          System.out.println("文件名称："+ files);
          if(files!=null&&files.length>0){
              String filePath = "C:\\Users\\lenovo\\Desktop\\ideal-project\\";
              for (MultipartFile mf : files) {
                  // 获取文件名称
                  String fileName = mf.getOriginalFilename();
  //                // 获取文件后缀
  //                String suffixName = fileName.substring(fileName.lastIndexOf("."));
  //                // 重新生成文件名
  //                fileName = UUID.randomUUID()+suffixName;
  
                  if (mf.isEmpty()) {
                      return "文件名称："+ fileName +"上传失败，原因是文件为空!";
                  }
                  File dir = new File(filePath + fileName);
                  try {
                      // 写入文件
                      mf.transferTo(dir);
                      System.out.println("文件名称："+ fileName +"上传成功");
                  } catch (IOException e) {
                      return "文件名称："+ fileName +"上传失败";
                  }
              }
              return "多文件上传成功";
          }
          return "上传文件不能为空";
      }
  
      @RequestMapping("/test")
      public String test(){
          return "/fileUpload";
      }
  
      @RequestMapping("/test2")
      public String test2(){
          return "/fileUpload2";
      }
  }
  ```

  

