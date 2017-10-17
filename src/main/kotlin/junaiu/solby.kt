package junaiu

import junaiu.router.Users
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.application.*
import org.jetbrains.ktor.features.*
import org.jetbrains.ktor.http.*
import org.jetbrains.ktor.response.*
import org.jetbrains.ktor.routing.*
import org.jetbrains.ktor.sessions.*
import org.jetbrains.ktor.util.*
import java.io.*
import org.jetbrains.ktor.application.log
import org.jetbrains.ktor.config.MapApplicationConfig
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.Compression
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing

/**
 * Description
 *
 * @author junaiu
 * @date 2017/06/24
 */
fun Application.sApp() {
    //        connectToDB()

//        Database.connect(
//                url = db.prop("url"),
//                driver = db.prop("driver"),
//                user = db.prop("user"),
//                password = db.prop("password")
//        )

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)

    /*val mapConfig = MapApplicationConfig()
    mapConfig.put("aa", "ddd")

    val ii = mapConfig.property("aa").getString()
    println("ii: $ii")

    val dd = mapConfig.property("database").getString()
    println("dd: $dd")*/

    log.info("config...")
    val youkubeConfig = environment.config.config("database")
    val key: String = youkubeConfig.property("driver").getString()
    println("$key")

    val db = environment.config.property("database.url").getString()
//    println("url: ${db.property("url").getString()}")
    log.info("config.. $db")

    install(Routing) {
        Users()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8888, module = Application::sApp).start(wait = true)
}