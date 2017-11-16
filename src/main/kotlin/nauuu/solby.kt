package nauuu

import io.ktor.application.Application
import io.ktor.application.ApplicationStopped
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.routing.routing
import nauuu.domain.*
import nauuu.router.SRoles
import nauuu.router.SUsers
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction
import java.text.DateFormat

/**
 * Description
 *
 * @author nauuu
 * @date 2017/06/24
 */
/*
fun Application.module() {
    routing {
        Users()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8888, module = Application::module).start(wait = true)
}*/

fun Application.main() {
    connectDB(environment)
    transaction {
        create(Users, Groups, Roles, Folders, Files)
    }

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression) {
        gzip()
    }
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            serializeNulls()
            setPrettyPrinting()
        }
    }
    routing {
        SUsers()
        SRoles()
    }
    environment.monitor.subscribe(ApplicationStopped) { println("___bye___") }
}