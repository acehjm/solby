## Solby

> Solby 是一款基于SpringBoot开发的Web服务脚手架，目前还在不断的梳理功能并完善。

![apache](http://jaywcjlove.github.io/sb/license/apache.svg)     ![passing](http://jaywcjlove.github.io/sb/build/passing.svg)   [![follow](http://jaywcjlove.github.io/sb/github/w-follow.svg)](https://github.com/acehjm/solby/blob/master/README.md)      [![fork](http://jaywcjlove.github.io/sb/github/w-fork.svg)](https://github.com/acehjm/solby/blob/master/README.md)    [![star](http://jaywcjlove.github.io/sb/github/w-star.svg)](https://github.com/acehjm/solby/blob/master/README.md)

[English](https://github.com/acehjm/solby) | 中文文档

### 项目环境

- 开发环境：[OpenJDK11](https://openjdk.java.net/projects/jdk/11)，[IDEA](https://www.jetbrains.com/idea/)；
- 构建工具：[Gradle](https://gradle.org/)；
- 开发框架：[Spring boot](https://spring.io/projects/spring-boot)，[mybatis-plus](https://github.com/baomidou/mybatis-plus), etc；
- 数据库：[Redis](https://redis.io/)，[PostgreSQL](https://www.postgresql.org/)，[MySQL](https://www.mysql.com/)；

### 项目模块描述

***xcommon***

***xfile*** 

文件相关模块，包括excel和csv的导入和导出、文件大小、文件哈希、文件后缀检查等。

***xtool***

通用工具集合，具有json解析包、安全工具类、树操作、通用验证等。

***xoauth***

授权登录模块，为业务模块提供授权和验证服务。

***xquartz***

基于java quzrtz的定时任务服务。

***xconfig***

通用配置服务，如Redis、线程池、数据源、Kafka、RabbitMQ等。

***xboot***

业务服务，包括webmvc、datasource、mybatis-plus、flyway配置等，使用诸多SpringBoot特性。

### 如何使用

*IDEA方式:* 在IDEA中，使用“`Run`”或“`Debug`”模式运行Xboot主类`XbootApplication`。

*Gradle方式:* 在Gradle中，执行 `gradle bootRun` 来启动应用。

*Script方式:*  在 `/bin`目录下找到启动脚本, 使用以下命令启动： `./xxx.sh [APP_NAME] [start|stop|restart|status]`.
