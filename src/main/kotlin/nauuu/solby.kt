package nauuu

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import io.ktor.features.gzip
import io.ktor.routing.routing
import nauuu.router.Users

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

    install(DefaultHeaders)
    install(CallLogging)
    install(Compression) {
        gzip()
    }
    routing {
        Users()
    }
}