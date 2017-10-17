package junaiu

import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.routing

/**
 * Description
 *
 * @author junaiu
 * @date 2017/06/24
 */
fun main(args: Array<String>) {
    val server = embeddedServer(Netty, 8888) {
        //        Database.connect(url = "", driver = "", user = "", password = "")

        routing {
            get("/") {
                call.respondText("""<div style="font-size:36px;color:blue;">Hello, Ktor!</div>""", ContentType.Text.Html)
            }
            get("/user") {
                call.respond(vv(1))
            }
        }
    }
    server.start(wait = true)
}