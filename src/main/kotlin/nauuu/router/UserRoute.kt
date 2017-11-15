package nauuu.router

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import nauuu.domain.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Description
 *
 * @author nauuu
 * @date 2017-10-17
 */
fun Routing.SUsers() {
    get("/") {
        call.respondText("""<div style="font-size:360px;color:blue;">Hello, Ktor!</div>""", ContentType.Text.Html)
    }
    get("/user") {
        call.respond("fail")
    }
    get("/user/ooo") {
        call.respond("ooo")
    }
    get("/users") {
        call.respondText("success")
    }
}