# SpringBoot中JDBCTemplate的基本使用

在springboot中如果不自己引入数据源，默认会使用HikariDataSource 数据源，以下就以默认数据源为例

- 准备工作

1. 在pom.xml中导入两个需要使用的相关依赖

```xml
<!--        引入spring-boot-starter-jdbc的依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
<!--        引入mysql连接池-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

2. 在resource目录下的配置文件application.xml里配置数据源

   ```properties
   #注册驱动
   spring.datasource.driver-class-name=com.mysql.jdbc.Driver
   #指定url，xxx是数据库的名字，serverTizone=UTF解决时区问题，&useUnicode=true&characterEncoding=utf-8指定字符的编码集
   spring.datasource.url=jdbc:mysql://localhost:3306/xxx?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
   #数据库的用户名
   spring.datasource.username=root
   #密码
   spring.datasource.password=123456
   ```

   当然也可以使用xxx.yml配置文件，只需改变格式即可

   ```yml
   spring:
     datasource:
       username: root
       password: 123456
       url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
       driver-class-name: com.mysql.cj.jdbc.Driver
   ```

   准备工作完成，开始编写代码

- 测试

  编写一个Controller，注入 jdbcTemplate，编写测试方法进行访问测试；

  ```java
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  
  import java.util.List;
  import java.util.Map;
  
  @RestController
  @RequestMapping("/jdbc")
  public class TestController {
  
      @Autowired//由于使用了默认的数据源，jdbcTemplate会自动装配数据源，使用起来也不用再自己来关闭数据库连接
      JdbcTemplate jdbcTemplate;
  
  
      @GetMapping("/list")//查询数据表中的全部数据
      public List<Map<String, Object>> userList(){
          String sql = "select * from xxx";//xxx为数据表的名字
          List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
          return maps;
      }
  
      //新增一个用户
      @GetMapping("/add")
      public String addUser(){
          //插入语句xxx为表名...为数据表的各项属性，中间以逗号隔开
          String sql = "insert into xxx(...) values (,,,)";//,,,为将要插入的值
          jdbcTemplate.update(sql);
          //查询
          return "Success";
      }
      
  }
  ```

  常用的jdbcTemplate方法

  - execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
  - update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
  - query方法及queryForXXX方法：用于执行查询相关语句；

  简单的增删改查就完成了

- 

