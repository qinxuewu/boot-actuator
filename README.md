# boot-actuator


### 演示地址：http://www.qinxuewu.club/login  账号密码 admin/admin


#### 项目介绍
- 基于SpringBoot2.0 实现的jvm远程监工图形化工具，可以同时监控多个web应用
- 该项目是借鉴另个一开源项目 （ JavaMonitor） https://gitee.com/zyzpp/JavaMonitor 演变而来，剔除了一些功能，增加了可远程监控模块，只需要在需要监控的项目集成监控的jar包 并设置可访问的IP（默认为空 则不拦截IP访问） 就可以实现远程监控,和用户管理模块,动态定时任务
支付windows服务器和Linux服务监控,Mac还未测试 应该也支持 

#### 项目框架
- SpringBoot 2.0.3.RELEASE
- mybatis-plus 3.6
- MySql
- Jdk1.8


#### 目录说明
1. boot-actuator  需要监控的项目demo
1. actuator-service  监控端点jar包 需要引入到需要监控的项目中（已打包好上传）
1. boot-monitor    监监控图形化工程
1. Sql文件  /boot-monitor/src/main/resources/db/actuator.sql

### 安装说明

 **第一步** 
编译actuator-service工程 打成jar包

```
mvn install:install-file -Dfile=actuator-service-1.0.jar -DgroupId=com.github.qinxuewu -DartifactId=actuator-service -Dversion=1.0 -Dpackaging=jar

Dfile: 要安装的JAR的本地路径 
DgroupId：要安装的JAR的Group Id  （本地仓库的下一级目录到生成好的jar包的上一级目录 之间 用.分割：redis.clients）
DartifactId: 要安装的JAR的 Artificial Id （生成好的jar包的上一级目录）
Dversion: JAR 版本 
Dpackaging: 打包类型，例如JAR

```

 **第二步 需要监控的项目中引入actuator-service-1.0.jar** 

```
<dependency>
	<groupId>com.github.qinxuewu</groupId>
	 <artifactId>actuator-service</artifactId>
	 <version>1.0</version>
</dependency

#application.properties增加如下配置
#监控应用名称 唯一
spring.application.name=web1

#限制白名单 访问监控端点 为空则不限制  建议设置
actuator.server.ip=


#springBoot启动类上增加扫描包注解 

com.pflm.**：  是需要监控的项目包名  
com.github.qinxuewu.cor   ：是暴露给外部访问的监控端点包（actuator-service-1.0.jar中的controller）

@ComponentScan("com.pflm.**,com.github.qinxuewu.core")


#编译成jar或war包  ，启动需要监控的项目 （如：）
nohup java -Xms256m -Xmx256m -jar boot-actuator.jar  &
```
启动日志出现如下端点信息 则表示集成监控jar包成功
![输入图片说明](https://images.gitee.com/uploads/images/2018/1211/201912_ac025aa7_1478371.png "屏幕截图.png")


 **第三步启动boot-monitor工程（JVM远程性能监控管理工具） ** 

```
#修改application.properties配置 

#配置mysql数据源
mybatis-plus.configuration.aggressive-lazy-loading=false
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/actuator?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.username=root
spring.datasource.password=root

#编译成jar或war包启动工程
nohup java -jar boot-monitor.jar  &
```
访问：http://localhost:8080/login  登录默认账号  admin/admin

### 效果图如下

### 登录
![输入图片说明](https://images.gitee.com/uploads/images/2018/1212/180351_85b7e7c0_1478371.png "屏幕截图.png")

### 监控列表主页  增加应用，删除应用

![![输入图片说明](https://images.gitee.com/uploads/images/2018/1212/153102_753284c9_1478371.png "屏幕截图.png")](https://images.gitee.com/uploads/images/2018/1213/162341_854c1ecb_1478371.png "屏幕截图.png")

### 监控详情
![输入图片说明](https://images.gitee.com/uploads/images/2018/1213/163003_4852ee05_1478371.png "屏幕截图.png")


### 用户管理
![输入图片说明](https://images.gitee.com/uploads/images/2018/1213/162918_d51c9088_1478371.png "屏幕截图.png")
### 定时任务
![输入图片说明](https://images.gitee.com/uploads/images/2018/1215/124822_14bd50ee_1478371.png "屏幕截图.png")
### 监控参数的含义如下：
- S0C：s0（from）的大小（KB）
- S1C：s1（from）的大小（KB）
- S0U：s0（from）已使用的空间（KB）
- S1U：s1(from)已经使用的空间(KB)
- EC：eden区的大小(KB)
- EU：eden区已经使用的空间(KB)
- OC：老年代大小(KB)
- OU：老年代已经使用的空间(KB)
- MC：元空间的大小（Metaspace）
- MU：元空间已使用大小（KB）
- CCSC：压缩类空间大小（compressed class space）
- CCSU：压缩类空间已使用大小（KB）
- YGC：新生代gc次数
- YGCT：新生代gc耗时（秒）
- FGC：Full gc次数
- FGCT：Full gc耗时（秒）
- GCT：gc总耗时（秒）
- Loaded：表示载入了类的数量
- Unloaded：表示卸载类的数量
- Compiled：表示编译任务执行的次数
- Failed：表示编译失败的次数
- Total：线程总数
- Runnable：正在运行的线程数
- Sleeping：休眠的线程数
- Waiting：等待的线程数

###  QQ群交流：924715723 




