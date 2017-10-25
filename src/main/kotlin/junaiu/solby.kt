package junaiu

import junaiu.router.Users
import org.jetbrains.exposed.sql.Database
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
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

        Database.connect(
                url = "jdbc:mysql://127.0.0.1:3306/solby?characterEncoding=utf8&useSSL=true",
                driver = "com.mysql.cj.jdbc.Driver",
                user = "root",
                password = "root"
        )

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression)

    install(Routing) {
        Users()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8888, module = Application::sApp).start(wait = true)
}