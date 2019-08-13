## Solby

> Solby is a Spring boot-based scaffolding that has been combed out in the process of continuous improvement.

![apache](http://jaywcjlove.github.io/sb/license/apache.svg)     ![passing](http://jaywcjlove.github.io/sb/build/passing.svg)   [![follow](http://jaywcjlove.github.io/sb/github/w-follow.svg)](https://github.com/acehjm/solby/blob/master/README.md)      [![fork](http://jaywcjlove.github.io/sb/github/w-fork.svg)](https://github.com/acehjm/solby/blob/master/README.md)    [![star](http://jaywcjlove.github.io/sb/github/w-star.svg)](https://github.com/acehjm/solby/blob/master/README.md)

### Project environment

- Development environment：[OpenJDK11](https://openjdk.java.net/projects/jdk/11)；
- Build tool：[Gradle](https://gradle.org/)；
- Development Framework：[Spring boot](https://spring.io/projects/spring-boot)，[mybatis-plus](https://github.com/baomidou/mybatis-plus), etc；
- DB：[Redis](https://redis.io/)，[PostgreSQL](https://www.postgresql.org/)，[MySQL](https://www.mysql.com/)；

### Project module description

#### **ifile**

   ifile is a file-related module, including import and export of excel and csv, file size, file hash, file suffix check, etc.

#### **itool**

   itool is a generic module with json parsing packages, security-related tool classes, tree operations, and generic validation.
   
#### **xoauth**

   xoauth is an authorized login module that provides authorization and verification services for business modules.

#### **xquartz**

   xquratz is a timed task service based on java quzrtz.
   
#### **xconfig**

   xconfig is a service that includes configurations such as redis, thread pool, and related tools.

#### **xboot**

   xboot is a service including webmvc, datasource, mybatis-plus, flyway, etc.
