package junaiu

import org.jetbrains.ktor.host.embeddedServer
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
        routing {
            get("/") {
//                call.respondText("Hello, Ktor!", ContentType.Text.Html)
                call.respond("{\"aa\":\"11\"}")
            }
            get("/user/{name}"){
                call.respondText(call.parameters["name"]!!)
            }
        }
    }
    server.start(wait = true)
}