[TOC]

# Nacos 汇总

## Nacos的作用

​	目前主要用作项目配置中心和服务发现中心

## Nacos使用相关配置

> 目前较新的版本nacos默认启动方式为集群模式，个体用户建议更改为单机模式
>
> 需要修改nacos/conf目录下的application.properties   更改 nacos.standalone = true
>
> 并修改启动文件startup.cmd(linux同理)中set MODE="standalone"即可以单机模式启动nacos

nacos兼容mysql8（默认不支持）

添加目录nacos/plugins/mysql/ 目录下新增mysql8连接文件

<img src="C:\Users\95250\AppData\Roaming\Typora\typora-user-images\image-20210101213406242.png" alt="image-20210101213406242" style="float: left"  />

并在nacos配置文件中新增如下配置即可连接mysql8

``` 
spring.datasource.platform=mysql
db.num=1
driver-class-name=com.mysql.cj.jdbc.Driver
db.url.0=jdbc:mysql://YourIp:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai
db.user=YourUser
db.password=YourPassword
```

