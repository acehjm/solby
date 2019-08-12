### Solby

> Solby is a Spring boot-based scaffolding that has been combed out in the process of continuous improvement.

![apache](http://jaywcjlove.github.io/sb/license/apache.svg)

#### Project environment

- Development environment：[OpenJDK11](https://openjdk.java.net/projects/jdk/11)；
- Build tool：[Gradle](https://gradle.org/)；
- Development Framework：[Spring boot](https://spring.io/projects/spring-boot)，[mybatis-plus](https://github.com/baomidou/mybatis-plus), etc；

#### Project module description

1. **ifile**

   ifile is a file-related module, including import and export of excel and csv, file size, file hash, file suffix check, etc.

2. **itool**

   itool is a generic module with json parsing packages, security-related tool classes, tree operations, and generic validation.
   
3. **xoauth**

   xoauth is an authorized login module that provides authorization and verification services for business modules.

4. **xquartz**

   xquratz is a timed task service based on java quzrtz.
   
5. **xconfig**

   xconfig is a service that includes configurations such as redis, thread pool, and related tools.

6. **xboot**

   xboot is a service including webmvc, datasource, mybatis-plus, flyway, etc.
