# solby-backend

----

This project is a backend of file system management platform.

## Technologies used

----
+ Language: [Kotlin](https://kotlin.link/)
+ Framework: [Ktor](http://ktor.io)
+ Engine: [Netty](http://netty.io/) used for server
+ Persistence: [Exposed](https://github.com/JetBrains/Exposed)
+ Build: [Gradle Script Kotlin](https://github.com/gradle/gradle-script-kotlin)

## Developer guide

----
### Prerequisite

+ Install [Git](https://git-scm.com/)
+ [Fork](https://github.com/acehjm/solby-backend#fork-destination-box) and clone [the project](https://github.com/acehjm/solby-backend)
+ [Install Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### Run the app in dev mod using command line
 - Run `./gradlew bootRun` in terminal
 - Open `http://localhost:8080/` in your browser
 - If you want to debug the app, add `--debug-jvm` parameter to Gradle command line

### Import and run the project in IDEA
- Make sure you have at least IntelliJ IDEA `2017.2.x` and IDEA Kotlin plugin `1.1.6+` (menu Tools -> Kotlin -> configure Kotlin Plugin Updates -> make sure "Stable" channel is selected -> check for updates now -> restart IDE after the update)
 - Import it in IDEA as a Gradle project
 - In IntelliJ IDEA,This requires us to indicate a new main class as IDEA will no longer be able to find it automatically. In build.gradle we add:
    ```groovy
    mainClassName = 'io.ktor.server.netty.DevelopmentEngine'
    ```
   then go to `Run -> Edit Configurations -> select solby-backend configuration` and change its Main class to: `io.ktor.server.netty.DevelopmentEngine`,then click on `Application.kt` then `Run ...` or `Debug ...`
 - Open `http://localhost:8080/` in your browser