package nauuu.router

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import nauuu.domain.ss
import nauuu.domain.vv

/**
 * Description
 *
 * @author nauuu
 * @date 2017-10-17
 */
fun Routing.Users() {
    get("/") {
        call.respondText("""<div style="font-size:360px;color:blue;">Hello, Ktor!</div>""", ContentType.Text.Html)
    }
    get("/user") {
        val user = vv(1)
        call.respond(user?.name ?: "fail")
    }
    get("/user/ooo") {
        call.respond("ooo")
    }
    get("/users") {
        ss()
        call.respondText("success")
    }
}