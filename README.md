## Solby

> Solby is a Spring boot-based scaffolding that has been combed out in the process of continuous improvement.

![apache](http://jaywcjlove.github.io/sb/license/apache.svg)     ![passing](http://jaywcjlove.github.io/sb/build/passing.svg)   [![follow](http://jaywcjlove.github.io/sb/github/w-follow.svg)](https://github.com/acehjm/solby/blob/master/README.md)      [![fork](http://jaywcjlove.github.io/sb/github/w-fork.svg)](https://github.com/acehjm/solby/blob/master/README.md)    [![star](http://jaywcjlove.github.io/sb/github/w-star.svg)](https://github.com/acehjm/solby/blob/master/README.md)

### Project environment

- Development environment：[OpenJDK11](https://openjdk.java.net/projects/jdk/11)；
- Build tool：[Gradle](https://gradle.org/)；
- Development Framework：[Spring boot](https://spring.io/projects/spring-boot)，[mybatis-plus](https://github.com/baomidou/mybatis-plus), etc；
- Database：[Redis](https://redis.io/)，[PostgreSQL](https://www.postgresql.org/)，[MySQL](https://www.mysql.com/)；

### Project module description

***xcommon***

***xfile*** 

File-related module, including import and export of excel and csv, file size, file hash, file suffix check, etc.

***xtool***

Generic module with json parsing packages, security-related tool classes, tree operations, and generic validation.

***xoauth***

Authorized login module that provides authorization and verification services for business modules.

***xquartz***

Timed task service based on java quzrtz.

***xconfig***

Configurations such as redis, thread pool, and related tools.

***xboot***

Configurations with webmvc, datasource, mybatis-plus, flyway, etc. It is contain a main function, and has some test controller functions.

### Usage

*By IDEA:* In xboot, run a configuration with main class of `XbootApplication` using `Run` or `Debug` mode.

*By Gradle:* with gradle, you can using `gradle bootRun` to boot application.

*By Script:*  find script from `/bin`, and usage with `./xxx.sh [APP_NAME] [start|stop|restart|status]`.

### Know Defect

- Gradle5 unsupport lombok annotation such as `@Data`, etc.
