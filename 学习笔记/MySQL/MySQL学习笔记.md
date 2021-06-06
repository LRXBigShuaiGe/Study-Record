# MySQL学习笔记



## 小知识

如果安装mysql配置过程出错，可以在安装的目录中运行以下程序重新配置

[![2UFX6I.png](https://z3.ax1x.com/2021/06/06/2UFX6I.png)](https://imgtu.com/i/2UFX6I)

如果安装过程程序未响应，可以重新安装，也许杀毒软件阻止了安装过程的某些行为

## 基本语句结构

 ### 查询语句

#### 1.条件查询

select 查询列表

from 表名

where 筛选条件

##### 条件查询关键字

or 或，and 与， not 非

like 模糊查询   'a%'

in ：属于某个列表（字段） ，如select * from users where id in(10,50) ，意为查询users表中id为10或50的数据信息

between and ：在。。。之间，如between 10 and 50，即在10到50之间

is null：是否为null，select * from users where id is null ，意为查询users表中id为null的信息

distinct:去除重复

#### 2.排序查询

select 查询列表

from 表名·

【where 筛选条件】

order by 排序列表（顺序，表达式，别名）

##### 排序查询关键字

asc：升序排列，（默认 值）

desc：降序排列

##### a.按顺序排序

==例==：select * from employees where id > 1 ==order by salary asc== （按工资升序排列）

​		select * from employees where id > 1 ==order by salary desc== （按工资降序排列）

##### b.按表达式排序

例：SELECT *

 FORM employees 

WHERE salary IS NOT NULL

 ==ORDER BY== **salary* 12*(1+IFNULL(year,0)) ==DESC==**

##### c.按别名排序

SELECT * ，**salary* 12*(1+IFNULL(year,0))  年薪** 

FORM employees 

WHERE salary IS NOT NULL 

==ORDER BY== **年薪** ==DESC==

##### d.按表达式结果排序

例：按名字字数长度进行降序排列

SELECT last_name,id

FROM users

WHERE salary>1000

==ORDER BY **LENGTH(last_name)** DESC==

##### e.按多个字段进行排序

例：查询员工信息，先按工资升序，再按部门编号降序

SELECT last_name,salary,department_id

FROM users

WHERE salary>1000

==ORDER BY **salary ASC,department_id DESC**==

##### f.按列排序（较少使用）

如

SELECT last_name,salary,department_id

FROM users

WHERE salary>1000

==ORDER BY 2==

意为按表中第二个字段进行升序排序



### 插入数据

insert into 表名（字段名1，字段名2，。。。） values（值1，值2，。。。）------插入指定的字段值

insert into  表名  values（值1，值2，。。。） ----------所有的字段都需要填值，除了设置自增长的

insert into 表名  values（值1，值2，。。。。），（值1，值2，。。。）。。。 -------插入多行数据

特点：

		1. 字段和值列表一一对应，包含类型、约束等必须匹配
  		2. 数值型的数据不用单引号，非数值型的数据必须用单引号
                		3. 字段顺序无要求

eg：insert into students(stuid,name,email,majorid) values(1,'李小鑫',lixiaoxin@qq.com,2)

插入规则

1、如果插入某个字段不填入值，此字段如果设置为非空则报错，有默认值则填入默认值，没有默认值则为空

2、填入时没有设置为非空的字段可以直接填入NULL，表示为空

eg：insert into students(stuid,name,email,majorid) values(1,'李小鑫',**NULL**,2)



#### 自增主键

1. 如果是图形化界面管理工具，有设置自动递增的选项

2. 命令行设置自增长：

   ​	AUTO_INCREMENT 关键字

   ​	eg：CREATE TABLE stuinfo(

   ​		id **INT** *PRIMARY KEY* ==AUTO_INCREMENT==

   );

### 修改数据

update 表名 set 字段名1=值1，字段名2=值2，。。。

where 筛选条件





### 删除数据

**方式一**：delete语句：（删除符合条件的数据）

​			delete from 表名 where 筛选条件

**方式二**：truncate语句（删除表中所有数据）

​			truncate table 表名；

**ps: truncate语句是将原有的表删除，同时创建一个相同的表，以达到删除所有数据的目的**

**但是delete删除原有数据后，自增长列并不会从头开始，而是继续累加**





## 常见函数（只记录部分）



### 字符函数（只记下一些常用的）

#### 1. CONCAT 拼接字符

​	例：SELECT **CONCAT('hello',username)** FROM `user`;

输出结果为：

​		helloguest1
​		helloguest2
​		helloguest3

CONCAT可以将结果与指定字符拼接可以指定任意个变量==CONCAT(str1,str2,str3,...)==

#### 2.LENGTH 获取==字节==长度

​	SELECT LENGTH('hello,小星');

​	运行结果为：12，因为一个中文字符是3个字节

#### 3.CHAR_LENGTH 获取==字符==长度

​	SELECT CHAR_LENGTH('hello,小星');

​	运行结果为：8，因为计算的是字符长度

#### 4.SUBSTRING 截取字符串

​	结构：SUBSTRING(str,pos,len);

​				SUBSTRING(str,pos);

​	*注意:第一个str是指定的字符串，pos是起始位置（起始索引从1开始），len是截取长度，如果不填写，则截取后面的所有字符*

​	SELECT SUBSTR('最后的结局终于来临了',1,5);

​		结果：最后的结局

​	SELECT SUBSTR('最后的结局终于来临了',6);

​		结果：终于来临了

#### 5.INSTR 获取字符第一次出现的索引

​	SELECT INSTR('天上的星星不在星星所喜欢的星星的天空','星星');

​	结果：4，因为星星第一次出现在索引为4的地方

#### 6. TRIM 去掉前后指定的字符，默认是空格

​	结构：TRIM([**remstr** FROM] **str**)

​	SELECT TRIM('    繁   星   坠 落      ');

​	结果只有：繁   星   坠 落 

​	SELECT **TRIM('x'FROM'xxxx繁   星xxxx坠x落xxxxxx')**;

​	结果：**繁   星xxxx坠x落**、

#### 7.LPAD / RPAD  左填充/右填充

​	结构：LPAD(str,len,padstr)，str是指定字符串，len是输出字符串的长度，padstr是填充的字符

​	*如果字符串本身比指定长度常，则只会输出指定长度的字符*

​	SELECT LPAD('崩坏',8,'h');

​	结果：hhhhhh崩坏

​	SELECT RPAD('崩坏',8,'h');

​	结果：崩坏hhhhhh

	#### 8. UPPER/LOWER 变大写/变小写

​	SELECT UPPER('delete');

​	结果：DELETE

#### 9.STRCMP 比较字符串的大小

==有点问题，暂时无法解决==

#### 10. LEFT/RIGHT 截取子串

​	SELECT `LEFT`(str,len)

### 数学函数

	#### 1. ABS 绝对值

​	SELECT ABS(-2);       2

#### 2. CEIL 向上取整

​	SELECT CEIL(1.009);         2

#### 3. FLOOR 向下取整

​	SELECT FLOOR(1.999999);          1

#### 4.ROUND 四舍五入

​	ROUND(数字，保留几位小数)

​	SELECT ROUND(1.54236);            2

​	SELECT ROUND(1.44236);             1

​	SELECT ROUND(1.44236,4);            1.4424

​	SELECT ROUND(1.44233,4);            1.4423

#### 5.TRUNCATE 截断

TRUNCATE(数字，截断点)

​	SELECT TRUNCATE(1.2345678,2);          1.23

​	SELECT TRUNCATE(1.2345678,5);           1.23456

#### 6. MOD 取余

​	SELECT MOD(10,3);            1

​	SELECT MOD(10,-3)             1

​	SELECT MOD(-10,-3);            -1

​	SELECT MOD(-10,3);            -1

### 日期函数

#### 1. NOW 当前时间

SELECT NOW();           结果：2020-10-06 16:57:43

#### 2. CURDATE 获取当前日期

​	SELECT CURDATE();     结果：2020-10-06

#### 3. CURTIME 只获取当前时间

​	SELECT CURTIME();          结果：17:00:36

#### 4.DATADIFF 获取两个日期之差（天数）

​	SELECT DATEDIFF('2020-10-06','2001-03-31');       结果：7129

#### 5.DATE_FORMAT 改变日期格式

​	SELECT DATE_FORMAT(NOW(),'%Y年%M月%d日 %H时%i分钟%s秒') 当前时间; 

​		结果：2020年October月06日 17时08分钟59秒

#### 6.STR_TO_DATE 将指定字符串转换为日期格式

​	SELECT STR_TO_DATE('2020年，10.06','%Y年，%m.%d');

​	结果：2020-10-06

### 流程控制函数

#### 1. IF函数

​	IF(条件，满足，不满足)

​	SELECT IF(1>2,1,2);     结果为2

#### 2.CASE函数

------

**情况1：**（类比switch语句）

CASE 表达式

WHEN 值1 THEN 结果1

WHEN 值2 THEN 结果2

。。。

ELSE  结果n（如果都不满足）

END

------

**情况2：**（类比if选择语句）

CASE

WHEN 条件1 THEN 结果1

WHEN 条件2 THEN 结果2

。。。

ELSE 结果n

END

------



### 分组函数

1. sum(字段名)          求和

2. avg(字段名)            求平均数

3. max(字段名)           求最大值

4. min(字段名)            求最小值

5. count(字段名)          计算非空字段值的个数

   

**需要注意的是count函数**

1.  用法1：计算总条数  select count(*) from users;或者select count(常量) from users;

2. 用法2：搭配disitinct实现去重

   select count(distinct id) from users;

### 分组查询

语法：

select 查询列表

from  表名

where  筛选条件(不能使用函数)

group by  分组列表

having  分组后筛选条件（可以使用函数）

order by  排序列表；

执行顺序：1. from语句  -->2.  where子句  --> 3.group by子句 --> 4.having子句  --> 5.select子句 --> 6.order by子句

#### 1.简单分组

查询每个工种的平均工资

select AVG(salary),job_id 

from employees

group by job_id;

![image-20201007191704854](https://z3.ax1x.com/2021/06/06/2UkPhQ.png)

#### 2.分组前筛选

查询邮箱中包含字符a的每个部门的最高工资

SELECT  MAX(salary)  最高工资,department_id

FROM employees

WHERE email like '%a%'

GROUP BY department_id;

#### 3.分组后筛选

SELECT  count(*) 员工人数, department_id

FROM employees

GROUP BY department_id

**HAVING  count(*)>5;**

![image-20201007192510777](C:\Users\lenovo\Desktop\image-20201007192510777.png)

#### 4.实现排序操作

------

## 常见数据类型

| 类型         | 大小                                     | 范围（有符号）                                               | 范围（无符号）                                               | 用途            |
| ------------ | ---------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | --------------- |
| TINYINT      | 1 字节                                   | (-128，127)                                                  | (0，255)                                                     | 小整数值        |
| SMALLINT     | 2 字节                                   | (-32 768，32 767)                                            | (0，65 535)                                                  | 大整数值        |
| MEDIUMINT    | 3 字节                                   | (-8 388 608，8 388 607)                                      | (0，16 777 215)                                              | 大整数值        |
| INT或INTEGER | 4 字节                                   | (-2 147 483 648，2 147 483 647)                              | (0，4 294 967 295)                                           | 大整数值        |
| BIGINT       | 8 字节                                   | (-9,223,372,036,854,775,808，9 223 372 036 854 775 807)      | (0，18 446 744 073 709 551 615)                              | 极大整数值      |
| FLOAT        | 4 字节                                   | (-3.402 823 466 E+38，-1.175 494 351 E-38)，0，(1.175 494 351 E-38，3.402 823 466 351 E+38) | 0，(1.175 494 351 E-38，3.402 823 466 E+38)                  | 单精度 浮点数值 |
| DOUBLE       | 8 字节                                   | (-1.797 693 134 862 315 7 E+308，-2.225 073 858 507 201 4 E-308)，0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) | 0，(2.225 073 858 507 201 4 E-308，1.797 693 134 862 315 7 E+308) | 双精度 浮点数值 |
| DECIMAL      | 对DECIMAL(M,D) ，如果M>D，为M+2否则为D+2 | 依赖于M和D的值                                               | 依赖于M和D的值                                               | 小数值          |

**日期和时间类型**

表示时间值的日期和时间类型为DATETIME、DATE、TIMESTAMP、TIME和YEAR。

每个时间类型有一个有效值范围和一个"零"值，当指定不合法的MySQL不能表示的值时使用"零"值。

TIMESTAMP类型有专有的自动更新特性，将在后面描述。

| 类型      | 大小 (字节) | 范围                                                         | 格式                | 用途                     |
| --------- | ----------- | ------------------------------------------------------------ | ------------------- | ------------------------ |
| DATE      | 3           | 1000-01-01/9999-12-31                                        | YYYY-MM-DD          | 日期值                   |
| TIME      | 3           | '-838:59:59'/'838:59:59'                                     | HH:MM:SS            | 时间值或持续时间         |
| YEAR      | 1           | 1901/2155                                                    | YYYY                | 年份值                   |
| DATETIME  | 8           | 1000-01-01 00:00:00/9999-12-31 23:59:59                      | YYYY-MM-DD HH:MM:SS | 混合日期和时间值         |
| TIMESTAMP | 4           | 1970-01-01 00:00:00/2038结束时间是第 **2147483647** 秒，北京时间 **2038-1-19 11:14:07**，格林尼治时间 2038年1月19日 凌晨 03:14:07 | YYYYMMDD HHMMSS     | 混合日期和时间值，时间戳 |



**字符串类型**

字符串类型指CHAR、VARCHAR、BINARY、VARBINARY、BLOB、TEXT、ENUM和SET。该节描述了这些类型如何工作以及如何在查询中使用这些类型。





| 类型       | 大小                | 用途                            |
| ---------- | ------------------- | ------------------------------- |
| CHAR       | 0-255字节           | 定长字符串                      |
| VARCHAR    | 0-65535 字节        | 变长字符串                      |
| TINYBLOB   | 0-255字节           | 不超过 255 个字符的二进制字符串 |
| TINYTEXT   | 0-255字节           | 短文本字符串                    |
| BLOB       | 0-65 535字节        | 二进制形式的长文本数据          |
| TEXT       | 0-65 535字节        | 长文本数据                      |
| MEDIUMBLOB | 0-16 777 215字节    | 二进制形式的中等长度文本数据    |
| MEDIUMTEXT | 0-16 777 215字节    | 中等长度文本数据                |
| LONGBLOB   | 0-4 294 967 295字节 | 二进制形式的极大文本数据        |
| LONGTEXT   | 0-4 294 967 295字节 | 极大文本数据                    |



## 常用约束

1. NOT  NULL  非空: 用于限制该字段为必填项

2. DEFAULT  默认：用于限制该字段如果没有显示插入值，则显示默认值

3. PRIMARY KEY 主键：用于限制该字段不能重复，不能为空，且只能有一个主键

4. UNIQUE  唯一：用于限制该字段不能重复，可以为空，一张表中可以有多个唯一 

5. CHECK 检查：用于检查字段输入值是否满足条件，但MySQL不支持

6. FOREIGN KEY ：外键：用于限制两个表的关系，要求外键列的值必须来自主表的关联列

   ​								要求：

   ​							1、主表的关联列和从表的关联列类型必须一致，意思一样，名称没有要求

   ​							2、主表的关联列必须是主键

eg：

​	CREATE  TABLE IF  NOT  EXISTS stuinfo{

​		stuid INT  ==PRIMARY KEY==,#添加了主键约束

​		stuname  VARCHAR(20)  UNIQUE  NOT  NULL,  #添加了唯一约束、非空约束

​		stugender  CHAR(1)  DAFAULT '男',   添加了默认约束

​		email  VACHAR  NOT  NULL,

​		age  INT  CHECK(BETTEN 10  AND 50) , #添加了检查约束，但mysql不支持，虽然不报错，但是没效果

​		magorid  INT,

​		CONSTRAINT  fk_stuinfo_major  FOREIGN  KEY  (majorid) REFERENCES  major(id)



}





------

## SQL99语法

### 内连接

SELECT  查询列表

FROM  表名1 别名

==**【INNER】JOIN 表名2 别名**==

==**ON  连接条件**==

WHERE  筛选条件

GROUP BY  分组条件

HAVIGN  分组后筛选条件

ORDER BY  排序列表

### 外连接

​	SELECT  查询列表

​	FROM  表名1 别名

​	==**LEFT/RIGHT  JOIN 表名2 别名**==（左/右连接）

​	==**ON  连接条件**==

​	WHERE  筛选条件

​	GROUP BY  分组条件

​	HAVIGN  分组后筛选条件

​	ORDER BY  排序列表

​	左连接表示LEFT JOIN 左边是主表，RIGHT表示右边是主表

### 子查询

​	说明：当一个查询语句中又嵌套了另一个完整的select语句，则被嵌套的select语句成为子查询或内查询，

​				外面的select语句称为主查询或外查询

​	分类：

1、select后面

​	要求：子查询的结果为单行单列

2、from后面

​	要求：子查询的结果可以为多行多列

3、where或者having后面

​	要求：子查询的结果必须为单列，又分为单行子查询和多行子查询

4、 exists后面

​	要求：子查询结果必须为单列



特点：1、子查询放在条件中，要求必须放在条件的右侧

​			2、子查询一般放在小括号中

​			3、子查询的执行优先于主查询

​			4、单行子查询对应了单行操作符：> 、< 、>=、<=、<>

​					多行子查询对应了多行操作符：any、some、all、in

### 分页查询

​	语法结构：

​		select 查询列表

​		from 表1

​		join 表2

​		on  连接条件

​		where  筛选条件

​		group by  分组列表

​		having  分组后筛选

​		order by  排序列表

​		limit 起始条目索引，显示的条目数

​	**limit**关键字

​	特点：

​		1、如果起始条目索引不写，默认是0

​		2、limit后面支持两个参数，参数1是显示的起始条目索引，参数2是显示的条目数

### 联合查询

说明：当查询结果来自多张表，而多张表之间没有关联，这时候往往使用联合查询，也叫union查询

语法：

​	select 查询列表  from 表1 where 查询条件

​		union

​	select 查询列表 from 表2  where 查询条件

​		union

​	。。。。。。。

特点：1.多条待查询的查询语句的查询列数必须保持一致，查询类型，字段意义最好保持一致

​			2.union会自动去重，合并数据相同的条目

​			3.union all实现全部查询，包含重复项

## 数据库的管理

### 创建数据库

1. CREATE  DATABASE stuDB;
2. CREATE  DATABASE  **IF NOT EXISTS** stuDB;(如果不存在stuDB数据库，就创建数据库stuDB)

### 删除数据库

1. DROP DATABASE stuDB;
2. DROP DATABASE  IF  EXISTS stuDB;（如果存在数据库stuDB，则删除stuDB）

## 数据表的管理

### 创建表

CREATE TABLE [IF NOT EXISTS]  表名{

​		字段名  字段类型 【字段约束】,

​		字段名  字段类型 【字段约束】,

​		字段名  字段类型 【字段约束】

}

DESC 表名（查看表的结构）

### 修改表

语法：ALTER TABLE 表名  ==ADD==|==MODIFY==|==CHANGE==|==DROP== COLUMN 字段名 字段类型 【字段约束】

#### 修改表名

ALTER TABLE 表名 ==RENAME TO== 新表名

​	eg：ALTER TABLE stuinfo ==RENAME TO== students

#### 添加字段

ALTER TABLE **students**  ==ADD COLUMN== **borndate** *TIEMSTAMP NOT NULL*

#### 修改字段名

ALTER TABLE 表名  ==CHANGE== COLUMN 字段名 新字段名 新字段类型 【新字段约束】

​	eg：ALTER TABLE **students**  ==CHANGE COLUMN== **borndate**  **birthday** *TIEMSTAMP NOT NULL*

#### 修改字段约束

ALTER TABLE **表名**  ==MODIFY COLUMN== **字段名** *新类型*

​		eg：ALTER TABLE **students**  ==MODIFY COLUMN== **borndate** *DATETIME*

#### 删除字段

ALTER TABLE 表名  ==DROP COLUMN== **字段名** 

ALTER TABLE **students**  ==DROP COLUMN== **borndate** 



### 删除表

DROP TABLE IF EXISTS **表名**

​		eg：DROP TABLE IF EXISTS **students**

### 复制表

#### 仅仅复制表的结构，不复制数据

CREATE TABLE **新表名** LIKE **想要复制的表名**

​	eg：CREATE TABLE **newtable** LIKE **major**

#### 复制表的结构和数据

CREATE TABLE **新表名** SELECT * FROM **想要复制的表**

​	eg：`CREATE TABLE newtable SELECT * FROM students.major`

​			`ps : students.major 表示数据库students下的数据表major`



------

## 事务

 什么是事务？

​	事务，就是在达成一项任务时制定的所有步骤，如果其中一步出错，则整个任务失败，其他所有步骤也没有意义，需要回到原来的状态，只有所有的步骤都成功，任务才算完成

​	例子：小明转5块钱到小红账户，步骤如下：

​			1、小明账户 -5

​			2、小红账户 +5

​		如果第二步出错，则第一步没有意义，还可能带来麻烦，必须取消回到之前的状态

**总结就是 要么全部成功，要么全部失败**



事务的四大特性（ACID）

- 原子性（Atomicity）：事务中所有操作是不可再分割的原子单位。事务中所有操作要么全部执行成功，要么全部执行失败。

- 一致性（Consistency）：事务执行后，数据库状态与其它业务规则保持一致。如转账业务，无论事务执行成功与否，参与转账的两个账号余额之和应该是不变的。
-  隔离性（Isolation）：隔离性是指在并发操作中，不同事务之间应该隔离开来，使每个并发中的事务不会相互干扰。
-  持久性（Durability）：一旦事务提交成功，事务中所有的数据操作都必须被持久化到数据库中，即使提交事务后，数据库马上崩溃，在数据库重启时，也必须能保证通过某种机制恢复数据。



### sql中的事务

#### 分类

1. 隐性事务：没有明显的开始和结束标记

   必如dml中的update、delete、insert语句本身就是一条事物

   ​	insert into stuinfo values(1,'join','男',20)

   这也解释了为什么在插入时，其中一个字段插入错误，其他所有字段也插入错误

2. 显性事务：一般由多条sql语句构成，有明显的开始和结束标记

   编写步骤：

   1. 取消 隐式事务自动开启的功能

      运行 ` SHOW VARIABLES LIKE '%auto%;'`，可以看到自动提交功能已经开启

      ![image-20201023154719795](https://z3.ax1x.com/2021/06/06/2UkuAU.png)

      取消自动开启,运行 `SET autocommit=0;`

      ![image-20201023155137305](https://z3.ax1x.com/2021/06/06/2Uk139.png)

   2. 开启事务

      `START TRANSACTION`

   3. 编写事务的sql语句

      eg：

      `UPDATE users_company SET c_id=3 WHERE username='李小鑫';
      UPDATE users_company SET c_id=4 WHERE username='sdg';`

   4. 结束事务

      #提交(正常就提交)
      `COMMIT;`
      #回滚（出错就回滚）
      `ROLLBACK;`

------



## JDBC

JDBC: java database conectivity

连接数据库的基本代码：

```java
  public static final String URL = "jdbc:mysql://localhost:3306/数据库名";//数据库地址
   public static final String USER = "xxxx";//用户名
    public static final String PASSWORD = "123456";//密码

    public static void main(String[] args) throws Exception {
        //1.加载驱动程序
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_name, age FROM imooc_goddess");
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs.getString("user_name")+" 年龄："+rs.getInt("age"));
        }
        //关闭连接(后用先关原则)
        stmt.close();
        conn.close();
    }
```

### 加载驱动

加载驱动就是要将需要的类加载到内存中，有4种方式

1. new 对象
2. 加载子类（加载子类的同时，父类也加载进来了）
3. 调用类中的静态成员
4. 通过反射

使用new 对象的不足：

```java
DriverManager.deregisterDriver(new Driver());//使用new 对象方式的代码
```

​	1. 依赖性太强，属于编译期加载，如果编译期间该类不存在，则直接报编译错误；

 	2. 导致Drive类创建了两遍，效率太低（要根据源码理解源码）

![image-20201023165839448](https://z3.ax1x.com/2021/06/06/2Uk2E8.png)

一般采用反射的方式加载类

`Class.forName("com.mysql.jdbc.Driver");`

### 获取数据库连接

获取数据库连接的代码

`Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);`

getConnection方法有多种重载方法

1. getConnection(URL)//直接填写完整的url地址，

如jdbc:mysql://localhost:3306/database?user=username&password=password

​	2. getConnection(URL,USER,PASSWORD)//分别填写数据库地址，用户名，密码

​	3. 使用配置文件

![image-20201023171742937](https://z3.ax1x.com/2021/06/06/2UAexA.png)

### 执行增删改查

```java
 //3.操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT user_name, age FROM imooc_goddess");
        //如果有数据，rs.next()返回true
        while(rs.next()){
            System.out.println(rs.getString("user_name")+" 年龄："+rs.getInt("age"));
        }
```

1. Resultset获得查询的结果集，rs.next()可以获取每一行的结果

   rs.getString("列名（数据表中的字段名）")、

   rs.getString(列数):如 rs.getString(1)表示获取该行的第一列的值

   除了getString，还有getInt、getDate、getObject。。。。。

2. Statement 和 PrepareStatement

   1. 使用Statement：

   ```java
   Statement stmt = conn.createStatement();
   String Sql = "select * from users where username="+username+"and password="+password;
    ResultSet rs = stmt.executeQuery(sql);
   ```

   2. 使用PrepareStatement

   ```java
   
   	private static Connection conn = null;
   	private PreparedStatement pre =null;
   	private ResultSet rs = null;
   	public int login(User users){//登录操作
   		try{
   			conn = JDBCUtill.getConn();
               //编写sql语句
   			String sql = "select count(*) from hybigwork where username=? and password=?";
               //获取命令对象，并且预编译
   			pre = conn.prepareStatement(sql);
               //设置占位符的值
   			pre.setString(1, username);
   			pre.setString(2, password);
               //执行命令
   			rs = pre.executeQuery();
   			if(rs.next()){
   				return rs.getInt(1);
   			}
   		}catch(Exception e){
   			e.printStackTrace();
   		}
   ```

推荐使用PrepareStatement

理由：1. 不再使用 + 拼接sql语句，减少了语法错误，语义性强

   2. 将模版sql（固定的部分）与参数进行分离，提高维护性

   3. 有效地解决了sql注入问题（使用Statement会被输入的特殊字符影响，可能被恶意攻击）

   4. 效率高，减少了编译次数（因为有预编译，可以重复使用）

      

### jdbc常用接口：

[常用接口文章](https://www.cnblogs.com/klb561/p/10771364.html)



### JDBC实现事务

```java
 public static void main(String[] args) {
     	//获取连接
        Connection conn =getConnection();
        try {
            //1.关闭自动提交功能
            conn.setAutoCommit(false);
           //2.执行若干条sql语句
            //3.1 无异常则正常提交
            conn.commit();
        } catch (SQLException e) {
            System.out.println("************事务处理出现异常***********");
            e.printStackTrace();
            try {
                //3.2 出异常则回滚
                conn.rollback();
                System.out.println("*********事务回滚成功***********");
            } catch (Exception e2) {
                // TODO: handle exception
                e2.printStackTrace();
            }finally {
                try {
                    conn.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
```

1. 关闭自动提交功能：
               conn.setAutoCommit(false);
2. 执行sql语句
3. 1提交：conn.commit();

3. 2回滚：conn.rollback();



### 批处理

关键API：

addBatch：添加

executeBatch：处理

clearBatch：清空

```java

	private PreparedStatement pre =null;
	private ResultSet rs = null;
	
		try{
			conn = JDBCUtill.getConn();
            //编写sql语句
			String sql = "select count(*) from hybigwork where username=? and password=?";
            //获取命令对象，并且预编译
			pre = conn.prepareStatement(sql);
            for(int i=0;i<=50000;i++){
                 //设置占位符的值
				pre.setString(1, username);
				pre.setString(2, password);
                
                pre.addBatch();//添加sql语句到批处理包中
                if(i%1000==0){
                    pre.executeBatch();//执行批处理包中的语句
                    pre.clearBatch();//清空批处理包中的语句
                }
                
                
            }
           
 
		}catch(Exception e){
			e.printStackTrace();
		}
```

