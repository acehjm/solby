package junaiu.router

import junaiu.domain.vv
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respond
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Route
import org.jetbrains.ktor.routing.get

/**
 * Description
 *
 * @author dyoon
 * @date 2017-10-17
 */
fun Route.Users() {
    get("/") {
        call.respondText("""<div style="font-size:360px;color:blue;">Hello, Ktor!</div>""", ContentType.Text.Html)
    }
    get("/user") {
        call.respond(vv(1))
    }
    get("/user/ooo") {
        call.respond("ooo")
    }
}