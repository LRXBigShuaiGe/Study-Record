# Spring  IOC(控制反转)思想笔记

IOC控制反转基本理念就是将程序控制权从程序员手中交给用户自定义，从而避免了因为用户一个小需求的变化使得程序员需要改动大量代码。

[![2UEAe0.png](https://z3.ax1x.com/2021/06/06/2UEAe0.png)](https://imgtu.com/i/2UEAe0)

## 案例

如果按照之前javaweb的固定方法，写一个简单的业务逻辑

1. 一个DAO接口

   ```java
   public interface UserDao {
       public void getUser();
   }
   ```

2. 实现DAO接口

   ```java
   public class UserDaoImpl implements UserDao {
       @Override
       public void getUser() {
           System.out.println("获取用户数据");
       }
   }
   ```

3. 一个service接口

   ```java
   public interface UserService {
       public void getUser();
   }
   ```

4. 实现service接口

   ```java
   public class UserServiceImpl implements UserService {
       private UserDao userDao = new UserDaoImpl();
    
       @Override
       public void getUser() {
           userDao.getUser();
       }
   }
   ```

5. 测试类

   ```java
   @Test
   public void test(){
       UserService service = new UserServiceImpl();
       service.getUser();
   }
   ```

   这些是原来方式编写的基本步骤，如果现在增加需求，在UserServiceImpl中不是需要UserDaoImpl,而是一个新的实现了UserDao接口的UserDaoMySqlImpl

6. 新增UserDaoMySqlImpl实现UserDao接口

   ```java
   public class UserDaoMySqlImpl implements UserDao {
       @Override
       public void getUser() {
           System.out.println("MySql获取用户数据");
       }
   }
   ```

   如果要去使用这个UserDaoMySqlImpl的话，就需要在UserServiceImpl中修改代码

7. 修改UserServiceImpl

   ```java
   public class UserServiceImpl implements UserService {
       private UserDao userDao = new UserDaoMySqlImpl();//修改部分
    
       @Override
       public void getUser() {
           userDao.getUser();
       }
   }
   ```

8. 如果再增加一个UserDaoOracleImpl

   ```java
   public class UserDaoOracleImpl implements UserDao {
       @Override
       public void getUser() {
           System.out.println("Oracle获取用户数据");
       }
   }
   ```

   如果又要使用这个dao，则又需要去service中改变代码，假设项目十分庞大，修改一个dao可能涉及几十几百行代码，我们的工作量就会非常大，程序耦合性也非常高。

### 如何解决问题

 9. 在UserServiceImpl中增加一个set方法

    ```java
    public class UserServiceImpl implements UserService {
        private UserDao userDao;
        // 利用set方法，让调用者自行选择需要的dao
        public void setUserDao(UserDao userDao) {
            this.userDao = userDao;
        }
     
        @Override
        public void getUser() {
            userDao.getUser();
        }
    }
    ```

	10. 新的测试类

     ```java
     @Test
     public void test(){
         UserServiceImpl service = new UserServiceImpl();
         service.setUserDao( new UserDaoMySqlImpl() );//自行选择UserDaoMySqlImpl
         service.getUser();
         //如果又要用UserDaoOracleImpl，也是可以由调用者自行选择
         //service.setUserDao( new UserDaoOracleImpl() );
         //service.getUser();
     }
     ```

     

**之前所有的改变都是有程序员去控制，现在将改变的主动权交给了调用者/用户，程序也不用去管怎么创建和调用了，使得程序员可以专心实现业务，只实现一个提供服务的接口，不再管理对象的创建，也降低了代码之间的耦合度，当需求改变时也不必修改大量代码**



## IOC思想

**控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法**，没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方。

控制反转：可以理解为控制 **对象创建** 的 主动权 反转了。原来对象创建要由我们编写的程序控制，主动权在程序手里，控制反转后就将创建对象的主动权移交给第三方，这样可以降低程序代码间的耦合性，减少了修改代码的工作量。

![2UEnW4](https://z3.ax1x.com/2021/06/06/2UEnW4.png)

![2UEQyR](https://z3.ax1x.com/2021/06/06/2UEQyR.png)

![img](https://z3.ax1x.com/2021/06/06/2UE8w6.png)

在Spring中，IOC思想的体现就是依赖注入，在xml文件中或者使用注解创建对象交由IOC容器管理，之后获取对象就不再通过new 对象，而是通过配置文件从IOC容器中获取对象，之后修改就不需要改动程序，而只需要在xml配置文件中进行修改 ，将对象交由Spring去创建、管理、装配。

