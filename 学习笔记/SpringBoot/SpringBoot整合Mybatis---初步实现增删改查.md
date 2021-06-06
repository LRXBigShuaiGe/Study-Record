# SpringBoot整合Mybatis---初步实现增删改查

## 准备工作

1. 建库建表

   数据库名：think

   数据表名：department

   属性：int型 id， String型 departmentName

   ![image-20200728211331901](https://z3.ax1x.com/2021/06/06/2UelOs.png)

2. Springboot项目中导入Mybatis需要的依赖

```xml
<!-- MyBatis 所需要的依赖-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```

当然别忘了mysql连接池的依赖

3、配置数据库连接信息

可以在resources目录下的application.properties下配置，这里则使用application.yml文件配置，只是格式不同而已

```yaml
spring:
  datasource:
    username: root
    password:
    #?serverTimezone=UTC解决时区的报错,还有如果数据库不同记得更改数据库名
    url: jdbc:mysql://localhost:3306/think?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

4、测试数据库是否连接成功

编写测试类测试

```java
package com.xin.jdbctest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class JdbcTestApplicationTests {

    @Autowired
    DataSource dataSource;//自动配置数据源
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

}
```

如果测试运行不报错表示连接成功

## 配置Mybatis

1、先创建一个实体类department

​	创建一个entity包，在包中创建department实体类

```java
package com.xin.jdbctest.entity;

public class Department {
    private int id;
    private String departmentName;

    public Department() {
    }

    public Department(int id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
```

2. 创建mapper包以及对应的 Mapper 接口，并且先写上增删改查的方法

```java
package com.xin.jdbctest.mapper;

import com.xin.jdbctest.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
//@Mapper : 表示本类是一个 MyBatis 的 Mapper
@Mapper
@Repository
public interface DepartmentMapper {
    // 获取所有部门信息
    List<Department> getDepartments();

    // 通过id获得部门
    Department getDepartment(Integer id);

    //增加一个部门
    int addDepartment(Department department);

    //通过id删除一个部门
    int deleteDepartment(Integer id);

    //提供id修改部门名字
    int updateDepartment(Department department);
}
```

3. 创建对应的Mapper映射文件

   在resources目录下创建一个mybatis目录，在mybatis目录下创建一个DepartmentMapper.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   
    <!--        填写命名空间，关联到之前写的Mapper接口DepartmentMapper-->
   <mapper namespace="com.xin.jdbctest.mapper.DepartmentMapper">
    
    <!--获取所有部门信息 -->
       <select id="getDepartments" resultType="Department">
          select * from department;
       </select>
       
    <!--通过id获得部门 -->
       <select id="getDepartment" resultType="Department" parameterType="int">
          select * from department where id = #{id};
       </select>
       
   <!--增加一个部门 -->
       <insert id="addDepartment" parameterType="Department">
           insert into department (id,departmentName) values (#{id},#{departmentName});
       </insert>
       
   <!--通过id删除一个部门 -->
       <delete id="deleteDepartment" parameterType="int">
           delete from department where id = #{id};
       </delete>
       
   <!--提供id修改部门名字 -->
       <update id="updateDepartment" parameterType="Department">
           update department set departmentName = #{departmentName} where id = #{id};
       </update>
   </mapper>
   ```

   3.  SpringBoot整合Mybatis

   要整合Mybatis还需要在resources目录下的application.properties下配置,这里依然以yaml文件为例

   ```yaml
   spring:
     datasource:
       username: root
       password:
       #?serverTimezone=UTC解决时区的报错
       url: jdbc:mysql://localhost:3306/think?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
       driver-class-name: com.mysql.cj.jdbc.Driver
   
   #新增配置
   #整合mybatis
   mybatis:
   	#注意和自己的目录结构保持一致
     type-aliases-package: com.xin.jdbctest.entity
     mapper-locations: classpath:mybatis/*.xml
   
   ```

   

   ## 编写DepartmentController进行测试

   创建controller包，并在包内创建DepartmentController

   ```java
   package com.xin.jdbctest.controller;
   
   import com.xin.jdbctest.entity.Department;
   import com.xin.jdbctest.mapper.DepartmentMapper;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.bind.annotation.GetMapping;
   import org.springframework.web.bind.annotation.PathVariable;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.RestController;
   
   import java.util.List;
   
   @RestController
   public class DepartmentController {
       @Autowired
       DepartmentMapper departmentMapper;
   
       // 查询全部部门
       @GetMapping("/getDepartments")
       public List<Department> getDepartments(){
           return departmentMapper.getDepartments();
       }
   
       // 查询全部部门
       @GetMapping("/getDepartment/{id}")
       public Department getDepartment(@PathVariable("id") Integer id){
           return departmentMapper.getDepartment(id);
       }
   
       // 查询全部部门
       @GetMapping("/addDepartment")
       public String addDepartment(Integer id,String departmentName){
           int _id = (int) id;
           Department department = new Department(_id,departmentName);
           departmentMapper.addDepartment(department);
           return "OK";
       }
   
       @RequestMapping("/delete")
       public String delete(Integer id){
           System.out.println(departmentMapper.deleteDepartment(id));
           return "OK";
       }
   
       @RequestMapping("/update")
       public String update(Integer id,String departmentName){
           Department department = new Department(id,departmentName);
           departmentMapper.updateDepartment(department);
           return "OK";
       }
   
   }
   
   ```

   测试成功就完成了基本的增删改查操作

   最终的目录结构：
   
   ![image-20200728215143779](https://z3.ax1x.com/2021/06/06/2Ue0X9.png)