# springboot 多环境切换

##  properties文件

profile是Spring对不同环境提供不同配置功能的支持，可以通过激活不同的环境版本，实现快速切换环境；

例如：
	application-test.properties 代表测试环境配置
	application-dev.properties  代表开发环境配置
但是Springboot并不会直接启动这些配置文件，它默认使用application.properties主配置文件；

![image-20200731220821035](https://z3.ax1x.com/2021/06/06/2UZexJ.png)

我们需要通过一个配置来选择需要激活的环境：

```propert
spring.profiles.active=dev
```

spring.profiles.active可以指定配置环境，如指定为dev就会选择	application-dev.properties 文件作为配置环境，同理，要指定不同的环境配置，只需指定application-xxx.properties的xxx即可。

如此时创建不同的配置文件

[![2UZ1IK.png](https://z3.ax1x.com/2021/06/06/2UZ1IK.png)](https://imgtu.com/i/2UZ1IK)

在主配置文件中加入spring.profiles.active=dev，

在	application-dev.properties 中加入：

```
server.port=8081
```

在	application-test.properties 中加入

```
server.port=8082
```

启动项目，可以看到端口设置为了8081

[![2UZYxH.png](https://z3.ax1x.com/2021/06/06/2UZYxH.png)](https://imgtu.com/i/2UZYxH)

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件：

> 优先级1：项目路径下的config文件夹配置文件 file:   ./config/
> 优先级2：项目路径下配置文件:file:  ./
> 优先级3：资源路径下的config文件夹配置文件  classpath: ./config/
> 优先级4：资源路径下配置文件: classpath: ./  ----------默认创建application.properties的地方



## yaml文件

yaml文件切换配置环境与properties文件有所不同，它可以在同一个文件中配置多套环境，只要给每套环境设置名称，在主配置环境中指定即可：



```yaml

server:
  port: 8081
#选择要激活那个环境
spring:
  profiles:
    active: test

---
server:
  port: 8083
spring:
  profiles: dev #配置环境的名称


---

server:
  port: 8084
spring:
  profiles: test  #配置环境的名称
```

测试就发现启动端口是8084，切换环境成功