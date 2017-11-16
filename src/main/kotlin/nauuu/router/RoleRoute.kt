package nauuu.router

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.route
import nauuu.handler.*

/**
 * Description
 *
 * @author nauuu
 * @date 2017-11-14
 */
fun Routing.SRoles() {
    route("/roles") {
        get {
            call.respond(findAll())
        }
        get("/{id}") {
            val role = findOne(1)
            call.respond(role ?: "fail")
        }
        post {
            create("role_1", "role_1_desc")
            createG("group_001", "group_1_desc")
            insert()
            call.respondText("create success.")
        }
    }
}
