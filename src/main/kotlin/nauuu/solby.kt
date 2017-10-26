package nauuu

import nauuu.router.Users
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.Compression
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.features.gzip
import org.jetbrains.ktor.routing.routing

/**
 * Description
 *
 * @author nauuu
 * @date 2017/06/24
 */
/*
fun Application.module() {
    //        connectToDB()

    Database.connect(
            url = "jdbc:mysql://127.0.0.1:3306/solby?characterEncoding=utf8&useSSL=true",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "root",
            password = "root"
    )

    install(DefaultHeaders)

    install(CallLogging)

    install(Compression) {
        gzip()
    }

    routing {
        Users()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8888, module = Application::module).start(wait = true)
}*/

fun Application.main() {
//    connectToDB()
    val url = environment.config.config("database").property("url")
    println("url: ${url.getString()}")

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression) {
        gzip()
    }
    routing {
        Users()
    }
}